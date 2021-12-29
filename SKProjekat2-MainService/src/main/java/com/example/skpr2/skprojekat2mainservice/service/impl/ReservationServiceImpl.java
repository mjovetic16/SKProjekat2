package com.example.skpr2.skprojekat2mainservice.service.impl;



import com.example.skpr2.skprojekat2mainservice.domain.*;
import com.example.skpr2.skprojekat2mainservice.dto.*;
import com.example.skpr2.skprojekat2mainservice.exception.NotFoundException;
import com.example.skpr2.skprojekat2mainservice.helper.MessageHelper;
import com.example.skpr2.skprojekat2mainservice.mapper.HotelMapper;
import com.example.skpr2.skprojekat2mainservice.mapper.ReservationMapper;
import com.example.skpr2.skprojekat2mainservice.mapper.RoomMapper;
import com.example.skpr2.skprojekat2mainservice.repository.*;
import com.example.skpr2.skprojekat2mainservice.security.service.TokenService;
import com.example.skpr2.skprojekat2mainservice.service.ReservationService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private ReservationRepository reservationRepository;

    private TerminRepository terminRepository;

    private ReservationMapper reservationMapper;

    private TokenService tokenService;

    private JmsTemplate jmsTemplate;

    private MessageHelper messageHelper;

    private String reservationDest;

    private String cancelReservationDest;

    private String reminderDestination;



    public ReservationServiceImpl(ReservationMapper reservationMapper, ReservationRepository reservationRepository,
                                  TerminRepository terminRepository, TokenService tokenService, JmsTemplate jmsTemplate,
                                  MessageHelper messageHelper, @Value("${destination.reservationNotify}")String reservationDest,
                                  @Value("${destination.cancelReservationNotify}")String cancelReservationDest,
                                  @Value("${destination.reminderNotify}") String reminderDestination) {

        this.reservationMapper = reservationMapper;

        this.reservationRepository = reservationRepository;

        this.terminRepository = terminRepository;

        this.tokenService = tokenService;

        this.jmsTemplate = jmsTemplate;

        this.messageHelper = messageHelper;

        this.reservationDest = reservationDest;

        this.cancelReservationDest = cancelReservationDest;

        this.reminderDestination = reminderDestination;

    }

    @Override
    public Page<ReservationDto> findAll(Pageable pageable) {
        return reservationRepository.findAll(pageable)
                .map(reservationMapper::reservationToReservationDto);
    }

    @Override
    public Page<TerminDto> findAllTs(Pageable pageable) {

        return terminRepository.findAll(pageable)
                .map(reservationMapper::terminToTerminDto);

    }

    @Override
    public Page<TerminDto> findAllFiltered(Pageable pageable, FilterDto filterDto) throws ParseException {

        String city = "";
        String hotel = "";
        Date startDate = new Date(0);

        String date_string = "2100-25-06";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-MM");
        Date endDate = null;

        SimpleDateFormat f = new SimpleDateFormat();

        endDate = formatter.parse(date_string);

        if(filterDto.getCity()!=null)
            city = filterDto.getCity();
        if(filterDto.getHotel()!=null)
            hotel = filterDto.getHotel();
        if(filterDto.getStartDate()!=null)
            startDate = formatter.parse(filterDto.getStartDate());
        if(filterDto.getEndDate()!=null)
            endDate = formatter.parse(filterDto.getEndDate());

        pageable = PageRequest.of(0,100, Sort.by("accommodation.roomType.price").ascending());

        System.out.println(city);
        System.out.println(hotel);
        System.out.println(startDate);
        System.out.println(endDate);
        System.out.println(pageable);

        return terminRepository.findByCityContainsAndHotelNameContainsAndDayGreaterThanAndDayLessThanAndAccommodationAvailableRoomsGreaterThan(pageable,city,hotel,startDate,endDate,0)
                .map(reservationMapper::terminToTerminDto);

    }

    @Override
    public ReservationDto reserve(String authorization, TerminDto terminDto) {
        authorization = authorization.replace("Bearer ", "");
        Claims claims = tokenService.parseToken(authorization);

        int id = claims.get("id",Integer.class);


        //Get user from User Service
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + authorization);

        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<UserDto> response = restTemplate.exchange("http://localhost:8083/users/user/"+id, HttpMethod.GET, request, UserDto.class);
        UserDto userDto = response.getBody();

        if(userDto==null){
            throw new NotFoundException("User not found");
        }

        Termin termin = terminRepository.findById(terminDto.getId()).orElseThrow(()->new NotFoundException("Nije pronadjen termin"));


        //Calculate price
        RankDto rankDto = userDto.getRank();
        float price = (float)terminDto.getAccommodationDto().getRoomType().getPrice() *(1f-(1f/100*rankDto.getDiscount()));

        //Make reservation
        Reservation reservation = new Reservation();
        reservation.setUserID(userDto.getId());
        reservation.setTermin(reservationMapper.terminDtoToTermin(terminDto));
        reservation.setPrice(price);
        reservation.setNotified(false);

        System.out.println("The price is: "+reservation.getPrice());


        //Change user Res Number
        ResponseEntity<UserDto> response2 = restTemplate.exchange("http://localhost:8080/api/user/reservation/"+id+"/true", HttpMethod.POST, request, UserDto.class);
        UserDto userDto2 = response2.getBody();

        System.out.println(userDto2);

        //Change termin available rooms
        termin.getAccommodation().setAvailableRooms(termin.getAccommodation().getAvailableRooms()-1);
        terminRepository.save(termin);

        reservation.setTermin(termin);

        //TODO Retry pattern

        //Save reservation
        ReservationDto rDto = reservationMapper.reservationToReservationDto(reservationRepository.save(reservation));

        //Slanje konfirmacije na notification service
        ReservationUserDto rUserDto = reservationMapper.reservationUserDtoFromReservationDto(rDto);
        rUserDto.setUserDto(userDto2);
        jmsTemplate.convertAndSend(reservationDest,messageHelper.createTextMessage(rUserDto));


        return rDto;
    }

    @Override
    public ReservationDto cancelReservation(String authorization, ReservationDto reservationDto) {
        authorization = authorization.replace("Bearer ", "");
        Claims claims = tokenService.parseToken(authorization);

        int id = claims.get("id", Integer.class);


        //Get user from User Service
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + authorization);

        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<UserDto> response = restTemplate.exchange("http://localhost:8080/api/user/" + id, HttpMethod.GET, request, UserDto.class);
        UserDto userDto = response.getBody();

        if (userDto == null) {
            throw new NotFoundException("User not found");
        }

        Termin termin = terminRepository.findById(reservationDto.getTerminDto().getId()).orElseThrow(() -> new NotFoundException("Nije pronadjen termin"));


        //Change user Res Number
        ResponseEntity<UserDto> response2 = restTemplate.exchange("http://localhost:8080/api/user/reservation/" + id + "/false", HttpMethod.POST, request, UserDto.class);
        UserDto userDto2 = response2.getBody();

        System.out.println(userDto2);

        //Change termin available rooms
        termin.getAccommodation().setAvailableRooms(termin.getAccommodation().getAvailableRooms() + 1);
        terminRepository.save(termin);

        //Save reservation
        reservationRepository.delete(reservationRepository.findById(reservationDto.getId()).orElseThrow(()->new NotFoundException("Reservation doesn't exist")));

        ReservationDto reservationDtoEmpty = new ReservationDto();
        reservationDtoEmpty.setTerminDto(reservationMapper.terminToTerminDto(termin));

        //Slanje konfirmacije na notification service
        ReservationUserDto rUserDto = reservationMapper.reservationUserDtoFromReservationDto(reservationDtoEmpty);
        rUserDto.setUserDto(userDto2);
        jmsTemplate.convertAndSend(cancelReservationDest,messageHelper.createTextMessage(rUserDto));

        return reservationDtoEmpty;
    }

    @Override
    @Transactional
    public void remindReservation() {
        Date myDate = Date.from(Instant.now().minus(2, ChronoUnit.DAYS));
        reservationRepository.findAllByNotifiedAndTerminDayGreaterThan(false,myDate)
                .forEach(reservation->{

                    reservation.setNotified(true);
                    reservationRepository.save(reservation);


                    ReservationDto reservationDto = reservationMapper.reservationToReservationDto(reservation);

                    ReservationUserDto reservationUserDto = reservationMapper.reservationUserDtoFromReservationDto(reservationDto);


                    jmsTemplate.convertAndSend(reminderDestination,messageHelper.createTextMessage(reservationUserDto));

                });
    }



    //
//    @Override
//    public DiscountDto findDiscount(Long id) {
//        User user = userRepository
//                .findById(id)
//                .orElseThrow(() -> new NotFoundException(String
//                        .format("User with id: %d not found.", id)));
//        List<UserStatus> userStatusList = userStatusRepository.findAll();
//        //get discount
//        Integer discount = userStatusList.stream()
//                .filter(userStatus -> userStatus.getMaxNumberOfReservations() >= user.getNumberOfReservations()
//                        && userStatus.getMinNumberOfReservations() <= user.getNumberOfReservations())
//                .findAny()
//                .get()
//                .getDiscount();
//        return new DiscountDto(discount);
//    }
}

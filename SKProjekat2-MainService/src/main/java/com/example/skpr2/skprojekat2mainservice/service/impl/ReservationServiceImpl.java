package com.example.skpr2.skprojekat2mainservice.service.impl;



import com.example.skpr2.skprojekat2mainservice.domain.Hotel;
import com.example.skpr2.skprojekat2mainservice.domain.Room;
import com.example.skpr2.skprojekat2mainservice.domain.RoomType;
import com.example.skpr2.skprojekat2mainservice.dto.*;
import com.example.skpr2.skprojekat2mainservice.exception.NotFoundException;
import com.example.skpr2.skprojekat2mainservice.mapper.HotelMapper;
import com.example.skpr2.skprojekat2mainservice.mapper.ReservationMapper;
import com.example.skpr2.skprojekat2mainservice.mapper.RoomMapper;
import com.example.skpr2.skprojekat2mainservice.repository.*;
import com.example.skpr2.skprojekat2mainservice.service.ReservationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private ReservationRepository reservationRepository;

    private TerminRepository terminRepository;

    private ReservationMapper reservationMapper;



    public ReservationServiceImpl(ReservationMapper reservationMapper, ReservationRepository reservationRepository, TerminRepository terminRepository) {

        this.reservationMapper = reservationMapper;

        this.reservationRepository = reservationRepository;

        this.terminRepository = terminRepository;

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

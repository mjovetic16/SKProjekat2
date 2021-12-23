package com.example.skpr2.skprojekat2mainservice.service.impl;



import com.example.skpr2.skprojekat2mainservice.domain.Hotel;
import com.example.skpr2.skprojekat2mainservice.dto.HotelDto;
import com.example.skpr2.skprojekat2mainservice.dto.ReservationDto;
import com.example.skpr2.skprojekat2mainservice.exception.NotFoundException;
import com.example.skpr2.skprojekat2mainservice.mapper.HotelMapper;
import com.example.skpr2.skprojekat2mainservice.mapper.ReservationMapper;
import com.example.skpr2.skprojekat2mainservice.repository.HotelRepository;
import com.example.skpr2.skprojekat2mainservice.repository.ReservationRepository;
import com.example.skpr2.skprojekat2mainservice.service.ReservationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private ReservationRepository reservationRepository;
    private ReservationMapper reservationMapper;
    private HotelRepository hotelRepository;
    private HotelMapper hotelMapper;


    public ReservationServiceImpl(ReservationMapper reservationMapper, ReservationRepository reservationRepository,
                                  HotelRepository hotelRepository, HotelMapper hotelMapper) {

        this.reservationMapper = reservationMapper;
        this.reservationRepository = reservationRepository;
        this.hotelMapper = hotelMapper;
        this.hotelRepository = hotelRepository;

    }

    @Override
    public Page<ReservationDto> findAll(Pageable pageable) {
        return reservationRepository.findAll(pageable)
                .map(reservationMapper::reservationToReservationDto);
    }

    @Override
    public Page<HotelDto> findAllHotels(Pageable pageable) {
        return hotelRepository.findAll(pageable)
                .map(hotelMapper::hotelToHotelDto);
    }

    @Override
    public HotelDto updateHotel(HotelDto hotelDto) {
        hotelRepository.findById(hotelDto.getId()).orElseThrow(()->new NotFoundException("Hotel ne postoji"));
        Hotel hotel = hotelMapper.hotelDtoToHotel(hotelDto);

        return hotelMapper.hotelToHotelDto(hotelRepository.save(hotel));
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

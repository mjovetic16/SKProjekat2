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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

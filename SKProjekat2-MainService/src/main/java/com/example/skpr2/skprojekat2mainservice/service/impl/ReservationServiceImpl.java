package com.example.skpr2.skprojekat2mainservice.service.impl;



import com.example.skpr2.skprojekat2mainservice.dto.ReservationDto;
import com.example.skpr2.skprojekat2mainservice.mapper.ReservationMapper;
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


    public ReservationServiceImpl(ReservationMapper reservationMapper, ReservationRepository reservationRepository) {

        this.reservationMapper = reservationMapper;
        this.reservationRepository = reservationRepository;

    }

    @Override
    public Page<ReservationDto> findAll(Pageable pageable) {
        return reservationRepository.findAll(pageable)
                .map(reservationMapper::reservationToReservationDto);
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

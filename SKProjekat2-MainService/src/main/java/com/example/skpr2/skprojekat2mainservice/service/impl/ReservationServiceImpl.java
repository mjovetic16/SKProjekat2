package com.example.skpr2.skprojekat2mainservice.service.impl;



import com.example.skpr2.skprojekat2mainservice.domain.Hotel;
import com.example.skpr2.skprojekat2mainservice.domain.Room;
import com.example.skpr2.skprojekat2mainservice.domain.RoomType;
import com.example.skpr2.skprojekat2mainservice.dto.*;
import com.example.skpr2.skprojekat2mainservice.exception.NotFoundException;
import com.example.skpr2.skprojekat2mainservice.mapper.HotelMapper;
import com.example.skpr2.skprojekat2mainservice.mapper.ReservationMapper;
import com.example.skpr2.skprojekat2mainservice.mapper.RoomMapper;
import com.example.skpr2.skprojekat2mainservice.repository.HotelRepository;
import com.example.skpr2.skprojekat2mainservice.repository.ReservationRepository;
import com.example.skpr2.skprojekat2mainservice.repository.RoomRepository;
import com.example.skpr2.skprojekat2mainservice.repository.RoomTypeRepository;
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
    private RoomRepository roomRepository;
    private RoomTypeRepository roomTypeRepository;
    private HotelRepository hotelRepository;
    private ReservationMapper reservationMapper;
    private HotelMapper hotelMapper;
    private RoomMapper roomMapper;



    public ReservationServiceImpl(ReservationMapper reservationMapper, ReservationRepository reservationRepository,
                                  HotelRepository hotelRepository, RoomRepository roomRepository, RoomTypeRepository roomTypeRepository,
                                  HotelMapper hotelMapper, RoomMapper roomMapper) {

        this.reservationMapper = reservationMapper;
        this.roomMapper = roomMapper;
        this.hotelMapper = hotelMapper;
        this.hotelRepository = hotelRepository;
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.roomTypeRepository = roomTypeRepository;


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

    @Override
    public HotelDto changeRoomAllocation(AllocationDtoRequest allocationDtoRequest) {

        HotelDto hotelDto = new HotelDto();
        for (AllocationDto allocationDto : allocationDtoRequest.getAllocationDtos()){
            if(allocationDto.isRanged()){

                List<RoomDto> rooms = allocationDto.getHotelDto().getRooms();
                for(int i = allocationDto.getStartIndex();i<allocationDto.getEndIndex();i++){
                    rooms.get(i).setRoomType(allocationDto.getRoomTypeDto().getId());
                }

                List<Room> rooms1 = new ArrayList<>();
                rooms.forEach((r->{rooms1.add(roomMapper.roomDtoToRoom(r));}));
                roomRepository.saveAll(rooms1);

                RoomTypeDto roomTypeDto = allocationDto.getRoomTypeDto();
                roomTypeDto.setNumberOfRooms(allocationDto.getEndIndex()-allocationDto.getStartIndex());
                roomTypeRepository.save(roomMapper.roomTypeDtoToRoomType(roomTypeDto));

                hotelDto = allocationDto.getHotelDto();

            }else{

                List<RoomDto> rooms = allocationDto.getHotelDto().getRooms();
                for(int i : allocationDto.getIndexList()){
                    rooms.get(i).setRoomType(allocationDto.getRoomTypeDto().getId());
                }
                List<Room> rooms1 = new ArrayList<>();
                rooms.forEach((r->{rooms1.add(roomMapper.roomDtoToRoom(r));}));
                roomRepository.saveAll(rooms1);

                RoomTypeDto roomTypeDto = allocationDto.getRoomTypeDto();
                roomTypeDto.setNumberOfRooms(allocationDto.getEndIndex()-allocationDto.getStartIndex());
                roomTypeRepository.save(roomMapper.roomTypeDtoToRoomType(roomTypeDto));

                hotelDto = allocationDto.getHotelDto();
            }


        }

        return hotelMapper.hotelToHotelDto(hotelRepository.findById(hotelDto.getId()).orElseThrow(()->new NotFoundException("Nije pronadjen hotel")));
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

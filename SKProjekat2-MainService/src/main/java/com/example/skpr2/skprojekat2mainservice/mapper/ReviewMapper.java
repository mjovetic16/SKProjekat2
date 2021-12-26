package com.example.skpr2.skprojekat2mainservice.mapper;

import com.example.skpr2.skprojekat2mainservice.domain.Reservation;
import com.example.skpr2.skprojekat2mainservice.domain.Review;
import com.example.skpr2.skprojekat2mainservice.dto.ReservationDto;
import com.example.skpr2.skprojekat2mainservice.dto.ReviewDto;
import com.example.skpr2.skprojekat2mainservice.exception.NotFoundException;
import com.example.skpr2.skprojekat2mainservice.repository.AccommodationRepository;
import com.example.skpr2.skprojekat2mainservice.repository.HotelRepository;
import com.example.skpr2.skprojekat2mainservice.repository.RoomTypeRepository;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    private HotelMapper hotelMapper;
    private HotelRepository hotelRepository;

    public ReviewMapper(HotelRepository hotelRepository,  HotelMapper hotelMapper) {

        this.hotelRepository = hotelRepository;
        this.hotelMapper = hotelMapper;

    }

    public ReviewDto reviewToReviewDto(Review review){
        ReviewDto reviewDto = new ReviewDto();

        reviewDto.setCity(review.getCity());
        reviewDto.setComment(review.getComment());
        reviewDto.setId(review.getId());
        reviewDto.setGrade(review.getGrade());
        reviewDto.setHotel(hotelMapper.hotelToHotelDtoRoomless(review.getHotel()));

        return reviewDto;
    }

    public Review reviewDtoToReview(ReviewDto reviewDto){

        Review review = new Review();

        review.setCity(reviewDto.getCity());
        review.setComment(reviewDto.getComment());
        review.setGrade(reviewDto.getGrade());
        review.setHotel(hotelRepository.findById(reviewDto.getHotel().getId()).orElseThrow(()->new NotFoundException("Nije pronadjen hotel")));
        review.setId(reviewDto.getId());

        return review;

    }

}

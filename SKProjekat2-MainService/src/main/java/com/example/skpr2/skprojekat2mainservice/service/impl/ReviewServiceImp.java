package com.example.skpr2.skprojekat2mainservice.service.impl;

import com.example.skpr2.skprojekat2mainservice.domain.Hotel;
import com.example.skpr2.skprojekat2mainservice.domain.Review;
import com.example.skpr2.skprojekat2mainservice.dto.FilterDto;
import com.example.skpr2.skprojekat2mainservice.dto.HotelDto;
import com.example.skpr2.skprojekat2mainservice.dto.ReviewDto;
import com.example.skpr2.skprojekat2mainservice.exception.NotFoundException;
import com.example.skpr2.skprojekat2mainservice.mapper.HotelMapper;
import com.example.skpr2.skprojekat2mainservice.mapper.ReviewMapper;
import com.example.skpr2.skprojekat2mainservice.repository.HotelRepository;
import com.example.skpr2.skprojekat2mainservice.repository.ReviewRepository;
import com.example.skpr2.skprojekat2mainservice.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImp implements ReviewService {

    private ReviewRepository reviewRepository;
    private HotelRepository hotelRepository;
    private HotelMapper hotelMapper;
    private ReviewMapper reviewMapper;


    public ReviewServiceImp(ReviewMapper reviewMapper, ReviewRepository reviewRepository, HotelMapper hotelMapper, HotelRepository hotelRepository) {
        this.hotelMapper = hotelMapper;
        this.hotelRepository = hotelRepository;
        this.reviewMapper = reviewMapper;
        this.reviewRepository = reviewRepository;

    }

    @Override
    public ReviewDto makeReview(String auth, ReviewDto reviewDto) {


        Review review = reviewMapper.reviewDtoToReview(reviewDto);

        return reviewMapper.reviewToReviewDto(reviewRepository.save(review));
    }

    @Override
    public ReviewDto deleteReview(String auth, ReviewDto reviewDto) {

        Review review = reviewRepository.findById(reviewDto.getId()).orElseThrow(()->new NotFoundException("Review doesn't exist"));

        reviewRepository.deleteById(review.getId());

        return reviewDto;

    }

    @Override
    public Page<ReviewDto> getFilteredReviews(String auth, FilterDto filterDto, Pageable pageable) {
        String city = "";
        String hotel = "";


        if(filterDto.getCity()!=null)
            city = filterDto.getCity();
        if(filterDto.getHotel()!=null)
            hotel = filterDto.getHotel();


        //Pageable pageable = PageRequest.of(0,100, Sort.by("accommodation.roomType.price").ascending());

        return reviewRepository.findAllByCityContainsAndHotelNameContains(city,hotel,pageable)
                .map(reviewMapper::reviewToReviewDto);
    }

    @Override
    public List<HotelDto> getBestHotels(String auth) {


        List<HotelDto> hotelDtos = new ArrayList<>();

        HashMap<Long,Hotel> hotelHashMap = new HashMap<Long, Hotel>();

        List<Review> reviews = new ArrayList<>();

        int average = 0;

        reviews = reviewRepository.findAll();

        for(Review r: reviews){
            average+=r.getGrade();
        }

        average = average/ reviews.size();

        reviews = reviewRepository.findAllByGradeGreaterThan(average);


        if(reviews.size()==0){
            throw new NotFoundException("No reviews found");
        }

        reviews.forEach((r)->{
            Hotel hotel = r.getHotel();
            hotelHashMap.put(hotel.getId(),hotel);
        });

        for (Hotel hotel : hotelHashMap.values()) {
            hotelDtos.add(hotelMapper.hotelToHotelDto(hotel));
        }

        return hotelDtos;
    }
}

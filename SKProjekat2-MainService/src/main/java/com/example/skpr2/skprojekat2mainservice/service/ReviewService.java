package com.example.skpr2.skprojekat2mainservice.service;

import com.example.skpr2.skprojekat2mainservice.dto.FilterDto;
import com.example.skpr2.skprojekat2mainservice.dto.HotelDto;
import com.example.skpr2.skprojekat2mainservice.dto.ReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {


    ReviewDto makeReview(String auth, ReviewDto reviewDto);

    ReviewDto deleteReview(String auth, ReviewDto reviewDto);

    Page<ReviewDto> getFilteredReviews(String auth, FilterDto filterDto, Pageable pageable);

    List<HotelDto> getBestHotels(String auth);



}

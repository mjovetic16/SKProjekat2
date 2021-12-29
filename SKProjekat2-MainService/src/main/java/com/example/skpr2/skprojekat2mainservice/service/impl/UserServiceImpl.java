package com.example.skpr2.skprojekat2mainservice.service.impl;

import com.example.skpr2.skprojekat2mainservice.dto.UserDto;
import com.example.skpr2.skprojekat2mainservice.exception.NotFoundException;
import com.example.skpr2.skprojekat2mainservice.service.UserService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceImpl implements UserService {

    public UserServiceImpl() {

    }

    @Override
    public UserDto getUser(long userId){
        //Get user from User Service
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<UserDto> response = restTemplate.exchange("http://localhost:8080/api/user/" + userId, HttpMethod.GET, request, UserDto.class);
        UserDto userDto = response.getBody();

        if (userDto == null) {
            throw new NotFoundException("User not found");
        }

        return userDto;
    }


}

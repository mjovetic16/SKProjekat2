package com.example.skpr2.skprojekat2userservice.service;


import com.example.skpr2.skprojekat2userservice.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Page<UserDto> findAll(Pageable pageable);

    UserDto add(UserCreateDto userCreateDto);

    UserDto addManager(ManagerCreateDto managerCreateDto);

    UserDto updateUser(UserDto userDto, String auth);

    TokenResponseDto login(TokenRequestDto tokenRequestDto);

    BlockedDto block(UserDto userDto);

    BlockedDto unblock(UserDto userDto);

    boolean isBlocked(UserDto userDto);

    BlockedDto getBlocked();
}

package com.example.skpr2.skprojekat2userservice.controller;


import com.example.skpr2.skprojekat2userservice.domain.Blocked;
import com.example.skpr2.skprojekat2userservice.dto.*;
import com.example.skpr2.skprojekat2userservice.security.CheckSecurity;
import com.example.skpr2.skprojekat2userservice.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Get all users")
    @GetMapping
    @CheckSecurity(roles = {"ROLE_ADMIN", "ROLE_CLIENT"})
    public ResponseEntity<Page<UserDto>> getAllUsers(@RequestHeader("Authorization") String authorization,
                                                     Pageable pageable) {

        return new ResponseEntity<>(userService.findAll(pageable), HttpStatus.OK);
    }


    @ApiOperation(value = "Register client")
    @PostMapping("/client")
    public ResponseEntity<UserDto> saveClient(@RequestBody @Valid UserCreateDto userCreateDto) {
        return new ResponseEntity<>(userService.add(userCreateDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Register manager")
    @PostMapping("/manager")
    public ResponseEntity<UserDto> saveManager(@RequestBody @Valid ManagerCreateDto managerCreateDto) {
        return new ResponseEntity<>(userService.addManager(managerCreateDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Login")
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> loginUser(@RequestBody @Valid TokenRequestDto tokenRequestDto) {
        return new ResponseEntity<>(userService.login(tokenRequestDto), HttpStatus.OK);
    }

    @ApiOperation(value="Block user")
    @PostMapping("/block")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<BlockedDto> blockUser(@RequestHeader("Authorization") String authorization, @RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.block(userDto),HttpStatus.OK);
    }

    @ApiOperation(value="Unblock user")
    @PostMapping("/block/unblock")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<BlockedDto> unblockUser(@RequestHeader("Authorization") String authorization, @RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.unblock(userDto),HttpStatus.OK);
    }

    @ApiOperation(value="List of all blocked users")
    @GetMapping("/block/all")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<BlockedDto> getBlocked(@RequestHeader("Authorization") String authorization){
        return new ResponseEntity<>(userService.getBlocked(),HttpStatus.OK);
    }

}

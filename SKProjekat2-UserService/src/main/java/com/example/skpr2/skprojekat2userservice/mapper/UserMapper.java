package com.example.skpr2.skprojekat2userservice.mapper;


import com.example.skpr2.skprojekat2userservice.domain.*;
import com.example.skpr2.skprojekat2userservice.dto.*;
import com.example.skpr2.skprojekat2userservice.repository.RoleRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserMapper {

    private RoleRepository roleRepository;

    public UserMapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public UserDto userToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setUsername(user.getUsername());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setBirthDate(user.getBirthDate());
        userDto.setRole(user.getRole().getName().name());

        userDto.setNumberOfReservations(user.getNumberOfReservations());
        userDto.setPassport(user.getPassport());

        userDto.setHotel(user.getHotel());
        userDto.setDateOfEmployment(user.getDateOfEmployment());
        userDto.setRank(rankToRankDto(user.getRank()));
        return userDto;
    }

    public User userDtoToUser(UserDto userDto) {
        User user = new User();

        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        user.setUsername(userDto.getUsername());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setBirthDate(userDto.getBirthDate());
        user.setRole(roleRepository.findRoleByName(RoleType.valueOf(userDto.getRole())).get());

        user.setNumberOfReservations(userDto.getNumberOfReservations());
        user.setPassport(userDto.getPassport());

        user.setHotel(userDto.getHotel());
        user.setDateOfEmployment(userDto.getDateOfEmployment());
        user.setRank(rankDtoToRank(userDto.getRank()));
        return user;
    }

    public User userCreateDtoToUser(UserCreateDto userCreateDto) {
        User user = new User();
        user.setEmail(userCreateDto.getEmail());
        user.setFirstName(userCreateDto.getFirstName());
        user.setLastName(userCreateDto.getLastName());
        user.setUsername(userCreateDto.getUsername());
        user.setPassword(userCreateDto.getPassword());
        user.setRole(roleRepository.findRoleByName(RoleType.ROLE_CLIENT).get());
        user.setBirthDate(userCreateDto.getBirthdate());
        user.setPhoneNumber(userCreateDto.getPhoneNumber());

        user.setPassport(userCreateDto.getPassport());
        user.setNumberOfReservations(0);

        return user;
    }

    public User managerCreateDtoToUser(ManagerCreateDto managerCreateDto) {
        User user = new User();
        user.setEmail(managerCreateDto.getEmail());
        user.setFirstName(managerCreateDto.getFirstName());
        user.setLastName(managerCreateDto.getLastName());
        user.setUsername(managerCreateDto.getUsername());
        user.setPassword(managerCreateDto.getPassword());
        user.setRole(roleRepository.findRoleByName(RoleType.ROLE_MANAGER).get());
        user.setBirthDate(managerCreateDto.getBirthdate());
        user.setPhoneNumber(managerCreateDto.getPhoneNumber());

        user.setDateOfEmployment(managerCreateDto.getDateOfEmployment());
        user.setHotel(managerCreateDto.getHotel());


        return user;
    }

    public BlockedDto blockedToBlockedDto(Blocked blocked){
        BlockedDto bdto = new BlockedDto();
        bdto.setId(blocked.getId());

        ArrayList<UserDto> userDtos = new ArrayList();
        blocked.getBlockedUsers().forEach( (user) -> userDtos.add(this.userToUserDto(user)) );
        bdto.setBlockedUsers(userDtos);

        return bdto;

    }

    public Rank rankDtoToRank(RankDto rankDto){
        Rank rank = new Rank();

        rank.setValue(rankDto.getValue());
        rank.setId(rankDto.getId());
        rank.setName(rankDto.getName());
        rank.setDiscount(rankDto.getDiscount());

        return rank;

    }

    public RankDto rankToRankDto(Rank rank){
        RankDto rankDto = new RankDto();

        rankDto.setId(rank.getId());
        rankDto.setName(rank.getName());
        rankDto.setValue(rank.getValue());
        rankDto.setDiscount(rank.getDiscount());

        return rankDto;
    }
}

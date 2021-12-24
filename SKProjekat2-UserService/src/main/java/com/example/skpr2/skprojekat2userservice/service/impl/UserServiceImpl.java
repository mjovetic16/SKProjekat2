package com.example.skpr2.skprojekat2userservice.service.impl;


import com.example.skpr2.skprojekat2userservice.domain.Blocked;
import com.example.skpr2.skprojekat2userservice.domain.Rank;
import com.example.skpr2.skprojekat2userservice.domain.User;
import com.example.skpr2.skprojekat2userservice.dto.*;
import com.example.skpr2.skprojekat2userservice.exception.BlockedException;
import com.example.skpr2.skprojekat2userservice.exception.NotFoundException;
import com.example.skpr2.skprojekat2userservice.mapper.UserMapper;
import com.example.skpr2.skprojekat2userservice.repository.BlockedRepository;
import com.example.skpr2.skprojekat2userservice.repository.RankRepository;
import com.example.skpr2.skprojekat2userservice.repository.UserRepository;
import com.example.skpr2.skprojekat2userservice.security.service.TokenService;
import com.example.skpr2.skprojekat2userservice.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private TokenService tokenService;
    private UserRepository userRepository;
    private UserMapper userMapper;
    private BlockedRepository blockedRepository;
    private RankRepository rankRepository;

    public UserServiceImpl(UserRepository userRepository, TokenService tokenService, UserMapper userMapper, BlockedRepository blockedRepository,
                           RankRepository rankRepository) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.userMapper = userMapper;
        this.blockedRepository = blockedRepository;
        this.rankRepository = rankRepository;
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::userToUserDto);
    }


    @Override
    public UserDto add(UserCreateDto userCreateDto) {
        User user = userMapper.userCreateDtoToUser(userCreateDto);
        user.setRank(rankRepository.getById(3L));
        System.out.println(user.getRank());
        userRepository.save(user);
        return userMapper.userToUserDto(user);
    }

    @Override
    public UserDto addManager(ManagerCreateDto managerCreateDto) {
        User user = userMapper.managerCreateDtoToUser(managerCreateDto);
        userRepository.save(user);
        return userMapper.userToUserDto(user);
    }


    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        //Try to find active user for specified credentials
        User user = userRepository
                .findUserByEmailAndPassword(tokenRequestDto.getEmail(), tokenRequestDto.getPassword())
                .orElseThrow(() -> new NotFoundException(String
                        .format("User with email: %s and password: %s not found.", tokenRequestDto.getEmail(),
                                tokenRequestDto.getPassword())));

        if(isBlocked(userMapper.userToUserDto(user)))
            throw  new BlockedException(String
                    .format("The user is blocked from accessing the application"));


        //Create token payload
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("role", user.getRole().getName());
        //Generate token
        return new TokenResponseDto(tokenService.generate(claims));
    }

    @Override
    public boolean isBlocked(UserDto userDto) {

        return blockedRepository.findByBlockedUsersContains(userMapper.userDtoToUser(userDto)).isPresent();

    }

    @Override
    public UserDto updateUser(UserDto userDto, String auth) {
        auth = auth.replace("Bearer ", "");
        Claims claims = tokenService.parseToken(auth);

        User user = userRepository.findUserById(userDto.getId())
                .orElseThrow(()->new NotFoundException(String.format("User doesn't exist")));


        if(user.getId().equals(Long.valueOf(claims.get("id",Integer.class)))){
            if(userDto.getRank().getName().equals(user.getRank().getName())){
                //
            }else{
                throw new BlockedException(String.format("User can't change his rank"));
            }

            if(userDto.getRole().equals(user.getRole().getName().name())){
                return userMapper.userToUserDto(userRepository.save(userMapper.userDtoToUser(userDto)));
            }else{
                throw new BlockedException(String.format("User can't change his role"));
            }
        }else{
            throw new BlockedException(String.format("This user can only change his own data"));
        }

    }

    @Override
    public UserDto getUser(int id) {
        return userMapper.userToUserDto(userRepository.findUserById((long)id).orElseThrow(()->new NotFoundException("User not found")));
    }

    @Override
    public BlockedDto block(UserDto userDto) {

        Blocked blocked = blockedRepository.findById(1).get();

        List<User> blockedUsers;
        blockedUsers = blocked.getBlockedUsers();
        blockedUsers.add(userMapper.userDtoToUser(userDto));
        blocked.setBlockedUsers(blockedUsers);

        return userMapper.blockedToBlockedDto(blockedRepository.save(blocked));

    }

    @Override
    public BlockedDto unblock(UserDto userDto) {

        Blocked blocked = blockedRepository.findById(1).get();

        List<User> blockedUsers;
        blockedUsers = blocked.getBlockedUsers();
        blockedUsers.remove(userMapper.userDtoToUser(userDto));
        blocked.setBlockedUsers(blockedUsers);

        return userMapper.blockedToBlockedDto(blockedRepository.save(blocked));

    }

    @Override
    public BlockedDto getBlocked(){
        Blocked blocked = blockedRepository.findById(1).get();

        return userMapper.blockedToBlockedDto(blocked);
    }

    @Override
    public RankDto changeRank(RankDto rankDto){
        return userMapper.rankToRankDto(rankRepository.save(userMapper.rankDtoToRank(rankDto)));

    }

    @Override
    public UserDto changeRes(int id, boolean addition) {

        User user = userRepository.findUserById((long)id).orElseThrow(()->new NotFoundException("Korisnik ne postoji"));

        if(addition){
            user.setNumberOfReservations(user.getNumberOfReservations()+1);
        }else{
            user.setNumberOfReservations(user.getNumberOfReservations()-1);
        }

        //Menjanje ranga

        List<Rank> ranks = new ArrayList<>();
        ranks = rankRepository.findAll(Sort.by("value").ascending());

        ranks.forEach((r)->{
            if(user.getNumberOfReservations()>=r.getValue()){
                user.setRank(r);
            }
        ;});

        return userMapper.userToUserDto(userRepository.save(user));
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

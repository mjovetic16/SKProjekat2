package com.example.skpr2.skprojekat2notificationservice.service.impl;

import com.example.skpr2.skprojekat2notificationservice.domain.Notification;
import com.example.skpr2.skprojekat2notificationservice.domain.NotificationType;
import com.example.skpr2.skprojekat2notificationservice.domain.Parameter;
import com.example.skpr2.skprojekat2notificationservice.dto.*;
import com.example.skpr2.skprojekat2notificationservice.exception.NotFoundException;
import com.example.skpr2.skprojekat2notificationservice.mapper.NotificationMapper;
import com.example.skpr2.skprojekat2notificationservice.repository.NotificationRepository;
import com.example.skpr2.skprojekat2notificationservice.repository.NotificationTypeRepository;
import com.example.skpr2.skprojekat2notificationservice.repository.ParameterRepository;
import com.example.skpr2.skprojekat2notificationservice.service.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private NotificationMapper notificationMapper;
    private NotificationRepository notificationRepository;
    private NotificationTypeRepository notificationTypeRepository;
    private ParameterRepository parameterRepository;

    public NotificationServiceImpl(NotificationMapper notificationMapper, NotificationRepository notificationRepository,
                                   NotificationTypeRepository notificationTypeRepository, ParameterRepository parameterRepository) {

        this.notificationMapper = notificationMapper;
        this.notificationRepository = notificationRepository;
        this.notificationTypeRepository = notificationTypeRepository;
        this.parameterRepository = parameterRepository;
    }


    @Override
    public Page<NotificationTypeDto> findAll(Pageable pageable) {

        return notificationTypeRepository.findAll(pageable).map(notificationMapper::notificationTypeToNotificationTypeDto);
    }

    @Override
    public NotificationTypeDto addType(NotificationTypeDto notificationTypeDto) {


        ArrayList<Parameter> parameters = new ArrayList<>();

        for(ParameterDto p:notificationTypeDto.getParameters()){
            if(parameterRepository.findByName(p.getName()).isPresent()){
                parameters.add(notificationMapper.parameterDtoToParameter(p));
            }else{
                p.setId(null);
                parameters.add(parameterRepository.save(notificationMapper.parameterDtoToParameter(p)));
            }
        }
        notificationTypeDto.setParameters(parameters.stream().map(notificationMapper::parameterToParameterDto).collect(Collectors.toList()));



        return notificationMapper.notificationTypeToNotificationTypeDto(notificationTypeRepository
                                                                    .save(notificationMapper.notificationTypeDtoToNotificationType(notificationTypeDto)));

    }

    @Override
    public NotificationTypeDto deleteType(NotificationTypeDto notificationTypeDto) {

        notificationTypeRepository.delete(notificationTypeRepository.findById(notificationTypeDto.getId())
                .orElseThrow(()->new NotFoundException("Notification type doesn't exist")));

        return notificationTypeDto;
    }

    @Override
    public Notification getRegisterNotification(UserDto userDto) {

        Notification notification = new Notification();

        NotificationType notificationType= notificationTypeRepository.findByName("Register").orElseThrow(()->new NotFoundException("Notfication type doesn't exist"));

        notification.setDate(new Date());
        notification.setNotificationType(notificationType);
        notification.setUserID(userDto.getId());
        notification.setEmail(userDto.getEmail());

        String text = notificationType.getTemplate();

        NotificationTypeDto notificationTypeDto = new NotificationTypeDto();
        notificationTypeDto= notificationMapper.notificationTypeToNotificationTypeDto(notificationType);

        List<ParameterDto> parameters = notificationTypeDto.getParameters();



        for(ParameterDto p: parameters){
            if(p.getName().equals("%name"))
                p.setValue(userDto.getFirstName());
            else if(p.getName().equals("%link"))
                p.setValue("http://localhost:8083/users/user/confirm/"+userDto.getId());
        }

        for(ParameterDto p: parameters){
            text = text.replace(p.getName(),p.getValue());
        }


        notification.setText(text+"\n\n Original Recepient: "+userDto.getEmail());

        notificationRepository.save(notification);

        return notification;
    }

    @Override
    public Notification getReservationNotification(ReservationUserDto reservationUserDto) {
        Notification notification = new Notification();

        NotificationType notificationType= notificationTypeRepository.findByName("Confirm Reservation").orElseThrow(()->new NotFoundException("Notfication type doesn't exist"));

        UserDto userDto = reservationUserDto.getUserDto();

        notification.setDate(new Date());
        notification.setNotificationType(notificationType);
        notification.setUserID(userDto.getId());
        notification.setEmail(userDto.getEmail());

        String text = notificationType.getTemplate();

        NotificationTypeDto notificationTypeDto = new NotificationTypeDto();
        notificationTypeDto = notificationMapper.notificationTypeToNotificationTypeDto(notificationType);

        List<ParameterDto> parameters = notificationTypeDto.getParameters();

        System.out.println(userDto);

        for(ParameterDto p: parameters){
            switch (p.getName()) {
                case "%name":
                    p.setValue(userDto.getFirstName());
                    break;
                case "%date":
                    p.setValue(reservationUserDto.getTerminDto().getDay().toString());
                    break;
                case "%hotel":
                    p.setValue(reservationUserDto.getTerminDto().getHotel().getName());
                    break;
                case "%roomType":
                    p.setValue(reservationUserDto.getTerminDto().getAccommodationDto().getRoomType().getName());
                    break;
                case "%price":
                    p.setValue(String.valueOf(reservationUserDto.getPrice()));
                    break;
            }
            System.out.println(p);
        }

        for(ParameterDto p: parameters){
            System.out.println(text);
            System.out.println(p);
            text = text.replace(p.getName(),p.getValue());
        }


        notification.setText(text+"\n\n Original Recepient: "+userDto.getEmail());

        notificationRepository.save(notification);

        return notification;
    }

    @Override
    public Notification getCanceledReservationNotification(ReservationUserDto reservationUserDto) {
        Notification notification = new Notification();

        NotificationType notificationType= notificationTypeRepository.findByName("Cancel Reservation").orElseThrow(()->new NotFoundException("Notfication type doesn't exist"));

        UserDto userDto = reservationUserDto.getUserDto();

        notification.setDate(new Date());
        notification.setNotificationType(notificationType);
        notification.setUserID(userDto.getId());
        notification.setEmail(userDto.getEmail());

        String text = notificationType.getTemplate();

        NotificationTypeDto notificationTypeDto = new NotificationTypeDto();
        notificationTypeDto = notificationMapper.notificationTypeToNotificationTypeDto(notificationType);

        List<ParameterDto> parameters = notificationTypeDto.getParameters();

        for(ParameterDto p: parameters){
            switch (p.getName()) {
                case "%name":
                    p.setValue(userDto.getFirstName());
                    break;
                case "%date":
                    p.setValue(reservationUserDto.getTerminDto().getDay().toString());
                    break;
                case "%hotel":
                    p.setValue(reservationUserDto.getTerminDto().getHotel().getName());
                    break;
                case "%roomType":
                    p.setValue(reservationUserDto.getTerminDto().getAccommodationDto().getRoomType().getName());
                    break;
                case "%price":
                    p.setValue(String.valueOf(reservationUserDto.getPrice()));
                    break;
            }
        }

        for(ParameterDto p: parameters){
            text = text.replace(p.getName(),p.getValue());
        }


        notification.setText(text+"\n\n Original Recepient: "+userDto.getEmail());

        notificationRepository.save(notification);

        return notification;
    }

    @Override
    public List<UserDto> getManagers(){
        //Get user from User Service
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<ManagerContainerDto> response = restTemplate.exchange("http://localhost:8083/users/user/manager/all", HttpMethod.GET, request, ManagerContainerDto.class);
        ManagerContainerDto managerContainerDto = response.getBody();

        if(managerContainerDto==null){
            throw new NotFoundException("No managers found");
        }

        return managerContainerDto.getManagers();
    }

    @Override
    public Page<NotificationDto> getAllNotificationsFitlered(String authorization, FilterDto filterDto, Pageable pageable) {
        //TODO implement notification filtering
        return null;
    }

    @Override
    public Notification getResetNotification(UserDto userDto) {
        Notification notification = new Notification();

        NotificationType notificationType= notificationTypeRepository.findByName("Reset").orElseThrow(()->new NotFoundException("Notfication type doesn't exist"));

        notification.setDate(new Date());
        notification.setNotificationType(notificationType);
        notification.setUserID(userDto.getId());
        notification.setEmail(userDto.getEmail());

        String text = notificationType.getTemplate();

        NotificationTypeDto notificationTypeDto = new NotificationTypeDto();
        notificationTypeDto= notificationMapper.notificationTypeToNotificationTypeDto(notificationType);

        List<ParameterDto> parameters = notificationTypeDto.getParameters();



        for(ParameterDto p: parameters){
            if(p.getName().equals("%name"))
                p.setValue(userDto.getFirstName());
            else if(p.getName().equals("%pass"))
                p.setValue("kDbjsdg12534");
        }

        for(ParameterDto p: parameters){
            System.out.println(p);
            text = text.replace(p.getName(),p.getValue());
        }


        notification.setText(text+"\n\n Original Recepient: "+userDto.getEmail());

        notificationRepository.save(notification);

        return notification;
    }
}

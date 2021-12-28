package com.example.skpr2.skprojekat2notificationservice.mapper;

import com.example.skpr2.skprojekat2notificationservice.domain.Notification;
import com.example.skpr2.skprojekat2notificationservice.domain.NotificationType;
import com.example.skpr2.skprojekat2notificationservice.domain.Parameter;
import com.example.skpr2.skprojekat2notificationservice.dto.NotificationDto;
import com.example.skpr2.skprojekat2notificationservice.dto.NotificationTypeDto;
import com.example.skpr2.skprojekat2notificationservice.dto.ParameterDto;
import com.example.skpr2.skprojekat2notificationservice.repository.NotificationRepository;
import com.example.skpr2.skprojekat2notificationservice.repository.NotificationTypeRepository;
import com.example.skpr2.skprojekat2notificationservice.repository.ParameterRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class NotificationMapper {

    private NotificationRepository notificationRepository;
    private NotificationTypeRepository notificationTypeRepository;
    private ParameterRepository parameterRepository;


    public NotificationMapper(NotificationRepository notificationRepository, NotificationTypeRepository notificationTypeRepository,
                              ParameterRepository parameterRepository){

        this.notificationRepository = notificationRepository;
        this.notificationTypeRepository = notificationTypeRepository;
        this.parameterRepository = parameterRepository;

    }

    public Notification notificationDtoToNorification(NotificationDto notificationDto){
        Notification notification = new Notification();

        notification.setNotificationType(notificationTypeDtoToNotificationType(notificationDto.getNotificationType()));
        notification.setId(notificationDto.getId());
        notification.setEmail(notificationDto.getEmail());
        notification.setText(notificationDto.getText());

        return notification;
    }

    public NotificationDto notificationToNotificationDto(Notification notification){
        NotificationDto notificationDto = new NotificationDto();

        notificationDto.setNotificationType(notificationTypeToNotificationTypeDto(notification.getNotificationType()));
        notificationDto.setId(notification.getId());
        notificationDto.setEmail(notification.getEmail());
        notification.setText(notification.getText());

        return notificationDto;

    }

    public NotificationType notificationTypeDtoToNotificationType(NotificationTypeDto notificationTypeDto){
        NotificationType notificationType = new NotificationType();

        notificationType.setId(notificationTypeDto.getId());
        notificationType.setTemplate(notificationTypeDto.getTemplate());

        ArrayList<Parameter> parameters = new ArrayList<Parameter>();

        notificationTypeDto.getParameters().forEach((p)->{
            parameters.add(parameterDtoToParameter(p));
        });
        notificationType.setParameters(parameters);

        return notificationType;
    }

    public NotificationTypeDto notificationTypeToNotificationTypeDto(NotificationType notificationType){
        NotificationTypeDto notificationTypeDto = new NotificationTypeDto();

        notificationTypeDto.setId(notificationType.getId());
        notificationTypeDto.setTemplate(notificationType.getTemplate());

        ArrayList<Parameter> parameters = new ArrayList<Parameter>();

        ArrayList<ParameterDto> parameterDtos = new ArrayList<ParameterDto>();

        notificationType.getParameters().forEach((p)->{
            parameterDtos.add(parameterToParameterDto(p));
        });
        notificationTypeDto.setParameters(parameterDtos);

        return notificationTypeDto;
    }

    public Parameter parameterDtoToParameter(ParameterDto parameterDto){
        Parameter parameter = new Parameter();

        parameter.setId(parameterDto.getId());
        parameter.setName(parameterDto.getName());
        parameter.setValue(parameterDto.getValue());

        return parameter;

    }

    public ParameterDto parameterToParameterDto(Parameter parameter){
        ParameterDto parameterDto = new ParameterDto();

        parameterDto.setId(parameter.getId());
        parameterDto.setName(parameter.getName());
        parameterDto.setValue(parameter.getValue());

        return parameterDto;

    }





}

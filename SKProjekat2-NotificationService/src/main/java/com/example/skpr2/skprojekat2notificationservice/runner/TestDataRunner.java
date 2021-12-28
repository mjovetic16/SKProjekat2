package com.example.skpr2.skprojekat2notificationservice.runner;

import com.example.skpr2.skprojekat2notificationservice.domain.Notification;
import com.example.skpr2.skprojekat2notificationservice.domain.NotificationType;
import com.example.skpr2.skprojekat2notificationservice.domain.Parameter;
import com.example.skpr2.skprojekat2notificationservice.mapper.NotificationMapper;
import com.example.skpr2.skprojekat2notificationservice.repository.NotificationRepository;
import com.example.skpr2.skprojekat2notificationservice.repository.NotificationTypeRepository;
import com.example.skpr2.skprojekat2notificationservice.repository.ParameterRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Profile({"default"})
@Component
public class TestDataRunner implements CommandLineRunner {

    private NotificationMapper notificationMapper;
    private NotificationRepository notificationRepository;
    private NotificationTypeRepository notificationTypeRepository;
    private ParameterRepository parameterRepository;

    public TestDataRunner(NotificationMapper notificationMapper, NotificationRepository notificationRepository,
                          NotificationTypeRepository notificationTypeRepository, ParameterRepository parameterRepository) {

        this.notificationMapper = notificationMapper;
        this.notificationRepository = notificationRepository;
        this.notificationTypeRepository = notificationTypeRepository;
        this.parameterRepository = parameterRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        //Dodavanje parametara u bazu
        Parameter pName = new Parameter();
        Parameter pLink = new Parameter();
        Parameter pDate = new Parameter();
        Parameter pHotel = new Parameter();
        Parameter pRoomType = new Parameter();
        Parameter pPrice = new Parameter();

        pName.setName("%name");
        pLink.setName("%link");
        pDate.setName("%date");
        pHotel.setName("%hotel");
        pRoomType.setName("%roomType");
        pPrice.setName("%price");

        pName.setId(1L);
        pLink.setId(2L);
        pDate.setId(3L);
        pHotel.setId(4L);
        pRoomType.setId(5L);
        pPrice.setId(6L);

        parameterRepository.save(pName);
        parameterRepository.save(pLink);
        parameterRepository.save(pDate);
        parameterRepository.save(pHotel);
        parameterRepository.save(pRoomType);
        parameterRepository.save(pPrice);

        //Dodavanje tipova notifikacija u bazu
        NotificationType nRegister = new NotificationType();
        nRegister.setName("Register");
        nRegister.setId(1L);
        nRegister.setTemplate("Hello %name, please click the link to confirm your registration\n" +
                "%link");
        ArrayList <Parameter> nrParams = new ArrayList<>();
        nrParams.add(pName);
        nrParams.add(pLink);
        nRegister.setParameters(nrParams);
        //

        NotificationType nConfirm = new NotificationType();
        nConfirm.setName("Confirm Reservation");
        nConfirm.setId(2L);
        nConfirm.setTemplate("Hello %name, your reservation has been completed successfully.\n" +
                "\n" +
                "Date: %date\n" +
                "Hotel: %hotel\n" +
                "Room Type: %roomType\n" +
                "Price: %price $");
        ArrayList <Parameter> ncParams = new ArrayList<>();
        ncParams.add(pName);
        ncParams.add(pDate);
        ncParams.add(pHotel);
        ncParams.add(pRoomType);
        ncParams.add(pPrice);
        nConfirm.setParameters(ncParams);
        //

        NotificationType nCancel = new NotificationType();
        nCancel.setName("Cancel Reservation");
        nCancel.setId(3L);
        nCancel.setTemplate("Hello %name, your reservation has been cancelled.\n" +
                "\n" +
                "Date: %date\n" +
                "Hotel: %hotel\n" +
                "Room Type: %roomType\n");

        ArrayList <Parameter> nccPar = new ArrayList<>();
        nccPar.add(pName);
        nccPar.add(pDate);
        nccPar.add(pHotel);
        nccPar.add(pRoomType);
        nccPar.add(pPrice);
        nCancel.setParameters(nccPar);
        //


        NotificationType nReset = new NotificationType();
        nReset.setName("Reset Password");
        nReset.setId(4L);
        nReset.setTemplate("Hello %name, please click the link to reset your password\n" +
                "%link");
        ArrayList <Parameter> nrrPar = new ArrayList<>();
        nrrPar.add(pName);
        nrrPar.add(pLink);
        nReset.setParameters(nrrPar);
        //


        NotificationType nRemind = new NotificationType();
        nRemind.setName("Reservation Reminder");
        nRemind.setId(5L);
        nRemind.setTemplate("Hello %name, we want to remind you that your reservation is due in 2 days.\n" +
                "\n" +
                "Date: %date\n" +
                "Hotel: %hotel\n" +
                "Room Type: %roomType\n" +
                "Price: %price $");
        ArrayList <Parameter> nrrParams = new ArrayList<>();
        nrrParams.add(pName);
        nrrParams.add(pDate);
        nrrParams.add(pHotel);
        nrrParams.add(pRoomType);
        nrrParams.add(pPrice);
        nRemind.setParameters(nrrParams);

        notificationTypeRepository.save(nRegister);
        notificationTypeRepository.save(nConfirm);
        notificationTypeRepository.save(nCancel);
        notificationTypeRepository.save(nReset);
        notificationTypeRepository.save(nRemind);



    }
}

package com.example.skpr2.skprojekat2userservice.runner;


import com.example.skpr2.skprojekat2userservice.domain.*;
import com.example.skpr2.skprojekat2userservice.repository.BlockedRepository;
import com.example.skpr2.skprojekat2userservice.repository.RankRepository;
import com.example.skpr2.skprojekat2userservice.repository.RoleRepository;
import com.example.skpr2.skprojekat2userservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Profile({"default"})
@Component
public class TestDataRunner implements CommandLineRunner {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private BlockedRepository blockedRepository;
    private RankRepository rankRepository;


    public TestDataRunner(RoleRepository roleRepository, UserRepository userRepository, BlockedRepository blockedRepository,
                          RankRepository rankRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.blockedRepository = blockedRepository;
        this.rankRepository = rankRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //Insert roles
        Role roleClient = new Role(RoleType.ROLE_CLIENT, "Client role");
        Role roleAdmin = new Role(RoleType.ROLE_ADMIN, "Admin role");
        Role roleManager = new Role(RoleType.ROLE_MANAGER, "Manager role");

        roleClient.setId(1L);
        roleAdmin.setId(2L);
        roleManager.setId(3L);

        roleRepository.save(roleClient);
        roleRepository.save(roleAdmin);
        roleRepository.save(roleManager);

        //Insert blocklist
        Blocked b = new Blocked();
        b.setId(1);
        b.setBlockedUsers(new ArrayList<User>());

        blockedRepository.save(b);


        //Insert Rank
        Rank rank = new Rank();
        rank.setId(1L);
        rank.setName("Gold");
        rank.setValue(15);
        rank.setDiscount(20);


        Rank rank2 = new Rank();
        rank2.setId(2L);
        rank2.setName("Silver");
        rank2.setValue(7);
        rank2.setDiscount(10);


        Rank rank3 = new Rank();
        rank3.setId(3L);
        rank3.setName("Bronze");
        rank3.setValue(0);
        rank3.setDiscount(5);

        rankRepository.save(rank);
        rankRepository.save(rank2);
        rankRepository.save(rank3);




        //Insert admin

        User admin = new User();
        admin.setRank(rank);
        admin.setEmail("admin@gmail.com");
        admin.setFirstName("Admin");
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setRole(roleAdmin);
        admin.setNumberOfReservations(7);
        userRepository.save(admin);

        //Insert manager

        User manager = new User();
        manager.setRank(rank2);
        manager.setEmail("manager@gmail.com");
        manager.setUsername("manager");
        manager.setPassword("manager");
        manager.setRole(roleManager);
        manager.setNumberOfReservations(7);
        userRepository.save(manager);
//        //User statuses
//        userStatusRepository.save(new UserStatus(0, 5, 0));
//        userStatusRepository.save(new UserStatus(6, 10, 10));
//        userStatusRepository.save(new UserStatus(11, 20, 20));
    }
}

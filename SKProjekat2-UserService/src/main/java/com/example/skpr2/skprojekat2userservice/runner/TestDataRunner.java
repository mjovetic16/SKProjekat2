package com.example.skpr2.skprojekat2userservice.runner;


import com.example.skpr2.skprojekat2userservice.domain.Blocked;
import com.example.skpr2.skprojekat2userservice.domain.Role;
import com.example.skpr2.skprojekat2userservice.domain.RoleType;
import com.example.skpr2.skprojekat2userservice.domain.User;
import com.example.skpr2.skprojekat2userservice.repository.BlockedRepository;
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


    public TestDataRunner(RoleRepository roleRepository, UserRepository userRepository, BlockedRepository blockedRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.blockedRepository = blockedRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //Insert roles
        Role roleClient = new Role(RoleType.ROLE_CLIENT, "Client role");
        Role roleAdmin = new Role(RoleType.ROLE_ADMIN, "Admin role");
        Role roleManager = new Role(RoleType.ROLE_MANAGER, "Manager role");

        roleRepository.save(roleClient);
        roleRepository.save(roleAdmin);
        roleRepository.save(roleManager);

        //Insert blocklist
        Blocked b = new Blocked();
        b.setId(1);
        b.setBlockedUsers(new ArrayList<User>());

        blockedRepository.save(b);

        //Insert admin
        User admin = new User();
        admin.setEmail("admin@gmail.com");
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setRole(roleAdmin);
        admin.setNumberOfReservations(7);
        userRepository.save(admin);
//        //User statuses
//        userStatusRepository.save(new UserStatus(0, 5, 0));
//        userStatusRepository.save(new UserStatus(6, 10, 10));
//        userStatusRepository.save(new UserStatus(11, 20, 20));
    }
}

package com.example.skpr2.skprojekat2userservice.repository;

import com.example.skpr2.skprojekat2userservice.domain.Blocked;
import com.example.skpr2.skprojekat2userservice.domain.Role;
import com.example.skpr2.skprojekat2userservice.domain.RoleType;
import com.example.skpr2.skprojekat2userservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlockedRepository extends JpaRepository<Blocked, Integer> {

    Optional<Blocked> findById(int id);

    Optional<Blocked> findByBlockedUsersContains(User user);

}
package com.example.skpr2.skprojekat2userservice.repository;

import com.example.skpr2.skprojekat2userservice.domain.Role;
import com.example.skpr2.skprojekat2userservice.domain.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findRoleByName(RoleType name);
}

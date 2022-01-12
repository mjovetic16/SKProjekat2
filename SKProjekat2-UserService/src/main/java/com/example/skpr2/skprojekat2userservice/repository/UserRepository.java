package com.example.skpr2.skprojekat2userservice.repository;

import com.example.skpr2.skprojekat2userservice.domain.Role;
import com.example.skpr2.skprojekat2userservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsernameAndPassword(String username, String password);
    Optional<User> findUserByEmailAndPassword(String email, String password);
    Optional<User> findUserById(Long id);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUsername(String username);
    Optional<List<User>> findAllByRole(Role role);
}

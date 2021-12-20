package com.example.skpr2.skprojekat2userservice.dto;

import com.example.skpr2.skprojekat2userservice.domain.User;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

public class BlockedDto {

    private int id;

    private List<UserDto> blockedUsers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<UserDto> getBlockedUsers() {
        return blockedUsers;
    }

    public void setBlockedUsers(List<UserDto> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }
}

package com.example.skpr2.skprojekat2userservice.dto;

import java.util.List;

public class ManagerContainerDto {

    private List<UserDto> managers;


    public List<UserDto> getManagers() {
        return managers;
    }

    public void setManagers(List<UserDto> managers) {
        this.managers = managers;
    }
}

package com.example.skpr2.skprojekat2mainservice.dto;

import java.util.List;

public class AllocationDtoRequest {

    private List<AllocationDto> allocationDtos;

    public List<AllocationDto> getAllocationDtos() {
        return allocationDtos;
    }

    public void setAllocationDtos(List<AllocationDto> allocationDtos) {
        this.allocationDtos = allocationDtos;
    }
}

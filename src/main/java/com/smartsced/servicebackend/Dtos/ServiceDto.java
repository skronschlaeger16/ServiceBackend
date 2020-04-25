package com.smartsced.servicebackend.Dtos;

import lombok.Data;

@Data
public class ServiceDto {
    private int id;
    private String name;
    private int employeeId;
    private String date;
    private String address;
}

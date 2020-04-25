package com.smartsced.servicebackend.Resources;

import lombok.Data;

@Data
public class ServiceResource {
    private int id;
    private String name;
    private EmployeeResource employee;
    private String date;
    private String longitude;
    private String latitude;
}

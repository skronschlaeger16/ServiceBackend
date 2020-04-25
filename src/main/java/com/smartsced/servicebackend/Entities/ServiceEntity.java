package com.smartsced.servicebackend.Entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="services")
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMPLOYEE_ID")
    private EmployeeEntity employee;
    private String datee;
    private String longitude;
    private String latitude;
}


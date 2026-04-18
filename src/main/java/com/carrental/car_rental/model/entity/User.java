package com.carrental.car_rental.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;


    private String kycStatus = "PENDING";
    private String licenseImg;


    @Column(nullable = false)
    private boolean isActive = true;


    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

}

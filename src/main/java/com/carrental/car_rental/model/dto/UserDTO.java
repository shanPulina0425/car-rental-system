package com.carrental.car_rental.model.dto;

import lombok.Data;

@Data
public class UserDTO {


    private Long id;
    private String name;
    private String email;
    private String roleName;
}
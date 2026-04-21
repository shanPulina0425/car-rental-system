package com.carrental.car_rental.controller;


import com.carrental.car_rental.model.dto.UserDTO;
import com.carrental.car_rental.model.entity.User;
import com.carrental.car_rental.service.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {

        this.userService = userService;
    }


    @GetMapping
    public List<UserDTO> getUsers() {

        return userService.getAllUsers();
    }
}
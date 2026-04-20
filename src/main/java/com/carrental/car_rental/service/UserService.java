package com.carrental.car_rental.service;


import com.carrental.car_rental.model.dto.UserDTO;
import com.carrental.car_rental.model.entity.User;
import com.carrental.car_rental.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }


    public List<UserDTO> getAllUsers() {

        List<User> usersFromDb= userRepository.findAll();

        return usersFromDb.stream().map(user -> {
            UserDTO dto = new UserDTO();
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());


            if (user.getRole() != null) {
                dto.setRoleName(user.getRole().getName());
            }
            return dto;

        }).collect(Collectors.toList());

    }
}
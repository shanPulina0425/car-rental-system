package com.carrental.car_rental.service;

import com.carrental.car_rental.model.dto.AuthRequest;
import com.carrental.car_rental.model.dto.AuthResponse;
import com.carrental.car_rental.model.dto.UserDTO;
import com.carrental.car_rental.model.entity.User;
import com.carrental.car_rental.repository.UserRepository;
import com.carrental.car_rental.security.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    public AuthService(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    public AuthResponse login(AuthRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );


        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        String jwtToken = jwtUtils.generateToken(user.getEmail());


        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRoleName(user.getRole().getName());


        return new AuthResponse(jwtToken, userDTO);
    }
}
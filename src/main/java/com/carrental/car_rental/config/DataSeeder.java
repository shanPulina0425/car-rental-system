package com.carrental.car_rental.config;


import com.carrental.car_rental.model.entity.Role;
import com.carrental.car_rental.model.entity.User;
import com.carrental.car_rental.repository.RoleRepository;
import com.carrental.car_rental.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    public DataSeeder(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {



        if (roleRepository.findByName("SUPER_ADMIN").isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName("SUPER_ADMIN");
            roleRepository.save(adminRole);
            System.out.println(" Default Role created: SUPER_ADMIN");
        }


        if (roleRepository.findByName("CUSTOMER").isEmpty()) {
            Role customerRole = new Role();
            customerRole.setName("CUSTOMER");
            roleRepository.save(customerRole);
            System.out.println(" Default Role created: CUSTOMER");
        }

        if(roleRepository.findByName("ADMIN").isEmpty()){
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            roleRepository.save(adminRole);
            System.out.println(" Default Role created: ADMIN");


        }


        if (userRepository.findByEmail("superadmin@carrental.com").isEmpty()) {
            User adminUser = new User();
            adminUser.setName("Super Admin");
            adminUser.setEmail("superadmin@carrental.com");
            adminUser.setPassword(passwordEncoder.encode("admin123"));

            Optional<Role> adminRole = roleRepository.findByName("SUPER_ADMIN");
            adminRole.ifPresent(adminUser::setRole);

            userRepository.save(adminUser);
            System.out.println(" Default Admin User created successfully!");
        }
    }
}
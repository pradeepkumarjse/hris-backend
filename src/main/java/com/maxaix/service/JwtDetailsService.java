package com.maxaix.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.maxaix.entity.Role;
import com.maxaix.entity.User;
import com.maxaix.repository.RoleRepository;
import com.maxaix.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtDetailsService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;   

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    public Map<String, Object> save(User user, String role) {
        Map<String, Object> response = new HashMap<>();     
         response = saveUser(user, role);
        return response;
    }

    public boolean isUserAlreadyPresent(User user) {
        User rUser = userRepository.findByUsername(user.getUsername().trim());
        if (rUser == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isUserAlreadyPresentByEmail(User user) {
        User rUser = userRepository.findByUserEmail(user.getEmail().toLowerCase().trim());
        if (rUser == null) {
            return false;
        } else {
            return true;
        }
    }

    public Map<String, Object> saveUser(User user, String role) {
        log.info("Inside saveUser");
        User newUser = new User();
        User savedUser = new User();
        Role userRole = new Role();
        Map<String, Object> response = new HashMap<>();    
        if (!isUserAlreadyPresent(user) && !isUserAlreadyPresentByEmail(user)) {
          if (role.contains("admin")) {
                userRole = roleRepository.findByRoleName("SITE_ADMIN");
            } else {
                userRole = roleRepository.findByRoleName("SITE_USER");
            }
            newUser.setUsername(user.getUsername().trim());
                   newUser.setFirstname(user.getFirstname());
                    newUser.setLastname(user.getLastname());
                    newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
                    newUser.setRole(new HashSet<Role>(Arrays.asList(userRole)));
                    newUser.setEmail(user.getEmail().toLowerCase().trim());                  


            savedUser = userRepository.save(newUser);
            response.put("user", savedUser);
            response.put("status", "User created.");

        } else if (isUserAlreadyPresent(user) && isUserAlreadyPresentByEmail(user)) {
            log.info(user.getEmail() + " Both already exists. ");
            response.put("user", user.getUsername());
            response.put("status", "UserName (" + user.getUsername() + ") and Email Id(" + user.getEmail() + ") already exists.");
        } else if (isUserAlreadyPresent(user)) {
            log.info(user.getUsername() + " already exists. ");
            response.put("user", user.getUsername());
            response.put("status", user.getUsername() + " already exists. ");
        } else {
            log.info(user.getEmail() + " already exists. ");
            response.put("user", user.getEmail());
            response.put("status", user.getEmail() + " already exists. ");
        }
        return response;
    }

 

    public ResponseEntity<?> returnResponse(Map<String, Object> res) throws Exception {

        if (res.get("user") == null) {
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        } else {
            if (res.get("status").toString().equalsIgnoreCase("User created.") || res.get("status").toString().contains("updated.")) {
                return new ResponseEntity<>(res, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(res, HttpStatus.CONFLICT);
            }
        }
    }

}

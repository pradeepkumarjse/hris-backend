package com.maxaix.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.maxaix.entity.Role;
import com.maxaix.entity.User;
import com.maxaix.repository.RoleRepository;
import com.maxaix.repository.UserRepository;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	private static final Logger log = LogManager.getLogger(JwtUserDetailsService.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;


	@Autowired
	private PasswordEncoder bcryptEncoder;
        
        private Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
    return roles.stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))
            .collect(Collectors.toList());
}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				true, true, true, true, this.getAuthorities(user.getRole()));
	}
	
	public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
		User user = userRepository.findByUserEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with email: " + email);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				true, true, true, true, new ArrayList<>());
	}

	public User save(User user) {

		User newUser = new User();

		if(!isUserAlreadyPresent(user) && !isUserAlreadyPresentByEmail(user)) {

			Role userRole = roleRepository.findByRoleName("SITE_USER");
			newUser.setUsername(user.getUsername().trim());
			newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
			newUser.setRole(new HashSet<Role>(Arrays.asList(userRole)));			
			newUser.setEmail(user.getEmail().toLowerCase().trim());
                                                  
                                                     
			return userRepository.save(newUser);
		}else {
			log.info(user.getUsername() + " already exists. ");
			return null;
		}

	}

	public boolean isUserAlreadyPresent(User user) {
		User rUser = userRepository.findByUsername(user.getUsername().trim());
		if(rUser == null) {
			return false;
		}else {
			return true;
		}
	}

	public boolean isUserAlreadyPresentByEmail(User user) {
		User rUser = userRepository.findByUserEmail(user.getEmail().toLowerCase().trim());
		if(rUser == null) {
			return false;
		}else {
			return true;
		}
	}

	public User getRegisterUser(String username) {
		return userRepository.findByUsername(username); 
	}
	
	
	public Optional<User> findById(long userId) {
		return userRepository.findById(userId);
	}
	
	
}
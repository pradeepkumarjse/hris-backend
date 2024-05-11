package com.maxaix.controller;

import com.maxaix.config.JwtTokenUtil;
import com.maxaix.entity.JwtRequest;
import com.maxaix.entity.JwtResponse;
import com.maxaix.entity.Role;
import com.maxaix.service.JwtDetailsService;
import com.maxaix.service.JwtRoleDetailsService;
import com.maxaix.service.JwtUserDetailsService;
import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;


@Tag(name = "Authentication")
@RestController
@CrossOrigin(origins = "*")
@Slf4j
public class JwtAuthenticationController {
	

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	@Autowired
	private JwtDetailsService jwtDetailsService;

	@Autowired
	private JwtRoleDetailsService roleDetailsService;
	

	@Operation(summary = "Authenticate a user by username,password and generate a token")
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getEmail());
		if (userDetails.isAccountNonLocked() == Boolean.FALSE) {
			throw new Exception("User is not verified", new IllegalStateException("User is not verified"));
		}

		authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

		com.maxaix.entity.User registerUser = userDetailsService.getRegisterUser(authenticationRequest.getEmail());
                
		
		final String token = jwtTokenUtil.generateToken(userDetails,registerUser.getRole());
		JwtResponse jwtResponse = new JwtResponse(token);
		jwtResponse.setUserId(registerUser.getUserId());
		jwtResponse.setUsername(registerUser.getUsername());
		if (!CollectionUtils.isEmpty(registerUser.getRole())) {
			String roleName = registerUser.getRole().iterator().next().getRoleName();
			jwtResponse.setUserRole(roleName);
		}
		
		return ResponseEntity.ok(jwtResponse);
	}
	
	

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

	
	
	@Operation(summary = "Register a User")
	@RequestMapping(value = "/register", method = RequestMethod.POST,
			consumes = { "application/json"},
			produces = { "application/json"})
	public ResponseEntity<?> saveUser(@RequestBody com.maxaix.entity.User newUser) throws Exception {
		Map<String,Object> res = jwtDetailsService.save(newUser,"user");
		return jwtDetailsService.returnResponse(res);
	}
	
	

	@Operation(summary = "Create a new role")
	@RequestMapping(value = "/role", method = RequestMethod.POST, 
			consumes = { "application/json"},
			produces = { "application/json"})
	public ResponseEntity<?> saveRole(@RequestBody Role role) throws Exception {
		Map<String,String> response = new HashMap<>();

		if (!roleDetailsService.save(role)) {
			response.put("role", role.getRoleName());
			response.put("status", "Role already exists.");

			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}else {
			response.put("role", role.getRoleName());
			response.put("status", "Role created.");
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		}
	}

	@Operation(summary = "Get list of roles")
	@RequestMapping(method = RequestMethod.GET, 
			value = "/role", 
			produces = { "application/json"})
	public ResponseEntity<?> getAllRoles(){
		return new ResponseEntity<>(roleDetailsService.getAllRoles(), HttpStatus.OK);
	}

	
	
}
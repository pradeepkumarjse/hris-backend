package com.maxaix.entity;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse implements Serializable {

	private final String jwttoken;
	private String username;
	private Long userId;
	private String userRole;

	public JwtResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}
	
	public JwtResponse(String jwttoken, String username) {
		this.jwttoken = jwttoken;
		this.username = username;
	}

}
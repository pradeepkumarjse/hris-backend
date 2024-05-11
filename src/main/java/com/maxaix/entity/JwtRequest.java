package com.maxaix.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest  implements Serializable {
	
	private String email;
	private String password;	
                  private Integer organizationId;
}
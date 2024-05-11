package com.maxaix.config;



import com.maxaix.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.maxaix.service.JwtRoleDetailsService;

@Configuration
public class WebConfig {
    
	@Autowired
	private JwtRoleDetailsService roleDetailsService;

	
	@Bean
	public void addRoles() {
		Role role = new Role();	

		role = new Role();
		role.setRoleName("ADMIN");
		role.setDescription("ADMIN");
		System.out.println("Role SITE_ADMIN created :" + roleDetailsService.save(role));

		role = new Role();
		role.setRoleName("EMPLOYEE");
		role.setDescription("EMPLOYEE");
		System.out.println("Role EMPLOYEE created :" + roleDetailsService.save(role));

	}
		
	

	
}
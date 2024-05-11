package com.maxaix.service;

import com.maxaix.entity.Role;
import com.maxaix.repository.RoleRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class JwtRoleDetailsService {
	
	@Autowired
	private RoleRepository roleRepository;

	
	
	public Role loadRoleByRolename(String roleName) throws Exception { 
		Role role =  roleRepository.findByRoleName(roleName); 
		if (role == null) { 
			throw new Exception("Role not found with rolename: " + roleName); 
			}
		
		return role; 
		}
	 
	
	public boolean save(Role role) {
		Role newRole = new Role();
		if(!isRoleAlreadyPresent(role)) {
			newRole.setRoleName(role.getRoleName());
			newRole.setDescription(role.getDescription());
			roleRepository.save(newRole);
			return true;
		}else {
			return false;
		}
		
	}
	
	public boolean isRoleAlreadyPresent(Role role) {
		Role chkRole = roleRepository.findByRoleName(role.getRoleName());
		if(chkRole == null) {
			return false;
		}else {
			return true;
		}
	}
	
	public List<Role> getAllRoles(){
		return (List<Role>) roleRepository.findAll();
	}
}
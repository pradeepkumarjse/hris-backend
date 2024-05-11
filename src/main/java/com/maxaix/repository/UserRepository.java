package com.maxaix.repository;

import com.maxaix.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    
                 User findByUsername(String username);

	@Query("SELECT u FROM User u WHERE LOWER(u.email) = LOWER(:email)") 
	public User findByUserEmail(@Param("email") String email);
	
}

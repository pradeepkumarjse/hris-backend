package com.maxaix.service;

import com.maxaix.entity.User;
import java.util.List;
import org.springframework.data.repository.query.Param;

public interface UserService {

    User getUserByUsername(String username);

    User createUser(User user);
    
     public List<User> getAllUsers();
     
     public User getUserById(Long id);
      
     public void deleteUser(Long id);
      
     public User updateUser(Long id, User updatedUser);
     
     public User findByUserEmail(@Param("email") String email);
     
}

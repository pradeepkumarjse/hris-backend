package com.maxaix.serviceimpl;

import com.maxaix.entity.User;
import com.maxaix.exception.ResourceNotFoundException;
import com.maxaix.repository.UserRepository;
import com.maxaix.service.UserService;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.maxaix.util.EmailSender;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private EmailSender emailSender;

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User createUser(User user) {
     String generatedPassword = generateRandomPassword();
        user.setPassword(passwordEncoder.encode(generatedPassword));

        // Save the user to the database
  User savedUser = userRepository.save(user);

        // Send the email
        String subject = "Welcome to Maxaix HRIS";
        
       String body = "Dear " + user.getFirstname() + ",<br><br>"
        + "You are invited to login on Maxaix HRIS. Your login credentials are:<br><br>"
        + "<b>Email:</b> " + user.getEmail()+ "<br>"
        + "<b>Password:</b> " + generatedPassword + "<br><br>"
        + "You can now log using these credentials.";


        try {
            System.out.println("================");
            emailSender.sendEmail(user.getEmail(), subject, body);
        } catch (MessagingException | UnsupportedEncodingException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }

        
        return savedUser;
    }
    
    
    public static String generateRandomPassword() {
        int length = 10;
        String charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = new Random().nextInt(charset.length());
            password.append(charset.charAt(randomIndex));
        }

        return password.toString();
    }
   
    @Override
    public User updateUser(Long id, User updatedUser) {
       User existingUser = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));

    existingUser.setUsername(updatedUser.getUsername());
    existingUser.setPhone(updatedUser.getPhone());
    existingUser.setEmail(updatedUser.getEmail());
    existingUser.setFirstname(updatedUser.getFirstname());
    existingUser.setLastname(updatedUser.getLastname());
    existingUser.setStatus(updatedUser.getStatus());
    existingUser.setWorkLocation(updatedUser.getWorkLocation());
    existingUser.setRole(updatedUser.getRole());
    existingUser.setOrganization(updatedUser.getOrganization());
    User savedUser = userRepository.save(existingUser);
    return savedUser;
    }
    @Override
    public List<User> getAllUsers() {
        // Your logic to retrieve all users
        return userRepository.findAll();
    }

     @Override
    public User getUserById(Long id) {
        // Your logic to retrieve a user by id
        // You might want to check if the user with the given id exists, handle exceptions, etc.
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteUser(Long id) {
            // You might want to check if the user with the given id exists, handle exceptions, etc.
        userRepository.deleteById(id);
    }

    @Override
    public User findByUserEmail(String email) {
          return userRepository.findByUserEmail(email);
    }
    
}

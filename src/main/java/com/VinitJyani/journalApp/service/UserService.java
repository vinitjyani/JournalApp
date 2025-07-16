package com.VinitJyani.journalApp.service;

import com.VinitJyani.journalApp.Entity.User;
import com.VinitJyani.journalApp.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepo userRepo;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void SaveNewUSer(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("User"));
         userRepo.save(user);
    }
    public void saveuser(User user){
         userRepo.save(user);
    }



    public List<User> getall(){
        return userRepo.findAll();
    }

    public Optional<User> findbyid(ObjectId id){
        return userRepo.findById(id);
    }

    public void deletebyid(ObjectId id){
        userRepo.deleteById(id);
    }

    public User findbyusername(String username){
        return userRepo.findByUsername(username);
    }

    public User findbyUserName(String username){
        return userRepo.findByUsername(username);
    }


    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("User","ADMIN"));
        userRepo.save(user);
    }
}

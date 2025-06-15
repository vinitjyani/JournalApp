package com.VinitJyani.journalApp.controller;

import com.VinitJyani.journalApp.Entity.User;
import com.VinitJyani.journalApp.repository.UserRepo;
import com.VinitJyani.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public List<User> getallusers(){
        return userService.getall();
    }

    @PostMapping
    public void createuser(@RequestBody User user){
        userService.SaveNewUSer(user);
    }

    @PutMapping
    public ResponseEntity<User> updateuser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User userindb = userService.findbyusername(username);
        if(userindb!=null){
            userindb.setUsername(user.getUsername());
            userindb.setPassword(user.getPassword());
            userService.SaveNewUSer(userindb);
        }
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserbyId(){
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    userRepo.deleteByUsername(authentication.getName());
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}

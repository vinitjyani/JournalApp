package com.VinitJyani.journalApp.controller;


import com.VinitJyani.journalApp.Entity.User;
import com.VinitJyani.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {


    @Autowired
    private UserService userService;



    @PostMapping("/create-user")
    public void createuser(@RequestBody User user){
        userService.SaveNewUSer(user);
    }
}

package com.example.GestionStages.controllers;

import com.example.GestionStages.Services.UserService;
import com.example.GestionStages.models.Tuteur;
import com.example.GestionStages.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    UserService userService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return userService.verify(user);
    }

    @PostMapping("/register")
    public User regiseter(@RequestBody Tuteur tuteur){
        tuteur.setPassword(encoder.encode(tuteur.getPassword()));
        return userService.register(tuteur);
    }
}
package com.example.GestionStages.controllers;

import com.example.GestionStages.Services.UserService;
import com.example.GestionStages.dto.LogedUserDTO;
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
    public LogedUserDTO login(@RequestBody User user) {
        LogedUserDTO logedUser = new LogedUserDTO();
        logedUser.setUsername(user.getUsername());
        logedUser.setType("Admin"); // Fetch the user type from the database
        logedUser.setToken(userService.verify(user));
        return logedUser;
    }

    @PostMapping("/register")
    public User regiseter(@RequestBody Tuteur tuteur){
        tuteur.setPassword(encoder.encode(tuteur.getPassword()));
        return userService.addUser(tuteur);
    }
}
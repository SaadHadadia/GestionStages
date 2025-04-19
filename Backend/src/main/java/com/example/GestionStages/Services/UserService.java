package com.example.GestionStages.Services;

import com.example.GestionStages.models.User;
import com.example.GestionStages.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private JWTService jwtService;
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    UserRepository userRepository;

    public User register(User user){
        return  userRepository.save(user);
    }

    public String verify(User user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername())  ;
        } else {
            return "fail";
        }
    }
}

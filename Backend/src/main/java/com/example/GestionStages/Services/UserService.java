package com.example.GestionStages.Services;

import com.example.GestionStages.models.User;
import com.example.GestionStages.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private JWTService jwtService;
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    UserRepository userRepository;

    // Méthode pour enregistrer un utilisateur
    public User register(User user){
        return userRepository.save(user);
    }

    // Méthode pour vérifier un utilisateur (authentification)
    public String verify(User user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        } else {
            return "fail";
        }
    }

    // Méthode pour récupérer tous les utilisateurs
    public List<User> getAllUsers() {
        return userRepository.findAll();  // Cette méthode utilise le repository pour récupérer tous les utilisateurs
    }

    // Méthode pour récupérer un utilisateur par son username
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);  // Cette méthode récupère un utilisateur par son nom d'utilisateur
    }
}

package com.example.GestionStages.Services;

import com.example.GestionStages.models.Admin;
import com.example.GestionStages.models.Stagiaire;
import com.example.GestionStages.models.Tuteur;
import com.example.GestionStages.models.User;
import com.example.GestionStages.repositories.UserRepository;
import jakarta.persistence.DiscriminatorValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

    @Autowired
    private JavaMailSender emailSender;

    // Méthode pour enregistrer un utilisateur
    public User addUser(User user){
        return userRepository.save(user);
    }

    public boolean sendEmail(User user, String password) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getUsername());
            message.setSubject("Vos identifiants de compte");
            message.setText(String.format(
                "Bonjour %s %s,\n\n" +
                "Votre compte a été créé avec succès.\n" +
                "Voici vos identifiants de connexion:\n\n" +
                "Nom d'utilisateur: %s\n" +
                "Mot de passe: %s\n\n" +
                "Veuillez modifier votre mot de passe après votre première connexion.\n\n" +
                "Cordialement,\n" +
                "L'équipe de l'application de getion des stages",
                user.getFirstname(), user.getLastname(),
                user.getUsername(), password
            ));
            
            emailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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

    public String getUserType(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getClass().getAnnotation(DiscriminatorValue.class).value();
    }
}

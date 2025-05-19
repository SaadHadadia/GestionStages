package com.example.GestionStages.services;

import com.example.GestionStages.models.Periode;
import com.example.GestionStages.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private Environment env;

    public boolean sendCredentailsEmail(User user, String password) {
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

    public boolean sendStageAttEmail(User user, Periode periode) {
        try {
            String link = env.getProperty("frontend.link")+"/stage/evaluer/";
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getUsername());
            message.setSubject("Évaluation du stage");
            message.setText(String.format(
                    "Bonjour %s %s,\n\n" +
                            "Veuillez évaluer ce stage: %s\n\n" +
                            "Cordialement,\n" +
                            "L'équipe de l'application de getion des stages",
                    user.getFirstname(), user.getLastname(),
                    link+periode.getId()
            ));

            emailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}

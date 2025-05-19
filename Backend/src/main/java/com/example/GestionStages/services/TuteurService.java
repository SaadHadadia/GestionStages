package com.example.GestionStages.services;

import com.example.GestionStages.models.Tuteur;
import com.example.GestionStages.repositories.TuteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TuteurService {

    @Autowired
    private TuteurRepository tuteurRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Tuteur addTuteur(Tuteur tuteur) {
        String randomPassword = UUID.randomUUID().toString().substring(0, 8);
        tuteur.setPassword(passwordEncoder.encode(randomPassword));
        Tuteur savedTuteur = tuteurRepository.save(tuteur);
        emailService.sendCredentailsEmail(savedTuteur, randomPassword);
        return savedTuteur;
    }

    public List<Tuteur> getAllTuteurs() {
        return tuteurRepository.findAll();
    }

    public Optional<Tuteur> getTuteurByUsername(String username) {
        return tuteurRepository.findById(username);
    }

    public List<Tuteur> getTuteursByEntreprise(String entreprise) {
        return tuteurRepository.findByEntreprise(entreprise);
    }

    public Tuteur updateTuteur(Tuteur tuteur) {
        return tuteurRepository.save(tuteur);
    }

    public void deleteTuteur(String username) {
        tuteurRepository.deleteById(username);
    }
}
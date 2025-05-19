package com.example.GestionStages.services;

import com.example.GestionStages.models.Stagiaire;
import com.example.GestionStages.repositories.StagiaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StagiaireService {

    @Autowired
    private StagiaireRepository stagiaireRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Stagiaire addStagiaire(Stagiaire stagiaire) {
        String randomPassword = UUID.randomUUID().toString().substring(0, 8);
        stagiaire.setPassword(passwordEncoder.encode(randomPassword));
        Stagiaire savedStagiaire = stagiaireRepository.save(stagiaire);
        emailService.sendCredentailsEmail(savedStagiaire, randomPassword);
        return savedStagiaire;
    }

    public List<Stagiaire> getAllStagiaires() {
        return stagiaireRepository.findAll();
    }

    public Optional<Stagiaire> getStagiaireByUsername(String username) {
        return stagiaireRepository.findById(username);
    }

    public List<Stagiaire> getStagiairesByInstitution(String institution) {
        return stagiaireRepository.findByInstitution(institution);
    }

    public Stagiaire updateStagiaire(Stagiaire stagiaire) {
        return stagiaireRepository.save(stagiaire);
    }

    public void deleteStagiaire(String username) {
        stagiaireRepository.deleteById(username);
    }
}
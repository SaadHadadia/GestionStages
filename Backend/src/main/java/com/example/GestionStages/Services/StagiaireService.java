package com.example.GestionStages.Services;
import com.example.GestionStages.models.Stagiaire;
import com.example.GestionStages.repositories.StagiaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StagiaireService {

    private final StagiaireRepository stagiaireRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public StagiaireService(StagiaireRepository stagiaireRepository, PasswordEncoder passwordEncoder) {
        this.stagiaireRepository = stagiaireRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Stagiaire> getAllStagiaires() {
        return stagiaireRepository.findAll();
    }

    public Optional<Stagiaire> getStagiaireByUsername(String username) {
        return stagiaireRepository.findByUsername(username);
    }

    public List<Stagiaire> getStagiairesByInstitution(String institution) {
        return stagiaireRepository.findByInstitution(institution);
    }

    public List<Stagiaire> getStagiairesByStageId(Long stageId) {
        return stagiaireRepository.findAllByStageId(stageId);
    }

    public List<Stagiaire> getStagiairesByTuteurId(String tuteurId) {
        return stagiaireRepository.findAllByTuteurId(tuteurId);
    }

    public Stagiaire saveStagiaire(Stagiaire stagiaire) {
        // Encoder le mot de passe avant de sauvegarder
        stagiaire.setPassword(passwordEncoder.encode(stagiaire.getPassword()));
        return stagiaireRepository.save(stagiaire);
    }

    public void deleteStagiaire(String username) {
        stagiaireRepository.deleteById(username);
    }
}

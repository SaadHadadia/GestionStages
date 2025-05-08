package com.example.GestionStages.Services;

import com.example.GestionStages.models.Appreciation;
import com.example.GestionStages.models.Tuteur;
import com.example.GestionStages.repositories.TuteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TuteurService {

    private final TuteurRepository tuteurRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public TuteurService(TuteurRepository tuteurRepository, PasswordEncoder passwordEncoder) {
        this.tuteurRepository = tuteurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Récupérer tous les tuteurs
    public List<Tuteur> getAllTuteurs() {
        return tuteurRepository.findAll();
    }

    // Récupérer un tuteur par son username
    public Optional<Tuteur> getTuteurByUsername(String username) {
        return tuteurRepository.findByUsername(username);
    }

    // Récupérer les tuteurs par entreprise
    public List<Tuteur> getTuteursByEntreprise(String entreprise) {
        return tuteurRepository.findByEntreprise(entreprise);
    }

    // Récupérer les tuteurs par ID de période
    public List<Tuteur> getTuteursByPeriodeId(Long periodeId) {
        return tuteurRepository.findAllByPeriodeId(periodeId);
    }

    // Sauvegarder un tuteur (utilisé pour créer ou mettre à jour un tuteur)
    private Tuteur saveTuteur(Tuteur tuteur) {
        // Encoder le mot de passe avant de sauvegarder
        tuteur.setPassword(passwordEncoder.encode(tuteur.getPassword()));  // Encoder le mot de passe
        return tuteurRepository.save(tuteur);
    }

    // Créer un nouveau tuteur
    public Tuteur createTuteur(Tuteur tuteur) {
        // Vérifier si un tuteur avec le même username existe déjà
        if (tuteurRepository.findByUsername(tuteur.getUsername()).isPresent()) {
            throw new RuntimeException("Un tuteur avec ce username existe déjà");
        }

        // Si le mot de passe est modifié, il doit être encodé avant la sauvegarde
        return saveTuteur(tuteur);
    }

    // Mettre à jour un tuteur existant
    public Tuteur updateTuteur(String username, Tuteur tuteur) {
        Optional<Tuteur> existingTuteurOpt = tuteurRepository.findByUsername(username);

        if (existingTuteurOpt.isEmpty()) {
            throw new RuntimeException("Le tuteur avec ce username n'existe pas");
        }

        Tuteur existingTuteur = existingTuteurOpt.get();

        // Mettre à jour les informations
        existingTuteur.setFirstname(tuteur.getFirstname());
        existingTuteur.setLastname(tuteur.getLastname());
        existingTuteur.setEntreprise(tuteur.getEntreprise());

        // Si le mot de passe est modifié, le réencoder
        if (!tuteur.getPassword().equals(existingTuteur.getPassword())) {
            existingTuteur.setPassword(passwordEncoder.encode(tuteur.getPassword()));
        }

        // Mettre à jour la liste des appréciations (si nécessaire)
        if (tuteur.getAppreciations() != null) {
            existingTuteur.setAppreciations(tuteur.getAppreciations());
        }

        return saveTuteur(existingTuteur);
    }

    // Supprimer un tuteur par son username
    public void deleteTuteur(String username) {
        tuteurRepository.deleteById(username);
    }

    // Vérifier s'il existe une appréciation pour une période donnée pour un tuteur
    public boolean existsAppreciationForPeriode(String tuteurId, Long periodeId) {
        return tuteurRepository.existsAppreciationForPeriode(tuteurId, periodeId);
    }
}

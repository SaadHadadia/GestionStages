package com.example.GestionStages.services;

import com.example.GestionStages.models.Categorie;
import com.example.GestionStages.models.Competence;
import com.example.GestionStages.repositories.CompetenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CompetenceService {

    @Autowired
    private CompetenceRepository competenceRepository;

    public Competence addCompetence(Competence competence) {
        return competenceRepository.save(competence);
    }

    public List<Competence> getAllCompetences() {
        return competenceRepository.findAll();
    }

    public Optional<Competence> getCompetenceById(Long id) {
        return competenceRepository.findById(id);
    }

    public List<Competence> getCompetencesByCategorie(Categorie categorie) {
        return competenceRepository.findByCategorie(categorie);
    }

    public Competence updateCompetence(Competence competence) {
        return competenceRepository.save(competence);
    }

    public void deleteCompetence(Long id) {
        competenceRepository.deleteById(id);
    }
}
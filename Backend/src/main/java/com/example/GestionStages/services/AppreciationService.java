package com.example.GestionStages.services;

import com.example.GestionStages.models.Appreciation;
import com.example.GestionStages.models.Competence;
import com.example.GestionStages.models.Periode;
import com.example.GestionStages.models.Tuteur;
import com.example.GestionStages.repositories.AppreciationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AppreciationService {

    @Autowired
    private AppreciationRepository appreciationRepository;

    public Appreciation addAppreciation(Appreciation appreciation) {
        return appreciationRepository.save(appreciation);
    }

    public List<Appreciation> getAllAppreciations() {
        return appreciationRepository.findAll();
    }

    public Optional<Appreciation> getAppreciationById(Long id) {
        return appreciationRepository.findById(id);
    }

    public List<Appreciation> getAppreciationsByTuteur(Tuteur tuteur) {
        return appreciationRepository.findByTuteur(tuteur);
    }

    public List<Appreciation> getAppreciationsByPeriode(Periode periode) {
        return appreciationRepository.findByPeriode(periode);
    }

    public List<Appreciation> getAppreciationsByCompetence(Competence competence) {
        return appreciationRepository.findByCompetence(competence);
    }

    public Optional<Appreciation> getAppreciationByTuteurAndPeriode(Tuteur tuteur, Periode periode) {
        return appreciationRepository.findByTuteurAndPeriode(tuteur, periode);
    }

    public Appreciation updateAppreciation(Appreciation appreciation) {
        return appreciationRepository.save(appreciation);
    }

    public void deleteAppreciation(Long id) {
        appreciationRepository.deleteById(id);
    }
}
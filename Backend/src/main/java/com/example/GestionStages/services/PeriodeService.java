package com.example.GestionStages.services;

import com.example.GestionStages.models.Periode;
import com.example.GestionStages.models.Stage;
import com.example.GestionStages.models.Stagiaire;
import com.example.GestionStages.models.Tuteur;
import com.example.GestionStages.repositories.PeriodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PeriodeService {

    @Autowired
    private PeriodeRepository periodeRepository;

    @Autowired
    private EmailService emailService;

    public Periode addPeriode(Periode periode) {
        return periodeRepository.save(periode);
    }

    public List<Periode> getAllPeriodes() {
        return periodeRepository.findAll();
    }

    public Optional<Periode> getPeriodeById(Long id) {
        return periodeRepository.findById(id);
    }

    public List<Periode> getPeriodesByStage(Stage stage) {
        return periodeRepository.findByStage(stage);
    }

    public List<Periode> getPeriodesByStagiaire(Stagiaire stagiaire) {
        return periodeRepository.findByStagiaire(stagiaire);
    }

    public List<Periode> getPeriodesAfterDate(LocalDate date) {
        return periodeRepository.findByDateDebutAfter(date);
    }

    public List<Periode> getPeriodesBeforeDate(LocalDate date) {
        return periodeRepository.findByDateFinBefore(date);
    }

    public List<Periode> getPeriodesBetweenDates(LocalDate startDate, LocalDate endDate) {
        return periodeRepository.findByDateDebutAfterAndDateFinBefore(startDate, endDate);
    }

    public Periode updatePeriode(Periode periode) {
        return periodeRepository.save(periode);
    }

    public void deletePeriode(Long id) {
        periodeRepository.deleteById(id);
    }

    public boolean sendEvaluationRequestToTuteur(Periode periode, Tuteur tuteur) {
        return emailService.sendStageAttEmail(tuteur, periode);
    }
}
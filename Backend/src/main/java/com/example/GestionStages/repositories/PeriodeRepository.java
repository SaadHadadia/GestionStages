package com.example.GestionStages.repositories;

import com.example.GestionStages.models.Periode;
import com.example.GestionStages.models.Stage;
import com.example.GestionStages.models.Stagiaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PeriodeRepository extends JpaRepository<Periode, Long> {
    List<Periode> findByStage(Stage stage);
    List<Periode> findByStagiaire(Stagiaire stagiaire);
    List<Periode> findByDateDebutAfter(LocalDate date);
    List<Periode> findByDateFinBefore(LocalDate date);
    List<Periode> findByDateDebutAfterAndDateFinBefore(LocalDate startDate, LocalDate endDate);
}
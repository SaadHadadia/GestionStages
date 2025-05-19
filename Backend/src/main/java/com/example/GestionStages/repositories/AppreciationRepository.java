package com.example.GestionStages.repositories;

import com.example.GestionStages.models.Appreciation;
import com.example.GestionStages.models.Competence;
import com.example.GestionStages.models.Periode;
import com.example.GestionStages.models.Tuteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppreciationRepository extends JpaRepository<Appreciation, Long> {
    List<Appreciation> findByTuteur(Tuteur tuteur);
    List<Appreciation> findByPeriode(Periode periode);
    List<Appreciation> findByCompetence(Competence competence);
    Optional<Appreciation> findByTuteurAndPeriode(Tuteur tuteur, Periode periode);
}
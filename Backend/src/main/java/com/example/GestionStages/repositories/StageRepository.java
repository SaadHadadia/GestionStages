package com.example.GestionStages.repositories;

import com.example.GestionStages.models.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StageRepository extends JpaRepository<Stage, Long> {
    List<Stage> findByEntreprise(String entreprise);

    @Query("SELECT DISTINCT s FROM Stage s JOIN s.periodes p JOIN p.appreciations a WHERE a.tuteur.username = :tuteurId")
    List<Stage> findAllByTuteurId(@Param("tuteurId") String tuteurId);

    @Query("SELECT s FROM Stage s JOIN s.periodes p WHERE p.stagiaire.username = :stagiaireId")
    List<Stage> findAllByStagiaireId(@Param("stagiaireId") String stagiaireId);
}
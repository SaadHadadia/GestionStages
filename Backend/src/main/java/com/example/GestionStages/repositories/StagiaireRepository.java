package com.example.GestionStages.repositories;

import com.example.GestionStages.models.Stagiaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StagiaireRepository extends JpaRepository<Stagiaire, String> {
    Optional<Stagiaire> findByUsername(String username);
    List<Stagiaire> findByInstitution(String institution);

    @Query("SELECT s FROM Stagiaire s JOIN s.periodes p WHERE p.stage.id = :stageId")
    List<Stagiaire> findAllByStageId(@Param("stageId") Long stageId);

    @Query("SELECT s FROM Stagiaire s JOIN s.periodes p JOIN p.appreciations a WHERE a.tuteur.username = :tuteurId")
    List<Stagiaire> findAllByTuteurId(@Param("tuteurId") String tuteurId);
}
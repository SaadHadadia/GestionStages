package com.example.GestionStages.repositories;

import com.example.GestionStages.models.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    List<Evaluation> findByAppreciationId(Long appreciationId);
    List<Evaluation> findByCategorie(String categorie);

    @Query("SELECT e FROM Evaluation e JOIN e.appreciation a WHERE a.tuteur.username = :tuteurId")
    List<Evaluation> findAllByTuteurId(@Param("tuteurId") String tuteurId);

    @Query("SELECT e FROM Evaluation e JOIN e.appreciation a JOIN a.periode p WHERE p.stagiaire.username = :stagiaireId")
    List<Evaluation> findAllByStagiaireId(@Param("stagiaireId") String stagiaireId);
}
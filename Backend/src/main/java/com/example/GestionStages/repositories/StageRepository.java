package com.example.GestionStages.repositories;

import com.example.GestionStages.models.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StageRepository extends JpaRepository<Stage, Long> {
    List<Stage> findByEntreprise(String entreprise);
}
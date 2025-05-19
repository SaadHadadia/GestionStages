package com.example.GestionStages.repositories;

import com.example.GestionStages.models.Stagiaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StagiaireRepository extends JpaRepository<Stagiaire, String> {
    List<Stagiaire> findByInstitution(String institution);
}
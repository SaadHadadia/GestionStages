package com.example.GestionStages.repositories;

import com.example.GestionStages.models.Categorie;
import com.example.GestionStages.models.Competence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompetenceRepository extends JpaRepository<Competence, Long> {
    List<Competence> findByCategorie(Categorie categorie);
}
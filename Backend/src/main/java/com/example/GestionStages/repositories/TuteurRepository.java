package com.example.GestionStages.repositories;

import com.example.GestionStages.models.Tuteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TuteurRepository extends JpaRepository<Tuteur, String> {
}

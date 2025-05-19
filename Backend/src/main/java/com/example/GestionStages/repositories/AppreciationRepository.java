package com.example.GestionStages.repositories;

import com.example.GestionStages.models.Appreciation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppreciationRepository extends JpaRepository<Appreciation, Long> {
}

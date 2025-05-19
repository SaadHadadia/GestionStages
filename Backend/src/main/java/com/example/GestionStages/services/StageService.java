package com.example.GestionStages.services;

import com.example.GestionStages.models.Stage;
import com.example.GestionStages.repositories.StageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StageService {

    @Autowired
    private StageRepository stageRepository;

    public Stage addStage(Stage stage) {
        return stageRepository.save(stage);
    }

    public List<Stage> getAllStages() {
        return stageRepository.findAll();
    }

    public Optional<Stage> getStageById(Long id) {
        return stageRepository.findById(id);
    }

    public List<Stage> getStagesByEntreprise(String entreprise) {
        return stageRepository.findByEntreprise(entreprise);
    }

    public Stage updateStage(Stage stage) {
        return stageRepository.save(stage);
    }

    public void deleteStage(Long id) {
        stageRepository.deleteById(id);
    }
}
package com.example.GestionStages.Services;
import com.example.GestionStages.models.Stage;
import com.example.GestionStages.repositories.StageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StageService {

    private final StageRepository stageRepository;

    @Autowired
    public StageService(StageRepository stageRepository) {
        this.stageRepository = stageRepository;
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

    public List<Stage> getStagesByTuteurId(String tuteurId) {
        return stageRepository.findAllByTuteurId(tuteurId);
    }

    public List<Stage> getStagesByStagiaireId(String stagiaireId) {
        return stageRepository.findAllByStagiaireId(stagiaireId);
    }

    public Stage saveStage(Stage stage) {
        return stageRepository.save(stage);
    }

    public void deleteStage(Long id) {
        stageRepository.deleteById(id);
    }
}

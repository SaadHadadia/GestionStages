package com.example.GestionStages.controllers;

import com.example.GestionStages.dto.StageDTO;
import com.example.GestionStages.models.Stage;
import com.example.GestionStages.services.StageService;
import com.example.GestionStages.mappers.MapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stages")
public class StageController {

    @Autowired
    private StageService stageService;

    @Autowired
    private MapperService mapperService;

    @PostMapping
    public ResponseEntity<StageDTO> createStage(@RequestBody StageDTO stageDTO) {
        Stage stage = mapperService.toStage(stageDTO);
        Stage savedStage = stageService.addStage(stage);
        return ResponseEntity.ok(mapperService.toStageDTO(savedStage));
    }

    @GetMapping
    public ResponseEntity<List<StageDTO>> getAllStages() {
        List<Stage> stages = stageService.getAllStages();
        return ResponseEntity.ok(mapperService.toStageDTOList(stages));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StageDTO> getStageById(@PathVariable Long id) {
        Optional<Stage> stage = stageService.getStageById(id);
        return stage.map(value -> ResponseEntity.ok(mapperService.toStageDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/entreprise/{entreprise}")
    public ResponseEntity<List<StageDTO>> getStagesByEntreprise(@PathVariable String entreprise) {
        List<Stage> stages = stageService.getStagesByEntreprise(entreprise);
        return ResponseEntity.ok(mapperService.toStageDTOList(stages));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StageDTO> updateStage(@PathVariable Long id, @RequestBody StageDTO stageDTO) {
        Optional<Stage> existingStage = stageService.getStageById(id);
        if (existingStage.isPresent()) {
            Stage stage = mapperService.toStage(stageDTO);
            stage.setId(id);
            Stage updatedStage = stageService.updateStage(stage);
            return ResponseEntity.ok(mapperService.toStageDTO(updatedStage));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStage(@PathVariable Long id) {
        stageService.deleteStage(id);
        return ResponseEntity.noContent().build();
    }
}
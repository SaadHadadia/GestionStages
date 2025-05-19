package com.example.GestionStages.controllers;

import com.example.GestionStages.dto.EvaluationDTO;
import com.example.GestionStages.models.Evaluation;
import com.example.GestionStages.services.EvaluationService;
import com.example.GestionStages.mappers.MapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/evaluations")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private MapperService mapperService;

    @PostMapping
    public ResponseEntity<EvaluationDTO> createEvaluation(@RequestBody EvaluationDTO evaluationDTO) {
        Evaluation evaluation = mapperService.toEvaluation(evaluationDTO);
        Evaluation savedEvaluation = evaluationService.addEvaluation(evaluation);
        return ResponseEntity.ok(mapperService.toEvaluationDTO(savedEvaluation));
    }

    @GetMapping
    public ResponseEntity<List<EvaluationDTO>> getAllEvaluations() {
        List<Evaluation> evaluations = evaluationService.getAllEvaluations();
        return ResponseEntity.ok(mapperService.toEvaluationDTOList(evaluations));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EvaluationDTO> getEvaluationById(@PathVariable Long id) {
        Optional<Evaluation> evaluation = evaluationService.getEvaluationById(id);
        return evaluation.map(value -> ResponseEntity.ok(mapperService.toEvaluationDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/categorie/{categorie}")
    public ResponseEntity<List<EvaluationDTO>> getEvaluationsByCategorie(@PathVariable String categorie) {
        List<Evaluation> evaluations = evaluationService.getEvaluationsByCategorie(categorie);
        return ResponseEntity.ok(mapperService.toEvaluationDTOList(evaluations));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EvaluationDTO> updateEvaluation(@PathVariable Long id, @RequestBody EvaluationDTO evaluationDTO) {
        Optional<Evaluation> existingEvaluation = evaluationService.getEvaluationById(id);
        if (existingEvaluation.isPresent()) {
            Evaluation evaluation = mapperService.toEvaluation(evaluationDTO);
            evaluation.setId(id);
            Evaluation updatedEvaluation = evaluationService.updateEvaluation(evaluation);
            return ResponseEntity.ok(mapperService.toEvaluationDTO(updatedEvaluation));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvaluation(@PathVariable Long id) {
        evaluationService.deleteEvaluation(id);
        return ResponseEntity.noContent().build();
    }
}
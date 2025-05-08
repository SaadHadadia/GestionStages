package com.example.GestionStages.controllers;
import com.example.GestionStages.dto.EvaluationDTO;
import com.example.GestionStages.models.Appreciation;
import com.example.GestionStages.models.Evaluation;
import com.example.GestionStages.Services.AppreciationService;
import com.example.GestionStages.Services.EvaluationService;
import com.example.GestionStages.mappers.MapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/evaluations")
@CrossOrigin(origins = "*")
public class EvaluationController {

    private final EvaluationService evaluationService;
    private final AppreciationService appreciationService;
    private final MapperService mapperService;

    @Autowired
    public EvaluationController(EvaluationService evaluationService, AppreciationService appreciationService,
                                MapperService mapperService) {
        this.evaluationService = evaluationService;
        this.appreciationService = appreciationService;
        this.mapperService = mapperService;
    }

    @GetMapping
    public ResponseEntity<List<EvaluationDTO>> getAllEvaluations() {
        List<Evaluation> evaluations = evaluationService.getAllEvaluations();
        return ResponseEntity.ok(mapperService.toEvaluationDTOs(evaluations));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EvaluationDTO> getEvaluationById(@PathVariable Long id) {
        Optional<Evaluation> evaluationOpt = evaluationService.getEvaluationById(id);
        return evaluationOpt.map(evaluation -> ResponseEntity.ok(mapperService.toEvaluationDTO(evaluation)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/appreciation/{appreciationId}")
    public ResponseEntity<List<EvaluationDTO>> getEvaluationsByAppreciationId(@PathVariable Long appreciationId) {
        List<Evaluation> evaluations = evaluationService.getEvaluationsByAppreciationId(appreciationId);
        return ResponseEntity.ok(mapperService.toEvaluationDTOs(evaluations));
    }

    @GetMapping("/categorie/{categorie}")
    public ResponseEntity<List<EvaluationDTO>> getEvaluationsByCategorie(@PathVariable String categorie) {
        List<Evaluation> evaluations = evaluationService.getEvaluationsByCategorie(categorie);
        return ResponseEntity.ok(mapperService.toEvaluationDTOs(evaluations));
    }

    @GetMapping("/tuteur/{tuteurId}")
    public ResponseEntity<List<EvaluationDTO>> getEvaluationsByTuteurId(@PathVariable String tuteurId) {
        List<Evaluation> evaluations = evaluationService.getEvaluationsByTuteurId(tuteurId);
        return ResponseEntity.ok(mapperService.toEvaluationDTOs(evaluations));
    }

    @GetMapping("/stagiaire/{stagiaireId}")
    public ResponseEntity<List<EvaluationDTO>> getEvaluationsByStagiaireId(@PathVariable String stagiaireId) {
        List<Evaluation> evaluations = evaluationService.getEvaluationsByStagiaireId(stagiaireId);
        return ResponseEntity.ok(mapperService.toEvaluationDTOs(evaluations));
    }

    @PostMapping
    public ResponseEntity<EvaluationDTO> createEvaluation(@RequestBody EvaluationDTO evaluationDTO) {
        Optional<Appreciation> appreciationOpt = appreciationService.getAppreciationById(evaluationDTO.getAppreciationId());

        if (!appreciationOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        Evaluation evaluation = mapperService.toEvaluation(evaluationDTO, appreciationOpt.get());
        Evaluation savedEvaluation = evaluationService.saveEvaluation(evaluation);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapperService.toEvaluationDTO(savedEvaluation));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EvaluationDTO> updateEvaluation(@PathVariable Long id, @RequestBody EvaluationDTO evaluationDTO) {
        if (!evaluationService.getEvaluationById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Appreciation> appreciationOpt = appreciationService.getAppreciationById(evaluationDTO.getAppreciationId());

        if (!appreciationOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        Evaluation evaluation = mapperService.toEvaluation(evaluationDTO, appreciationOpt.get());
        evaluation.setId(id);
        Evaluation updatedEvaluation = evaluationService.saveEvaluation(evaluation);
        return ResponseEntity.ok(mapperService.toEvaluationDTO(updatedEvaluation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvaluation(@PathVariable Long id) {
        if (!evaluationService.getEvaluationById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        evaluationService.deleteEvaluation(id);
        return ResponseEntity.noContent().build();
    }
}
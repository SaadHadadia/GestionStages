package com.example.GestionStages.controllers;

import com.example.GestionStages.dto.*;
import com.example.GestionStages.models.*;
import com.example.GestionStages.services.*;
import com.example.GestionStages.mappers.MapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stages")
public class StageController {

    @Autowired
    private StageService stageService;

    @Autowired
    private MapperService mapperService;

    @Autowired
    private StagiaireService stagiaireService;

    @Autowired
    private PeriodeService periodeService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    TuteurService tuteurService;

    @Autowired
    private AppreciationService appreciationService;

   @Autowired
   private CompetenceService competenceService;

   @Autowired
   private EvaluationService evaluationService;

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

    @PostMapping("attribuer")
    public Periode attribuerStage(@RequestBody AttribuerStageDTO stageDTO) {

        Optional<Stagiaire> stagiaireOpt = stagiaireService.getStagiaireByUsername(stageDTO.getStagiaireId());
        Stagiaire stagiaire = stagiaireOpt.get();

        Optional<Tuteur> tuteurOpt = tuteurService.getTuteurByUsername(stageDTO.getTuteurId());
        Tuteur tuteur = tuteurOpt.get();

        Stage stage = new Stage();
        stage.setEntreprise(tuteur.getEntreprise());
        Stage savedStage = stageService.addStage(stage);


        Periode periode = new Periode(
                stageDTO.getDateDebut(),
                stageDTO.getDateFin(),
                savedStage,
                stagiaire
        );

        Periode savedPeriode = periodeService.addPeriode(periode);

        emailService.sendStageAttEmail(tuteur ,savedPeriode);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedPeriode).getBody();

    }

    @PostMapping("/{periodeId}/evaluer")
    public ResponseEntity<List<AppreciationDTO>> evaluerStage(@PathVariable Long periodeId, @RequestBody EvaluationStageDTO evaluationStageDTO) {
        // Validate Periode
        Optional<Periode> periodeOpt = periodeService.getPeriodeById(periodeId);
        if (periodeOpt.isEmpty() || !periodeId.equals(evaluationStageDTO.getPeriodeId())) {
            return ResponseEntity.badRequest().build();
        }
        Periode periode = periodeOpt.get();

        // Validate Tuteur
        Optional<User> tuteurOpt = userService.getUserByUsername(evaluationStageDTO.getTuteurUsername());
        if (tuteurOpt.isEmpty() || !(tuteurOpt.get() instanceof Tuteur)) {
            return ResponseEntity.badRequest().build();
        }
        Tuteur tuteur = (Tuteur) tuteurOpt.get();

        // Update Stage if provided
        if (evaluationStageDTO.getStage() != null) {
            Optional<Stage> stageOpt = stageService.getStageById(periode.getStage().getId());
            if (stageOpt.isPresent()) {
                Stage stage = stageOpt.get();
                EvaluationStageDTO.StageInputDTO stageInput = evaluationStageDTO.getStage();
                if (stageInput.getDescription() != null && !stageInput.getDescription().isEmpty()) {
                    stage.setDescription(stageInput.getDescription());
                }
                if (stageInput.getObjectif() != null && !stageInput.getObjectif().isEmpty()) {
                    stage.setObjectif(stageInput.getObjectif());
                }
                stageService.updateStage(stage);
            }
        }

        // Process each appreciation
        List<Appreciation> appreciations = new ArrayList<>();
        for (EvaluationStageDTO.AppreciationInputDTO input : evaluationStageDTO.getAppreciations()) {
            // Validate Competence
            Optional<Competence> competenceOpt = competenceService.getCompetenceById(input.getCompetenceId());
            if (competenceOpt.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            Competence competence = competenceOpt.get();

            // Validate Evaluation
            Optional<Evaluation> evaluationOpt = evaluationService.getEvaluationById(input.getEvaluationId());
            if (evaluationOpt.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            Evaluation evaluation = evaluationOpt.get();

            // Create Appreciation
            Appreciation appreciation = new Appreciation();
            appreciation.setTuteur(tuteur);
            appreciation.setPeriode(periode);
            appreciation.setCompetence(competence);
            appreciation.setEvaluation(evaluation);
            appreciations.add(appreciation);
        }

        // Save all appreciations
        List<Appreciation> savedAppreciations = appreciationService.saveAllAppreciations(appreciations);

        // Get the stagiaire

//        String stagiereUsername = evaluationStageDTO.getStage().getStagiaireId();
        String stagiereUsername = "seedspi0@gmail.com"; // Pour le Test

        Optional<Stagiaire> stagiaireOPt =  stagiaireService.getStagiaireByUsername(stagiereUsername);
        Stagiaire stagiaire = stagiaireOPt.get();
        emailService.sendStageEvvEmail(stagiaire ,periode);

        return ResponseEntity.status(HttpStatus.CREATED).body(mapperService.toAppreciationDTOList(savedAppreciations));
    }
}
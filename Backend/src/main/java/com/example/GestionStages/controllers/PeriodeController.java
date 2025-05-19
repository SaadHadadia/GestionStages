package com.example.GestionStages.controllers;

import com.example.GestionStages.dto.PeriodeDTO;
import com.example.GestionStages.models.Periode;
import com.example.GestionStages.models.Stage;
import com.example.GestionStages.models.Stagiaire;
import com.example.GestionStages.models.Tuteur;
import com.example.GestionStages.services.PeriodeService;
import com.example.GestionStages.services.StageService;
import com.example.GestionStages.services.StagiaireService;
import com.example.GestionStages.services.TuteurService;
import com.example.GestionStages.mappers.MapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/periodes")
public class PeriodeController {

    @Autowired
    private PeriodeService periodeService;

    @Autowired
    private StageService stageService;

    @Autowired
    private StagiaireService stagiaireService;

    @Autowired
    private TuteurService tuteurService;

    @Autowired
    private MapperService mapperService;

    @PostMapping
    public ResponseEntity<PeriodeDTO> createPeriode(@RequestBody PeriodeDTO periodeDTO) {
        Optional<Stage> stage = stageService.getStageById(periodeDTO.getStageId());
        Optional<Stagiaire> stagiaire = stagiaireService.getStagiaireByUsername(periodeDTO.getStagiaireUsername());
        if (stage.isPresent() && stagiaire.isPresent()) {
            Periode periode = mapperService.toPeriode(periodeDTO, stage.get(), stagiaire.get());
            Periode savedPeriode = periodeService.addPeriode(periode);
            return ResponseEntity.ok(mapperService.toPeriodeDTO(savedPeriode));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<PeriodeDTO>> getAllPeriodes() {
        List<Periode> periodes = periodeService.getAllPeriodes();
        return ResponseEntity.ok(mapperService.toPeriodeDTOList(periodes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeriodeDTO> getPeriodeById(@PathVariable Long id) {
        Optional<Periode> periode = periodeService.getPeriodeById(id);
        return periode.map(value -> ResponseEntity.ok(mapperService.toPeriodeDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/stage/{stageId}")
    public ResponseEntity<List<PeriodeDTO>> getPeriodesByStage(@PathVariable Long stageId) {
        Optional<Stage> stage = stageService.getStageById(stageId);
        if (stage.isPresent()) {
            List<Periode> periodes = periodeService.getPeriodesByStage(stage.get());
            return ResponseEntity.ok(mapperService.toPeriodeDTOList(periodes));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/stagiaire/{username}")
    public ResponseEntity<List<PeriodeDTO>> getPeriodesByStagiaire(@PathVariable String username) {
        Optional<Stagiaire> stagiaire = stagiaireService.getStagiaireByUsername(username);
        if (stagiaire.isPresent()) {
            List<Periode> periodes = periodeService.getPeriodesByStagiaire(stagiaire.get());
            return ResponseEntity.ok(mapperService.toPeriodeDTOList(periodes));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/after/{date}")
    public ResponseEntity<List<PeriodeDTO>> getPeriodesAfterDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        List<Periode> periodes = periodeService.getPeriodesAfterDate(localDate);
        return ResponseEntity.ok(mapperService.toPeriodeDTOList(periodes));
    }

    @GetMapping("/before/{date}")
    public ResponseEntity<List<PeriodeDTO>> getPeriodesBeforeDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        List<Periode> periodes = periodeService.getPeriodesBeforeDate(localDate);
        return ResponseEntity.ok(mapperService.toPeriodeDTOList(periodes));
    }

    @GetMapping("/between/{startDate}/{endDate}")
    public ResponseEntity<List<PeriodeDTO>> getPeriodesBetweenDates(@PathVariable String startDate, @PathVariable String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<Periode> periodes = periodeService.getPeriodesBetweenDates(start, end);
        return ResponseEntity.ok(mapperService.toPeriodeDTOList(periodes));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PeriodeDTO> updatePeriode(@PathVariable Long id, @RequestBody PeriodeDTO periodeDTO) {
        Optional<Periode> existingPeriode = periodeService.getPeriodeById(id);
        if (existingPeriode.isPresent()) {
            Optional<Stage> stage = stageService.getStageById(periodeDTO.getStageId());
            Optional<Stagiaire> stagiaire = stagiaireService.getStagiaireByUsername(periodeDTO.getStagiaireUsername());
            if (stage.isPresent() && stagiaire.isPresent()) {
                Periode periode = mapperService.toPeriode(periodeDTO, stage.get(), stagiaire.get());
                periode.setId(id);
                Periode updatedPeriode = periodeService.updatePeriode(periode);
                return ResponseEntity.ok(mapperService.toPeriodeDTO(updatedPeriode));
            }
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePeriode(@PathVariable Long id) {
        periodeService.deletePeriode(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{periodeId}/tuteur/{tuteurUsername}/evaluate")
    public ResponseEntity<Void> sendEvaluationRequest(@PathVariable Long periodeId, @PathVariable String tuteurUsername) {
        Optional<Periode> periode = periodeService.getPeriodeById(periodeId);
        Optional<Tuteur> tuteur = tuteurService.getTuteurByUsername(tuteurUsername);
        if (periode.isPresent() && tuteur.isPresent()) {
            boolean sent = periodeService.sendEvaluationRequestToTuteur(periode.get(), tuteur.get());
            return sent ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
        }
        return ResponseEntity.notFound().build();
    }
}
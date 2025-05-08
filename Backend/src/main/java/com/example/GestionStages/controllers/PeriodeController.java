package com.example.GestionStages.controllers;
import com.example.GestionStages.dto.PeriodeDTO;
import com.example.GestionStages.models.Periode;
import com.example.GestionStages.models.Stage;
import com.example.GestionStages.models.Stagiaire;
import com.example.GestionStages.mappers.MapperService;
import com.example.GestionStages.Services.PeriodeService;
import com.example.GestionStages.Services.StageService;
import com.example.GestionStages.Services.StagiaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/periodes")
@CrossOrigin(origins = "*")
public class PeriodeController {

    private final PeriodeService periodeService;
    private final StageService stageService;
    private final StagiaireService stagiaireService;
    private final MapperService mapperService;

    @Autowired
    public PeriodeController(PeriodeService periodeService, StageService stageService,
                             StagiaireService stagiaireService, MapperService mapperService) {
        this.periodeService = periodeService;
        this.stageService = stageService;
        this.stagiaireService = stagiaireService;
        this.mapperService = mapperService;
    }

    @GetMapping
    public ResponseEntity<List<PeriodeDTO>> getAllPeriodes() {
        List<Periode> periodes = periodeService.getAllPeriodes();
        return ResponseEntity.ok(mapperService.toPeriodeDTOs(periodes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeriodeDTO> getPeriodeById(@PathVariable Long id) {
        Optional<Periode> periodeOpt = periodeService.getPeriodeById(id);
        return periodeOpt.map(periode -> ResponseEntity.ok(mapperService.toPeriodeDTO(periode)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/stage/{stageId}")
    public ResponseEntity<List<PeriodeDTO>> getPeriodesByStageId(@PathVariable Long stageId) {
        List<Periode> periodes = periodeService.getPeriodesByStageId(stageId);
        return ResponseEntity.ok(mapperService.toPeriodeDTOs(periodes));
    }

    @GetMapping("/stagiaire/{stagiaireUsername}")
    public ResponseEntity<List<PeriodeDTO>> getPeriodesByStagiaireUsername(@PathVariable String stagiaireUsername) {
        List<Periode> periodes = periodeService.getPeriodesByStagiaireUsername(stagiaireUsername);
        return ResponseEntity.ok(mapperService.toPeriodeDTOs(periodes));
    }

    @GetMapping("/active")
    public ResponseEntity<List<PeriodeDTO>> getAllActivePeriodes() {
        List<Periode> periodes = periodeService.getAllActivePeriodes();
        return ResponseEntity.ok(mapperService.toPeriodeDTOs(periodes));
    }

    @GetMapping("/tuteur/{tuteurId}")
    public ResponseEntity<List<PeriodeDTO>> getPeriodesByTuteurId(@PathVariable String tuteurId) {
        List<Periode> periodes = periodeService.getPeriodesByTuteurId(tuteurId);
        return ResponseEntity.ok(mapperService.toPeriodeDTOs(periodes));
    }

    @PostMapping
    public ResponseEntity<PeriodeDTO> createPeriode(@RequestBody PeriodeDTO periodeDTO) {
        Optional<Stage> stageOpt = stageService.getStageById(periodeDTO.getStageId());
        Optional<Stagiaire> stagiaireOpt = stagiaireService.getStagiaireByUsername(periodeDTO.getStagiaireUsername());

        if (!stageOpt.isPresent() || !stagiaireOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        Periode periode = mapperService.toPeriode(periodeDTO, stageOpt.get(), stagiaireOpt.get());
        Periode savedPeriode = periodeService.savePeriode(periode);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapperService.toPeriodeDTO(savedPeriode));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PeriodeDTO> updatePeriode(@PathVariable Long id, @RequestBody PeriodeDTO periodeDTO) {
        if (!periodeService.getPeriodeById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Stage> stageOpt = stageService.getStageById(periodeDTO.getStageId());
        Optional<Stagiaire> stagiaireOpt = stagiaireService.getStagiaireByUsername(periodeDTO.getStagiaireUsername());

        if (!stageOpt.isPresent() || !stagiaireOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        Periode periode = mapperService.toPeriode(periodeDTO, stageOpt.get(), stagiaireOpt.get());
        periode.setId(id);
        Periode updatedPeriode = periodeService.savePeriode(periode);
        return ResponseEntity.ok(mapperService.toPeriodeDTO(updatedPeriode));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePeriode(@PathVariable Long id) {
        if (!periodeService.getPeriodeById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        periodeService.deletePeriode(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{periodeId}/appreciations/count/tuteur/{tuteurId}")
    public ResponseEntity<Integer> countAppreciationsByTuteurForPeriode(
            @PathVariable Long periodeId, @PathVariable String tuteurId) {
        int count = periodeService.countAppreciationsByTuteurForPeriode(periodeId, tuteurId);
        return ResponseEntity.ok(count);
    }
}


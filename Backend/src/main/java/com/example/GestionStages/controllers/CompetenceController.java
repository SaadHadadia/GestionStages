package com.example.GestionStages.controllers;
import com.example.GestionStages.dto.CompetenceDTO;
import com.example.GestionStages.models.Appreciation;
import com.example.GestionStages.models.Competence;
import com.example.GestionStages.Services.AppreciationService;
import com.example.GestionStages.Services.CompetenceService;
import com.example.GestionStages.mappers.MapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/competences")
@CrossOrigin(origins = "*")
public class CompetenceController {

    private final CompetenceService competenceService;
    private final AppreciationService appreciationService;
    private final MapperService mapperService;

    @Autowired
    public CompetenceController(CompetenceService competenceService, AppreciationService appreciationService,
                                MapperService mapperService) {
        this.competenceService = competenceService;
        this.appreciationService = appreciationService;
        this.mapperService = mapperService;
    }

    @GetMapping
    public ResponseEntity<List<CompetenceDTO>> getAllCompetences() {
        List<Competence> competences = competenceService.getAllCompetences();
        return ResponseEntity.ok(mapperService.toCompetenceDTOs(competences));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompetenceDTO> getCompetenceById(@PathVariable Long id) {
        Optional<Competence> competenceOpt = competenceService.getCompetenceById(id);
        return competenceOpt.map(competence -> ResponseEntity.ok(mapperService.toCompetenceDTO(competence)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/appreciation/{appreciationId}")
    public ResponseEntity<List<CompetenceDTO>> getCompetencesByAppreciationId(@PathVariable Long appreciationId) {
        List<Competence> competences = competenceService.getCompetencesByAppreciationId(appreciationId);
        return ResponseEntity.ok(mapperService.toCompetenceDTOs(competences));
    }

    @GetMapping("/average/stagiaire/{stagiaireId}")
    public ResponseEntity<Double> getAverageNoteByStagiaire(@PathVariable String stagiaireId) {
        Double average = competenceService.getAverageNoteByStagiaire(stagiaireId);
        return ResponseEntity.ok(average != null ? average : 0.0);
    }

    @GetMapping("/tuteur/{tuteurId}")
    public ResponseEntity<List<CompetenceDTO>> getCompetencesByTuteurId(@PathVariable String tuteurId) {
        List<Competence> competences = competenceService.getCompetencesByTuteurId(tuteurId);
        return ResponseEntity.ok(mapperService.toCompetenceDTOs(competences));
    }

    @GetMapping("/average/periode/{periodeId}")
    public ResponseEntity<Double> getAverageNoteByPeriodeId(@PathVariable Long periodeId) {
        Double average = competenceService.getAverageNoteByPeriodeId(periodeId);
        return ResponseEntity.ok(average != null ? average : 0.0);
    }

    @PostMapping
    public ResponseEntity<CompetenceDTO> createCompetence(@RequestBody CompetenceDTO competenceDTO) {
        Optional<Appreciation> appreciationOpt = appreciationService.getAppreciationById(competenceDTO.getAppreciationId());

        if (!appreciationOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        Competence competence = mapperService.toCompetence(competenceDTO, appreciationOpt.get());
        Competence savedCompetence = competenceService.saveCompetence(competence);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapperService.toCompetenceDTO(savedCompetence));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompetenceDTO> updateCompetence(@PathVariable Long id, @RequestBody CompetenceDTO competenceDTO) {
        if (!competenceService.getCompetenceById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Appreciation> appreciationOpt = appreciationService.getAppreciationById(competenceDTO.getAppreciationId());

        if (!appreciationOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        Competence competence = mapperService.toCompetence(competenceDTO, appreciationOpt.get());
        competence.setId(id);
        Competence updatedCompetence = competenceService.saveCompetence(competence);
        return ResponseEntity.ok(mapperService.toCompetenceDTO(updatedCompetence));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompetence(@PathVariable Long id) {
        if (!competenceService.getCompetenceById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        competenceService.deleteCompetence(id);
        return ResponseEntity.noContent().build();
    }
}
package com.example.GestionStages.controllers;

import com.example.GestionStages.dto.CompetenceDTO;
import com.example.GestionStages.models.Categorie;
import com.example.GestionStages.models.Competence;
import com.example.GestionStages.services.CompetenceService;
import com.example.GestionStages.mappers.MapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/competences")
public class CompetenceController {

    @Autowired
    private CompetenceService competenceService;

    @Autowired
    private MapperService mapperService;

    @PostMapping
    public ResponseEntity<CompetenceDTO> createCompetence(@RequestBody CompetenceDTO competenceDTO) {
        Competence competence = mapperService.toCompetence(competenceDTO);
        Competence savedCompetence = competenceService.addCompetence(competence);
        return ResponseEntity.ok(mapperService.toCompetenceDTO(savedCompetence));
    }

    @GetMapping
    public ResponseEntity<List<CompetenceDTO>> getAllCompetences() {
        List<Competence> competences = competenceService.getAllCompetences();
        return ResponseEntity.ok(mapperService.toCompetenceDTOList(competences));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompetenceDTO> getCompetenceById(@PathVariable Long id) {
        Optional<Competence> competence = competenceService.getCompetenceById(id);
        return competence.map(value -> ResponseEntity.ok(mapperService.toCompetenceDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/categorie/{categorie}")
    public ResponseEntity<List<CompetenceDTO>> getCompetencesByCategorie(@PathVariable String categorie) {
        try {
            Categorie cat = Categorie.valueOf(categorie);
            List<Competence> competences = competenceService.getCompetencesByCategorie(cat);
            return ResponseEntity.ok(mapperService.toCompetenceDTOList(competences));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompetenceDTO> updateCompetence(@PathVariable Long id, @RequestBody CompetenceDTO competenceDTO) {
        Optional<Competence> existingCompetence = competenceService.getCompetenceById(id);
        if (existingCompetence.isPresent()) {
            Competence competence = mapperService.toCompetence(competenceDTO);
            competence.setId(id);
            Competence updatedCompetence = competenceService.updateCompetence(competence);
            return ResponseEntity.ok(mapperService.toCompetenceDTO(updatedCompetence));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompetence(@PathVariable Long id) {
        competenceService.deleteCompetence(id);
        return ResponseEntity.noContent().build();
    }
}
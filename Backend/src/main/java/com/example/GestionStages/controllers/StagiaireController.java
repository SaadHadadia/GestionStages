package com.example.GestionStages.controllers;

import com.example.GestionStages.dto.StagiaireDTO;
import com.example.GestionStages.models.Stagiaire;
import com.example.GestionStages.services.StagiaireService;
import com.example.GestionStages.mappers.MapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stagiaires")
public class StagiaireController {

    @Autowired
    private StagiaireService stagiaireService;

    @Autowired
    private MapperService mapperService;

    @PostMapping
    public ResponseEntity<StagiaireDTO> createStagiaire(@RequestBody StagiaireDTO stagiaireDTO) {
        Stagiaire stagiaire = mapperService.toStagiaire(stagiaireDTO);
        Stagiaire savedStagiaire = stagiaireService.addStagiaire(stagiaire);
        return ResponseEntity.ok(mapperService.toStagiaireDTO(savedStagiaire));
    }

    @GetMapping
    public ResponseEntity<List<StagiaireDTO>> getAllStagiaires() {
        List<Stagiaire> stagiaires = stagiaireService.getAllStagiaires();
        return ResponseEntity.ok(mapperService.toStagiaireDTOList(stagiaires));
    }

    @GetMapping("/{username}")
    public ResponseEntity<StagiaireDTO> getStagiaireByUsername(@PathVariable String username) {
        Optional<Stagiaire> stagiaire = stagiaireService.getStagiaireByUsername(username);
        return stagiaire.map(value -> ResponseEntity.ok(mapperService.toStagiaireDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/institution/{institution}")
    public ResponseEntity<List<StagiaireDTO>> getStagiairesByInstitution(@PathVariable String institution) {
        List<Stagiaire> stagiaires = stagiaireService.getStagiairesByInstitution(institution);
        return ResponseEntity.ok(mapperService.toStagiaireDTOList(stagiaires));
    }

    @PutMapping("/{username}")
    public ResponseEntity<StagiaireDTO> updateStagiaire(@PathVariable String username, @RequestBody StagiaireDTO stagiaireDTO) {
        Optional<Stagiaire> existingStagiaire = stagiaireService.getStagiaireByUsername(username);
        if (existingStagiaire.isPresent()) {
            Stagiaire stagiaire = mapperService.toStagiaire(stagiaireDTO);
            stagiaire.setUsername(username);
            Stagiaire updatedStagiaire = stagiaireService.updateStagiaire(stagiaire);
            return ResponseEntity.ok(mapperService.toStagiaireDTO(updatedStagiaire));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteStagiaire(@PathVariable String username) {
        stagiaireService.deleteStagiaire(username);
        return ResponseEntity.noContent().build();
    }
}
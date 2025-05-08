package com.example.GestionStages.controllers;
import com.example.GestionStages.dto.StagiaireDTO;
import com.example.GestionStages.models.Stagiaire;
import com.example.GestionStages.mappers.MapperService;
import com.example.GestionStages.Services.StagiaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stagiaires")
@CrossOrigin(origins = "*")
public class StagiaireController {

    private final StagiaireService stagiaireService;
    private final MapperService mapperService;

    @Autowired
    public StagiaireController(StagiaireService stagiaireService, MapperService mapperService) {
        this.stagiaireService = stagiaireService;
        this.mapperService = mapperService;
    }

    @GetMapping
    public ResponseEntity<List<StagiaireDTO>> getAllStagiaires() {
        List<Stagiaire> stagiaires = stagiaireService.getAllStagiaires();
        List<StagiaireDTO> stagiaireDTOs = stagiaires.stream()
                .map(stagiaire -> (StagiaireDTO) mapperService.toUserDTO(stagiaire))
                .collect(Collectors.toList());
        return ResponseEntity.ok(stagiaireDTOs);
    }

    @GetMapping("/{username}")
    public ResponseEntity<StagiaireDTO> getStagiaireByUsername(@PathVariable String username) {
        Optional<Stagiaire> stagiaireOpt = stagiaireService.getStagiaireByUsername(username);
        return stagiaireOpt.map(stagiaire -> ResponseEntity.ok((StagiaireDTO) mapperService.toUserDTO(stagiaire)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/institution/{institution}")
    public ResponseEntity<List<StagiaireDTO>> getStagiairesByInstitution(@PathVariable String institution) {
        List<Stagiaire> stagiaires = stagiaireService.getStagiairesByInstitution(institution);
        List<StagiaireDTO> stagiaireDTOs = stagiaires.stream()
                .map(stagiaire -> (StagiaireDTO) mapperService.toUserDTO(stagiaire))
                .collect(Collectors.toList());
        return ResponseEntity.ok(stagiaireDTOs);
    }

    @GetMapping("/stage/{stageId}")
    public ResponseEntity<List<StagiaireDTO>> getStagiairesByStageId(@PathVariable Long stageId) {
        List<Stagiaire> stagiaires = stagiaireService.getStagiairesByStageId(stageId);
        List<StagiaireDTO> stagiaireDTOs = stagiaires.stream()
                .map(stagiaire -> (StagiaireDTO) mapperService.toUserDTO(stagiaire))
                .collect(Collectors.toList());
        return ResponseEntity.ok(stagiaireDTOs);
    }

    @GetMapping("/tuteur/{tuteurId}")
    public ResponseEntity<List<StagiaireDTO>> getStagiairesByTuteurId(@PathVariable String tuteurId) {
        List<Stagiaire> stagiaires = stagiaireService.getStagiairesByTuteurId(tuteurId);
        List<StagiaireDTO> stagiaireDTOs = stagiaires.stream()
                .map(stagiaire -> (StagiaireDTO) mapperService.toUserDTO(stagiaire))
                .collect(Collectors.toList());
        return ResponseEntity.ok(stagiaireDTOs);
    }
}

package com.example.GestionStages.controllers;

import com.example.GestionStages.dto.TuteurDTO;
import com.example.GestionStages.models.Tuteur;
import com.example.GestionStages.services.TuteurService;
import com.example.GestionStages.mappers.MapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tuteurs")
public class TuteurController {

    @Autowired
    private TuteurService tuteurService;

    @Autowired
    private MapperService mapperService;

    @PostMapping
    public ResponseEntity<TuteurDTO> createTuteur(@RequestBody TuteurDTO tuteurDTO) {
        Tuteur tuteur = mapperService.toTuteur(tuteurDTO);
        Tuteur savedTuteur = tuteurService.addTuteur(tuteur);
        return ResponseEntity.ok(mapperService.toTuteurDTO(savedTuteur));
    }

    @GetMapping
    public ResponseEntity<List<TuteurDTO>> getAllTuteurs() {
        List<Tuteur> tuteurs = tuteurService.getAllTuteurs();
        return ResponseEntity.ok(mapperService.toTuteurDTOList(tuteurs));
    }

    @GetMapping("/{username}")
    public ResponseEntity<TuteurDTO> getTuteurByUsername(@PathVariable String username) {
        Optional<Tuteur> tuteur = tuteurService.getTuteurByUsername(username);
        return tuteur.map(value -> ResponseEntity.ok(mapperService.toTuteurDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/entreprise/{entreprise}")
    public ResponseEntity<List<TuteurDTO>> getTuteursByEntreprise(@PathVariable String entreprise) {
        List<Tuteur> tuteurs = tuteurService.getTuteursByEntreprise(entreprise);
        return ResponseEntity.ok(mapperService.toTuteurDTOList(tuteurs));
    }

    @PutMapping("/{username}")
    public ResponseEntity<TuteurDTO> updateTuteur(@PathVariable String username, @RequestBody TuteurDTO tuteurDTO) {
        Optional<Tuteur> existingTuteur = tuteurService.getTuteurByUsername(username);
        if (existingTuteur.isPresent()) {
            Tuteur tuteur = mapperService.toTuteur(tuteurDTO);
            tuteur.setUsername(username);
            Tuteur updatedTuteur = tuteurService.updateTuteur(tuteur);
            return ResponseEntity.ok(mapperService.toTuteurDTO(updatedTuteur));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteTuteur(@PathVariable String username) {
        tuteurService.deleteTuteur(username);
        return ResponseEntity.noContent().build();
    }
}
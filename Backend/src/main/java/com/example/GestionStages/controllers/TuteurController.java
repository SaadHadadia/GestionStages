package com.example.GestionStages.controllers;
import com.example.GestionStages.dto.TuteurDTO;
import com.example.GestionStages.dto.UserDTO;
import com.example.GestionStages.models.Tuteur;
import com.example.GestionStages.mappers.MapperService;
import com.example.GestionStages.Services.TuteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tuteurs")
@CrossOrigin(origins = "*")
public class TuteurController {

    private final TuteurService tuteurService;
    private final MapperService mapperService;

    @Autowired
    public TuteurController(TuteurService tuteurService, MapperService mapperService) {
        this.tuteurService = tuteurService;
        this.mapperService = mapperService;
    }

    @GetMapping
    public ResponseEntity<List<TuteurDTO>> getAllTuteurs() {
        List<Tuteur> tuteurs = tuteurService.getAllTuteurs();
        List<TuteurDTO> tuteurDTOs = tuteurs.stream()
                .map(tuteur -> (TuteurDTO) mapperService.toUserDTO(tuteur))
                .collect(Collectors.toList());
        return ResponseEntity.ok(tuteurDTOs);
    }

    @GetMapping("/{username}")
    public ResponseEntity<TuteurDTO> getTuteurByUsername(@PathVariable String username) {
        Optional<Tuteur> tuteurOpt = tuteurService.getTuteurByUsername(username);
        return tuteurOpt.map(tuteur -> ResponseEntity.ok((TuteurDTO) mapperService.toUserDTO(tuteur)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/entreprise/{entreprise}")
    public ResponseEntity<List<TuteurDTO>> getTuteursByEntreprise(@PathVariable String entreprise) {
        List<Tuteur> tuteurs = tuteurService.getTuteursByEntreprise(entreprise);
        List<TuteurDTO> tuteurDTOs = tuteurs.stream()
                .map(tuteur -> (TuteurDTO) mapperService.toUserDTO(tuteur))
                .collect(Collectors.toList());
        return ResponseEntity.ok(tuteurDTOs);
    }

    @GetMapping("/periode/{periodeId}")
    public ResponseEntity<List<TuteurDTO>> getTuteursByPeriodeId(@PathVariable Long periodeId) {
        List<Tuteur> tuteurs = tuteurService.getTuteursByPeriodeId(periodeId);
        List<TuteurDTO> tuteurDTOs = tuteurs.stream()
                .map(tuteur -> (TuteurDTO) mapperService.toUserDTO(tuteur))
                .collect(Collectors.toList());
        return ResponseEntity.ok(tuteurDTOs);
    }

    @GetMapping("/{tuteurId}/appreciation-exists-for-periode/{periodeId}")
    public ResponseEntity<Boolean> existsAppreciationForPeriode(
            @PathVariable String tuteurId, @PathVariable Long periodeId) {
        boolean exists = tuteurService.existsAppreciationForPeriode(tuteurId, periodeId);
        return ResponseEntity.ok(exists);
    }
}
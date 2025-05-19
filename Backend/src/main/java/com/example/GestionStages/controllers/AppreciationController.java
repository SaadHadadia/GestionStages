package com.example.GestionStages.controllers;

import com.example.GestionStages.dto.AppreciationDTO;
import com.example.GestionStages.models.Appreciation;
import com.example.GestionStages.models.Periode;
import com.example.GestionStages.models.Tuteur;
import com.example.GestionStages.services.AppreciationService;
import com.example.GestionStages.services.PeriodeService;
import com.example.GestionStages.services.TuteurService;
import com.example.GestionStages.mappers.MapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/appreciations")
public class AppreciationController {

    @Autowired
    private AppreciationService appreciationService;

    @Autowired
    private TuteurService tuteurService;

    @Autowired
    private PeriodeService periodeService;

    @Autowired
    private MapperService mapperService;

    @PostMapping
    public ResponseEntity<AppreciationDTO> createAppreciation(@RequestBody AppreciationDTO appreciationDTO) {
        Optional<Tuteur> tuteur = tuteurService.getTuteurByUsername(appreciationDTO.getTuteurUsername());
        Optional<Periode> periode = periodeService.getPeriodeById(appreciationDTO.getPeriodeId());
        if (tuteur.isPresent() && periode.isPresent()) {
            Appreciation appreciation = mapperService.toAppreciation(appreciationDTO, tuteur.get(), periode.get());
            Appreciation savedAppreciation = appreciationService.addAppreciation(appreciation);
            return ResponseEntity.ok(mapperService.toAppreciationDTO(savedAppreciation));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<AppreciationDTO>> getAllAppreciations() {
        List<Appreciation> appreciations = appreciationService.getAllAppreciations();
        return ResponseEntity.ok(mapperService.toAppreciationDTOList(appreciations));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppreciationDTO> getAppreciationById(@PathVariable Long id) {
        Optional<Appreciation> appreciation = appreciationService.getAppreciationById(id);
        return appreciation.map(value -> ResponseEntity.ok(mapperService.toAppreciationDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/tuteur/{username}")
    public ResponseEntity<List<AppreciationDTO>> getAppreciationsByTuteur(@PathVariable String username) {
        Optional<Tuteur> tuteur = tuteurService.getTuteurByUsername(username);
        if (tuteur.isPresent()) {
            List<Appreciation> appreciations = appreciationService.getAppreciationsByTuteur(tuteur.get());
            return ResponseEntity.ok(mapperService.toAppreciationDTOList(appreciations));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/periode/{periodeId}")
    public ResponseEntity<List<AppreciationDTO>> getAppreciationsByPeriode(@PathVariable Long periodeId) {
        Optional<Periode> periode = periodeService.getPeriodeById(periodeId);
        if (periode.isPresent()) {
            List<Appreciation> appreciations = appreciationService.getAppreciationsByPeriode(periode.get());
            return ResponseEntity.ok(mapperService.toAppreciationDTOList(appreciations));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppreciationDTO> updateAppreciation(@PathVariable Long id, @RequestBody AppreciationDTO appreciationDTO) {
        Optional<Appreciation> existingAppreciation = appreciationService.getAppreciationById(id);
        if (existingAppreciation.isPresent()) {
            Optional<Tuteur> tuteur = tuteurService.getTuteurByUsername(appreciationDTO.getTuteurUsername());
            Optional<Periode> periode = periodeService.getPeriodeById(appreciationDTO.getPeriodeId());
            if (tuteur.isPresent() && periode.isPresent()) {
                Appreciation appreciation = mapperService.toAppreciation(appreciationDTO, tuteur.get(), periode.get());
                appreciation.setId(id);
                Appreciation updatedAppreciation = appreciationService.updateAppreciation(appreciation);
                return ResponseEntity.ok(mapperService.toAppreciationDTO(updatedAppreciation));
            }
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppreciation(@PathVariable Long id) {
        appreciationService.deleteAppreciation(id);
        return ResponseEntity.noContent().build();
    }
}
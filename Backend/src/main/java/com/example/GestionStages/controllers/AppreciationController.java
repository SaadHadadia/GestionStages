package com.example.GestionStages.controllers;
import com.example.GestionStages.dto.AppreciationDTO;
import com.example.GestionStages.models.Appreciation;
import com.example.GestionStages.models.Periode;
import com.example.GestionStages.models.Tuteur;
import com.example.GestionStages.Services.AppreciationService;
import com.example.GestionStages.mappers.MapperService;
import com.example.GestionStages.Services.PeriodeService;
import com.example.GestionStages.Services.TuteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/appreciations")
@CrossOrigin(origins = "*")
public class AppreciationController {

    private final AppreciationService appreciationService;
    private final TuteurService tuteurService;
    private final PeriodeService periodeService;
    private final MapperService mapperService;

    @Autowired
    public AppreciationController(AppreciationService appreciationService, TuteurService tuteurService,
                                  PeriodeService periodeService, MapperService mapperService) {
        this.appreciationService = appreciationService;
        this.tuteurService = tuteurService;
        this.periodeService = periodeService;
        this.mapperService = mapperService;
    }

    @GetMapping
    public ResponseEntity<List<AppreciationDTO>> getAllAppreciations() {
        List<Appreciation> appreciations = appreciationService.getAllAppreciations();
        return ResponseEntity.ok(mapperService.toAppreciationDTOs(appreciations));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppreciationDTO> getAppreciationById(@PathVariable Long id) {
        Optional<Appreciation> appreciationOpt = appreciationService.getAppreciationById(id);
        return appreciationOpt.map(appreciation -> ResponseEntity.ok(mapperService.toAppreciationDTO(appreciation)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tuteur/{tuteurUsername}")
    public ResponseEntity<List<AppreciationDTO>> getAppreciationsByTuteurUsername(@PathVariable String tuteurUsername) {
        List<Appreciation> appreciations = appreciationService.getAppreciationsByTuteurUsername(tuteurUsername);
        return ResponseEntity.ok(mapperService.toAppreciationDTOs(appreciations));
    }

    @GetMapping("/periode/{periodeId}")
    public ResponseEntity<List<AppreciationDTO>> getAppreciationsByPeriodeId(@PathVariable Long periodeId) {
        List<Appreciation> appreciations = appreciationService.getAppreciationsByPeriodeId(periodeId);
        return ResponseEntity.ok(mapperService.toAppreciationDTOs(appreciations));
    }

    @GetMapping("/tuteur/{tuteurUsername}/periode/{periodeId}")
    public ResponseEntity<AppreciationDTO> getAppreciationByTuteurAndPeriode(
            @PathVariable String tuteurUsername, @PathVariable Long periodeId) {
        Optional<Appreciation> appreciationOpt = appreciationService.getAppreciationByTuteurAndPeriode(tuteurUsername, periodeId);
        return appreciationOpt.map(appreciation -> ResponseEntity.ok(mapperService.toAppreciationDTO(appreciation)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/stagiaire/{stagiaireId}")
    public ResponseEntity<List<AppreciationDTO>> getAppreciationsByStagiaireId(@PathVariable String stagiaireId) {
        List<Appreciation> appreciations = appreciationService.getAppreciationsByStagiaireId(stagiaireId);
        return ResponseEntity.ok(mapperService.toAppreciationDTOs(appreciations));
    }

    @PostMapping
    public ResponseEntity<AppreciationDTO> createAppreciation(@RequestBody AppreciationDTO appreciationDTO) {
        Optional<Tuteur> tuteurOpt = tuteurService.getTuteurByUsername(appreciationDTO.getTuteurUsername());
        Optional<Periode> periodeOpt = periodeService.getPeriodeById(appreciationDTO.getPeriodeId());

        if (!tuteurOpt.isPresent() || !periodeOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        Appreciation appreciation = mapperService.toAppreciation(appreciationDTO, tuteurOpt.get(), periodeOpt.get());
        Appreciation savedAppreciation = appreciationService.saveAppreciation(appreciation);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapperService.toAppreciationDTO(savedAppreciation));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppreciationDTO> updateAppreciation(@PathVariable Long id, @RequestBody AppreciationDTO appreciationDTO) {
        if (!appreciationService.getAppreciationById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Tuteur> tuteurOpt = tuteurService.getTuteurByUsername(appreciationDTO.getTuteurUsername());
        Optional<Periode> periodeOpt = periodeService.getPeriodeById(appreciationDTO.getPeriodeId());

        if (!tuteurOpt.isPresent() || !periodeOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        Appreciation appreciation = mapperService.toAppreciation(appreciationDTO, tuteurOpt.get(), periodeOpt.get());
        appreciation.setId(id);
        Appreciation updatedAppreciation = appreciationService.saveAppreciation(appreciation);
        return ResponseEntity.ok(mapperService.toAppreciationDTO(updatedAppreciation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppreciation(@PathVariable Long id) {
        if (!appreciationService.getAppreciationById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        appreciationService.deleteAppreciation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/tuteur/{tuteurId}/periode/{periodeId}")
    public ResponseEntity<Boolean> existsByTuteurIdAndPeriodeId(
            @PathVariable String tuteurId, @PathVariable Long periodeId) {
        boolean exists = appreciationService.existsByTuteurIdAndPeriodeId(tuteurId, periodeId);
        return ResponseEntity.ok(exists);
    }
}

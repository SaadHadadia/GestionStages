package com.example.GestionStages.controllers;
import com.example.GestionStages.Services.PeriodeService;
import com.example.GestionStages.Services.StagiaireService;
import com.example.GestionStages.Services.UserService;
import com.example.GestionStages.dto.AttribuerStageDTO;
import com.example.GestionStages.dto.StageDTO;
import com.example.GestionStages.models.*;
import com.example.GestionStages.mappers.MapperService;
import com.example.GestionStages.Services.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stages")
@CrossOrigin(origins = "*")
public class StageController {

    private final StageService stageService;
    private final MapperService mapperService;
    private final StagiaireService stagiaireService;
    private final PeriodeService periodeService;
    private final UserService userService;

    @Autowired
    public StageController(StageService stageService, MapperService mapperService, StagiaireService stagiaireService, PeriodeService periodeService, UserService userService) {
        this.stageService = stageService;
        this.mapperService = mapperService;
        this.stagiaireService = stagiaireService;
        this.periodeService = periodeService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<StageDTO>> getAllStages() {
        List<Stage> stages = stageService.getAllStages();
        return ResponseEntity.ok(mapperService.toStageDTOs(stages));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StageDTO> getStageById(@PathVariable Long id) {
        Optional<Stage> stageOpt = stageService.getStageById(id);
        return stageOpt.map(stage -> ResponseEntity.ok(mapperService.toStageDTO(stage)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/entreprise/{entreprise}")
    public ResponseEntity<List<StageDTO>> getStagesByEntreprise(@PathVariable String entreprise) {
        List<Stage> stages = stageService.getStagesByEntreprise(entreprise);
        return ResponseEntity.ok(mapperService.toStageDTOs(stages));
    }

    @GetMapping("/tuteur/{tuteurId}")
    public ResponseEntity<List<StageDTO>> getStagesByTuteurId(@PathVariable String tuteurId) {
        List<Stage> stages = stageService.getStagesByTuteurId(tuteurId);
        return ResponseEntity.ok(mapperService.toStageDTOs(stages));
    }

    @GetMapping("/stagiaire/{stagiaireId}")
    public ResponseEntity<List<StageDTO>> getStagesByStagiaireId(@PathVariable String stagiaireId) {
        List<Stage> stages = stageService.getStagesByStagiaireId(stagiaireId);
        return ResponseEntity.ok(mapperService.toStageDTOs(stages));
    }

    @PostMapping
    public ResponseEntity<StageDTO> createStage(@RequestBody StageDTO stageDTO) {
        Stage stage = mapperService.toStage(stageDTO);
        Stage savedStage = stageService.saveStage(stage);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapperService.toStageDTO(savedStage));
    }

    @PostMapping("attribuer")
    public Periode attribuerStage(@RequestBody AttribuerStageDTO stageDTO) {

        Stage stage = new Stage();
        Stage savedStage = stageService.saveStage(stage);

        Optional<Stagiaire> stagiaireOpt = stagiaireService.getStagiaireByUsername(stageDTO.getStagiaireId());
        Stagiaire stagiaire = stagiaireOpt.get();


        Periode periode = new Periode(
                stageDTO.getDateDebut(),
                stageDTO.getDateFin(),
                savedStage,
                stagiaire
        );

        Periode savedPeriode = periodeService.savePeriode(periode);

        Optional<User> tuteurOpt = userService.getUserByUsername(stageDTO.getTuteurId());
        User tuteur = tuteurOpt.get();

        periodeService.sendEmail(tuteur ,savedPeriode);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedPeriode).getBody();

    }


    @PutMapping("/{id}")
    public ResponseEntity<StageDTO> updateStage(@PathVariable Long id, @RequestBody StageDTO stageDTO) {
        if (!stageService.getStageById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Stage stage = mapperService.toStage(stageDTO);
        stage.setId(id);
        Stage updatedStage = stageService.saveStage(stage);
        return ResponseEntity.ok(mapperService.toStageDTO(updatedStage));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStage(@PathVariable Long id) {
        if (!stageService.getStageById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        stageService.deleteStage(id);
        return ResponseEntity.noContent().build();
    }
}


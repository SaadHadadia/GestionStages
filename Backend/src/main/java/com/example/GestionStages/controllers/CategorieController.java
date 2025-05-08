package com.example.GestionStages.controllers;
import com.example.GestionStages.dto.CategorieDTO;
import com.example.GestionStages.models.Categorie;
import com.example.GestionStages.models.Competence;
import com.example.GestionStages.Services.CategorieService;
import com.example.GestionStages.Services.CompetenceService;
import com.example.GestionStages.mappers.MapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategorieController {

    private final CategorieService categorieService;
    private final CompetenceService competenceService;
    private final MapperService mapperService;

    @Autowired
    public CategorieController(CategorieService categorieService, CompetenceService competenceService,
                               MapperService mapperService) {
        this.categorieService = categorieService;
        this.competenceService = competenceService;
        this.mapperService = mapperService;
    }

    @GetMapping
    public ResponseEntity<List<CategorieDTO>> getAllCategories() {
        List<Categorie> categories = categorieService.getAllCategories();
        return ResponseEntity.ok(mapperService.toCategorieDTOs(categories));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategorieDTO> getCategorieById(@PathVariable Long id) {
        Optional<Categorie> categorieOpt = categorieService.getCategorieById(id);
        return categorieOpt.map(categorie -> ResponseEntity.ok(mapperService.toCategorieDTO(categorie)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/competence/{competenceId}")
    public ResponseEntity<List<CategorieDTO>> getCategoriesByCompetenceId(@PathVariable Long competenceId) {
        List<Categorie> categories = categorieService.getCategoriesByCompetenceId(competenceId);
        return ResponseEntity.ok(mapperService.toCategorieDTOs(categories));
    }

    @GetMapping("/intitule/{intitule}")
    public ResponseEntity<List<CategorieDTO>> getCategoriesByIntituleContaining(@PathVariable String intitule) {
        List<Categorie> categories = categorieService.getCategoriesByIntituleContaining(intitule);
        return ResponseEntity.ok(mapperService.toCategorieDTOs(categories));
    }

    @GetMapping("/appreciation/{appreciationId}")
    public ResponseEntity<List<CategorieDTO>> getCategoriesByAppreciationId(@PathVariable Long appreciationId) {
        List<Categorie> categories = categorieService.getCategoriesByAppreciationId(appreciationId);
        return ResponseEntity.ok(mapperService.toCategorieDTOs(categories));
    }

    @GetMapping("/competence-intitule/{competenceIntitule}")
    public ResponseEntity<List<CategorieDTO>> getCategoriesByCompetenceIntitule(@PathVariable String competenceIntitule) {
        List<Categorie> categories = categorieService.getCategoriesByCompetenceIntitule(competenceIntitule);
        return ResponseEntity.ok(mapperService.toCategorieDTOs(categories));
    }

    @PostMapping
    public ResponseEntity<CategorieDTO> createCategorie(@RequestBody CategorieDTO categorieDTO) {
        Optional<Competence> competenceOpt = competenceService.getCompetenceById(categorieDTO.getCompetenceId());

        if (!competenceOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        Categorie categorie = mapperService.toCategorie(categorieDTO, competenceOpt.get());
        Categorie savedCategorie = categorieService.saveCategorie(categorie);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapperService.toCategorieDTO(savedCategorie));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategorieDTO> updateCategorie(@PathVariable Long id, @RequestBody CategorieDTO categorieDTO) {
        if (!categorieService.getCategorieById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Competence> competenceOpt = competenceService.getCompetenceById(categorieDTO.getCompetenceId());

        if (!competenceOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        Categorie categorie = mapperService.toCategorie(categorieDTO, competenceOpt.get());
        categorie.setId(id);
        Categorie updatedCategorie = categorieService.saveCategorie(categorie);
        return ResponseEntity.ok(mapperService.toCategorieDTO(updatedCategorie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategorie(@PathVariable Long id) {
        if (!categorieService.getCategorieById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        categorieService.deleteCategorie(id);
        return ResponseEntity.noContent().build();
    }
}
package com.example.GestionStages.Services;
import com.example.GestionStages.models.Categorie;
import com.example.GestionStages.repositories.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategorieService {

    private final CategorieRepository categorieRepository;

    @Autowired
    public CategorieService(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    public List<Categorie> getAllCategories() {
        return categorieRepository.findAll();
    }

    public Optional<Categorie> getCategorieById(Long id) {
        return categorieRepository.findById(id);
    }

    public List<Categorie> getCategoriesByCompetenceId(Long competenceId) {
        return categorieRepository.findByCompetenceId(competenceId);
    }

    public List<Categorie> getCategoriesByIntituleContaining(String intitule) {
        return categorieRepository.findByIntituleContaining(intitule);
    }

    public List<Categorie> getCategoriesByAppreciationId(Long appreciationId) {
        return categorieRepository.findAllByAppreciationId(appreciationId);
    }

    public List<Categorie> getCategoriesByCompetenceIntitule(String competenceIntitule) {
        return categorieRepository.findAllByCompetenceIntitule(competenceIntitule);
    }

    public Categorie saveCategorie(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    public void deleteCategorie(Long id) {
        categorieRepository.deleteById(id);
    }

    // Ajout de la méthode pour compter les compétences par catégorie
    public long countCompetencesByCategorie(Long categorieId) {
        return categorieRepository.countCompetencesByCategorieId(categorieId);
    }

}
package com.example.GestionStages.repositories;
import com.example.GestionStages.models.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {

    List<Categorie> findByCompetenceId(Long id);

    List<Categorie> findByIntituleContaining(String intitule);

    @Query("SELECT c FROM Categorie c JOIN c.competence comp JOIN comp.appreciation a WHERE a.id = :appreciationId")
    List<Categorie> findAllByAppreciationId(@Param("appreciationId") Long id);

    @Query("SELECT c FROM Categorie c JOIN c.competence comp WHERE comp.intitule = :intitule")
    List<Categorie> findAllByCompetenceIntitule(@Param("intitule") String intitule);


    // Ajout de la méthode pour compter les compétences par catégorie
    @Query("SELECT COUNT(c) FROM Competence c JOIN c.categories ca WHERE ca.id = :categorieId")
    long countCompetencesByCategorieId(@Param("categorieId") Long categorieId);



}
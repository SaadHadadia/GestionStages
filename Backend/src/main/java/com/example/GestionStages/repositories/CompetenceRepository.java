package com.example.GestionStages.repositories;

import com.example.GestionStages.models.Competence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompetenceRepository extends JpaRepository<Competence, Long> {
    List<Competence> findByAppreciationId(Long appreciationId);

    @Query("SELECT AVG(c.note) FROM Competence c JOIN c.appreciation a JOIN a.periode p WHERE p.stagiaire.username = :stagiaireId")
    Double findAverageNoteByStagiaire(@Param("stagiaireId") String stagiaireId);

    @Query("SELECT c FROM Competence c JOIN c.appreciation a WHERE a.tuteur.username = :tuteurId")
    List<Competence> findAllByTuteurId(@Param("tuteurId") String tuteurId);

    @Query("SELECT AVG(c.note) FROM Competence c JOIN c.appreciation a JOIN a.periode p WHERE p.id = :periodeId")
    Double findAverageNoteByPeriodeId(@Param("periodeId") Long periodeId);
}
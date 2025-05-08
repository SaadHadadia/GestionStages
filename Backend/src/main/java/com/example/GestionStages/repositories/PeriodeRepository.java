package com.example.GestionStages.repositories;

import com.example.GestionStages.models.Periode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PeriodeRepository extends JpaRepository<Periode, Long> {
    List<Periode> findByStageId(Long stageId);
    List<Periode> findByStagiaireUsername(String stagiaireUsername);

    @Query("SELECT p FROM Periode p WHERE p.dateDebut <= :date AND p.dateFin >= :date")
    List<Periode> findAllActivePeriodes(@Param("date") LocalDate date);

    @Query("SELECT p FROM Periode p JOIN p.appreciations a WHERE a.tuteur.username = :tuteurId")
    List<Periode> findAllByTuteurId(@Param("tuteurId") String tuteurId);

    @Query("SELECT COUNT(a) FROM Periode p JOIN p.appreciations a WHERE p.id = :periodeId AND a.tuteur.username = :tuteurId")
    int countAppreciationsByTuteurForPeriode(@Param("periodeId") Long periodeId, @Param("tuteurId") String tuteurId);
}
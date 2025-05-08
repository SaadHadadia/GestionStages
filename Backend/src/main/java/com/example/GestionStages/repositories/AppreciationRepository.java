package com.example.GestionStages.repositories;

import com.example.GestionStages.models.Appreciation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppreciationRepository extends JpaRepository<Appreciation, Long> {
    List<Appreciation> findByTuteurUsername(String tuteurUsername);
    List<Appreciation> findByPeriodeId(Long periodeId);

    Optional<Appreciation> findByTuteurUsernameAndPeriodeId(String tuteurUsername, Long periodeId);

    @Query("SELECT a FROM Appreciation a JOIN a.periode p JOIN p.stagiaire s WHERE s.username = :stagiaireId")
    List<Appreciation> findAllByStagiaireId(@Param("stagiaireId") String stagiaireId);

    @Query("SELECT COUNT(a) > 0 FROM Appreciation a WHERE a.tuteur.username = :tuteurId AND a.periode.id = :periodeId")
    boolean existsByTuteurIdAndPeriodeId(@Param("tuteurId") String tuteurId, @Param("periodeId") Long periodeId);
}
package com.example.GestionStages.repositories;
import com.example.GestionStages.models.Tuteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TuteurRepository extends JpaRepository<Tuteur, String> {
    Optional<Tuteur> findByUsername(String username);
    List<Tuteur> findByEntreprise(String entreprise);

    @Query("SELECT t FROM Tuteur t JOIN t.appreciations a JOIN a.periode p WHERE p.id = :periodeId")
    List<Tuteur> findAllByPeriodeId(@Param("periodeId") Long periodeId);

    @Query("SELECT COUNT(a) > 0 FROM Tuteur t JOIN t.appreciations a JOIN a.periode p WHERE t.username = :tuteurId AND p.id = :periodeId")
    boolean existsAppreciationForPeriode(@Param("tuteurId") String tuteurId, @Param("periodeId") Long periodeId);
}
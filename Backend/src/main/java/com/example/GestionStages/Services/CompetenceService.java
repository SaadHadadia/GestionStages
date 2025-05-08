package com.example.GestionStages.Services;
import com.example.GestionStages.models.Competence;
import com.example.GestionStages.repositories.CompetenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CompetenceService {

    private final CompetenceRepository competenceRepository;

    @Autowired
    public CompetenceService(CompetenceRepository competenceRepository) {
        this.competenceRepository = competenceRepository;
    }

    public List<Competence> getAllCompetences() {
        return competenceRepository.findAll();
    }

    public Optional<Competence> getCompetenceById(Long id) {
        return competenceRepository.findById(id);
    }

    public List<Competence> getCompetencesByAppreciationId(Long appreciationId) {
        return competenceRepository.findByAppreciationId(appreciationId);
    }

    public Double getAverageNoteByStagiaire(String stagiaireId) {
        return competenceRepository.findAverageNoteByStagiaire(stagiaireId);
    }

    public List<Competence> getCompetencesByTuteurId(String tuteurId) {
        return competenceRepository.findAllByTuteurId(tuteurId);
    }

    public Double getAverageNoteByPeriodeId(Long periodeId) {
        return competenceRepository.findAverageNoteByPeriodeId(periodeId);
    }

    public Competence saveCompetence(Competence competence) {
        return competenceRepository.save(competence);
    }

    public void deleteCompetence(Long id) {
        competenceRepository.deleteById(id);
    }
}


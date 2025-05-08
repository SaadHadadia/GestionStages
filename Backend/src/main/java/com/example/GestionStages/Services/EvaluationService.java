package com.example.GestionStages.Services;
import com.example.GestionStages.models.Evaluation;
import com.example.GestionStages.repositories.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EvaluationService {

    private final EvaluationRepository evaluationRepository;

    @Autowired
    public EvaluationService(EvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }

    public List<Evaluation> getAllEvaluations() {
        return evaluationRepository.findAll();
    }

    public Optional<Evaluation> getEvaluationById(Long id) {
        return evaluationRepository.findById(id);
    }

    public List<Evaluation> getEvaluationsByAppreciationId(Long appreciationId) {
        return evaluationRepository.findByAppreciationId(appreciationId);
    }

    public List<Evaluation> getEvaluationsByCategorie(String categorie) {
        return evaluationRepository.findByCategorie(categorie);
    }

    public List<Evaluation> getEvaluationsByTuteurId(String tuteurId) {
        return evaluationRepository.findAllByTuteurId(tuteurId);
    }

    public List<Evaluation> getEvaluationsByStagiaireId(String stagiaireId) {
        return evaluationRepository.findAllByStagiaireId(stagiaireId);
    }

    public Evaluation saveEvaluation(Evaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }

    public void deleteEvaluation(Long id) {
        evaluationRepository.deleteById(id);
    }
}

package com.example.GestionStages.services;

import com.example.GestionStages.models.Evaluation;
import com.example.GestionStages.repositories.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    public Evaluation addEvaluation(Evaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }

    public List<Evaluation> getAllEvaluations() {
        return evaluationRepository.findAll();
    }

    public Optional<Evaluation> getEvaluationById(Long id) {
        return evaluationRepository.findById(id);
    }

    public List<Evaluation> getEvaluationsByCategorie(String categorie) {
        return evaluationRepository.findByCategorie(categorie);
    }

    public Evaluation updateEvaluation(Evaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }

    public void deleteEvaluation(Long id) {
        evaluationRepository.deleteById(id);
    }
}
package com.example.GestionStages.Services;
import com.example.GestionStages.models.Appreciation;
import com.example.GestionStages.repositories.AppreciationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppreciationService {

    private final AppreciationRepository appreciationRepository;

    @Autowired
    public AppreciationService(AppreciationRepository appreciationRepository) {
        this.appreciationRepository = appreciationRepository;
    }

    public List<Appreciation> getAllAppreciations() {
        return appreciationRepository.findAll();
    }

    public Optional<Appreciation> getAppreciationById(Long id) {
        return appreciationRepository.findById(id);
    }

    public List<Appreciation> getAppreciationsByTuteurUsername(String tuteurUsername) {
        return appreciationRepository.findByTuteurUsername(tuteurUsername);
    }

    public List<Appreciation> getAppreciationsByPeriodeId(Long periodeId) {
        return appreciationRepository.findByPeriodeId(periodeId);
    }

    public Optional<Appreciation> getAppreciationByTuteurAndPeriode(String tuteurUsername, Long periodeId) {
        return appreciationRepository.findByTuteurUsernameAndPeriodeId(tuteurUsername, periodeId);
    }

    public List<Appreciation> getAppreciationsByStagiaireId(String stagiaireId) {
        return appreciationRepository.findAllByStagiaireId(stagiaireId);
    }

    public Appreciation saveAppreciation(Appreciation appreciation) {
        return appreciationRepository.save(appreciation);
    }

    public void deleteAppreciation(Long id) {
        appreciationRepository.deleteById(id);
    }

    public boolean existsByTuteurIdAndPeriodeId(String tuteurId, Long periodeId) {
        return appreciationRepository.existsByTuteurIdAndPeriodeId(tuteurId, periodeId);
    }
}

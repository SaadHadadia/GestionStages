package com.example.GestionStages.config;

import com.example.GestionStages.models.*;
import com.example.GestionStages.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InsertData {
    
    private static final Logger logger = LoggerFactory.getLogger(InsertData.class);
    
    @Autowired
    private Environment env;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private CompetenceRepository competenceRepository;

    @Autowired
    private EvaluationRepository evaluationRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Bean
    public boolean createRootAdmin() {
        String username = env.getProperty("root.admin.username");
        String password = env.getProperty("root.admin.password");

        if (username.isEmpty() || password.isEmpty()) {
            logger.error("Root admin credentials are not set in the environment variables.");
            throw new IllegalStateException("Root admin credentials are not set in the environment variables.");
        }

        // Check if the root admin already exists
        if (userRepository.findByUsername(username).isPresent()) {
            logger.info("Root admin already exists.");
            return true;
        }

        // Create the root admin
        Admin admin = new Admin(
                username,
                encoder.encode(password),
                "",
                ""
        );

        try {
            userRepository.save(admin);
            logger.info("Root admin created successfully.");
            return true;
        } catch (Exception e) {
            logger.error("Failed to create root admin: {}", e.getMessage());
            throw new IllegalStateException("Application startup failed due to root admin creation error.", e);
        }
    }

    @Bean
    public boolean createCompetences() {

        // Check if Competence table already has data
        if (competenceRepository.count() > 0 && evaluationRepository.count() > 0) {
            logger.info("Competence and Evaluation tables already contain data.");
            return true;
        }

        try {
            // Create Competences
            if (competenceRepository.count() == 0) {
                List<Competence> competences = new ArrayList<>();
                competences.add(new Competence("Implication dans ses activités", 0, null, Categorie.GLOBALE));
                competences.add(new Competence("Ouverture aux autres", 0, null, Categorie.GLOBALE));
                competences.add(new Competence("Être capable d'analyse et de synthèse", 0, null, Categorie.INDIVIDU));
                competences.add(new Competence("Être capable de s'autoévaluer", 0, null, Categorie.INDIVIDU));
                competences.add(new Competence("Être capable d'analyser le fonctionnement de l'entreprise", 0, null, Categorie.ENTREPRISE));
                competences.add(new Competence("Être capable de rechercher et sélectionner l'information", 0, null, Categorie.ENTREPRISE));
                competences.add(new Competence("Être capable d'assurer la conception", 0, null, Categorie.TECHNIQUE));

                competenceRepository.saveAll(competences);
                logger.info("Competences created successfully.");
            }

            // Create Evaluations
            if (evaluationRepository.count() == 0) {
                List<Evaluation> evaluations = new ArrayList<>();
                evaluations.add(new Evaluation("NA", "Non applicable", null));
                evaluations.add(new Evaluation("Débutant", "Applique, avec aide, les savoirs", null));
                evaluations.add(new Evaluation("Autonome", "Applique les pratiques de façon autonome", null));
                evaluations.add(new Evaluation("Autonome +", "Résout les problèmes complexes", null));

                evaluationRepository.saveAll(evaluations);
                logger.info("Evaluations created successfully.");
            }

            return true;
        } catch (Exception e) {
            logger.error("Failed to create competences or evaluations: {}", e.getMessage());
            throw new IllegalStateException("Application startup failed due to competences or evaluations creation error.", e);
        }
    }
}

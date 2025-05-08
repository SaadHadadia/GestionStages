package com.example.GestionStages.mappers;

import com.example.GestionStages.dto.*;
import com.example.GestionStages.models.*;
import com.example.GestionStages.requests.CreateUserRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service responsable de la conversion entre les entités et les DTOs
 */
@Service
public class MapperService {

    /**
     * Conversion User -> UserDTO
     */
    public UserDTO toUserDTO(User user) {
        if (user instanceof Tuteur) {
            return toTuteurDTO((Tuteur) user);
        } else if (user instanceof Stagiaire) {
            return toStagiaireDTO((Stagiaire) user);
        } else {
            return new UserDTO(user.getUsername(), user.getFirstname(), user.getLastname(), "User");
        }
    }

    /**
     * Conversion Tuteur -> TuteurDTO
     */
    public TuteurDTO toTuteurDTO(Tuteur tuteur) {
        return new TuteurDTO(
                tuteur.getUsername(),
                tuteur.getFirstname(),
                tuteur.getLastname(),
                tuteur.getEntreprise()
        );
    }

    /**
     * Conversion Stagiaire -> StagiaireDTO
     */
    public StagiaireDTO toStagiaireDTO(Stagiaire stagiaire) {
        if (stagiaire.getInstitution() == null) {
            throw new IllegalStateException("Institution est requis pour un stagiaire");
        }
        return new StagiaireDTO(
                stagiaire.getUsername(),
                stagiaire.getFirstname(),
                stagiaire.getLastname(),
                stagiaire.getInstitution()
        );
    }

    public User fromCreateUserRequest(CreateUserRequest request) {
        if ("Tuteur".equalsIgnoreCase(request.getType())) {
            return new Tuteur(request.getUsername(), request.getFirstname(), request.getLastname(), request.getEntreprise());
        } else if ("Stagiaire".equalsIgnoreCase(request.getType())) {
            return new Stagiaire(request.getUsername(), request.getFirstname(), request.getLastname(), request.getInstitution());
        } else {
            throw new IllegalArgumentException("Type d'utilisateur inconnu : " + request.getType());
        }
    }

    /**
     * Conversion Stage -> StageDTO
     */
    public StageDTO toStageDTO(Stage stage) {
        List<PeriodeDTO> periodeDTOs = stage.getPeriodes() != null ?
                stage.getPeriodes().stream().map(this::toPeriodeDTO).collect(Collectors.toList()) : null;
        return new StageDTO(stage.getId(), stage.getDescription(), stage.getObjectif(), stage.getEntreprise(), periodeDTOs);
    }

    /**
     * Conversion Periode -> PeriodeDTO
     */
    public PeriodeDTO toPeriodeDTO(Periode periode) {
        List<AppreciationDTO> appreciationDTOs = periode.getAppreciations() != null ?
                periode.getAppreciations().stream().map(this::toAppreciationDTO).collect(Collectors.toList()) : null;
        return new PeriodeDTO(
                periode.getId(),
                periode.getDateDebut(),
                periode.getDateFin(),
                periode.getStage() != null ? periode.getStage().getId() : null,
                periode.getStagiaire() != null ? periode.getStagiaire().getUsername() : null,
                appreciationDTOs
        );
    }

    /**
     * Conversion Appreciation -> AppreciationDTO
     */
    public AppreciationDTO toAppreciationDTO(Appreciation appreciation) {
        List<EvaluationDTO> evaluationDTOs = appreciation.getEvaluations() != null ?
                appreciation.getEvaluations().stream().map(this::toEvaluationDTO).collect(Collectors.toList()) : null;
        List<CompetenceDTO> competenceDTOs = appreciation.getCompetences() != null ?
                appreciation.getCompetences().stream().map(this::toCompetenceDTO).collect(Collectors.toList()) : null;
        return new AppreciationDTO(
                appreciation.getId(),
                appreciation.getCommentaire(),
                appreciation.getTuteur() != null ? appreciation.getTuteur().getUsername() : null,
                appreciation.getPeriode() != null ? appreciation.getPeriode().getId() : null,
                evaluationDTOs,
                competenceDTOs
        );
    }

    /**
     * Conversion Competence -> CompetenceDTO
     */
    public CompetenceDTO toCompetenceDTO(Competence competence) {
        List<CategorieDTO> categorieDTOs = competence.getCategories() != null ?
                competence.getCategories().stream().map(this::toCategorieDTO).collect(Collectors.toList()) : null;
        return new CompetenceDTO(
                competence.getId(),
                competence.getIntitule(),
                competence.getNote(),
                competence.getAppreciation() != null ? competence.getAppreciation().getId() : null,
                categorieDTOs
        );
    }

    /**
     * Conversion Categorie -> CategorieDTO
     */
    public CategorieDTO toCategorieDTO(Categorie categorie) {
        return new CategorieDTO(
                categorie.getId(),
                categorie.getIntitule(),
                categorie.getValeur(),
                categorie.getCompetence() != null ? categorie.getCompetence().getId() : null
        );
    }

    /**
     * Conversion Evaluation -> EvaluationDTO
     */
    public EvaluationDTO toEvaluationDTO(Evaluation evaluation) {
        return new EvaluationDTO(
                evaluation.getId(),
                evaluation.getCategorie(),
                evaluation.getValeur(),
                evaluation.getAppreciation() != null ? evaluation.getAppreciation().getId() : null
        );
    }

    /**
     * Conversion StageDTO -> Stage
     */
    public Stage toStage(StageDTO stageDTO) {
        Stage stage = new Stage();
        stage.setId(stageDTO.getId());
        stage.setDescription(stageDTO.getDescription());
        stage.setObjectif(stageDTO.getObjectif());
        stage.setEntreprise(stageDTO.getEntreprise());
        return stage;
    }

    /**
     * Conversion PeriodeDTO -> Periode
     */
    public Periode toPeriode(PeriodeDTO periodeDTO, Stage stage, Stagiaire stagiaire) {
        Periode periode = new Periode();
        periode.setId(periodeDTO.getId());
        periode.setDateDebut(periodeDTO.getDateDebut());
        periode.setDateFin(periodeDTO.getDateFin());
        periode.setStage(stage);
        periode.setStagiaire(stagiaire);
        return periode;
    }

    /**
     * Conversion AppreciationDTO -> Appreciation
     */
    public Appreciation toAppreciation(AppreciationDTO appreciationDTO, Tuteur tuteur, Periode periode) {
        Appreciation appreciation = new Appreciation();
        appreciation.setId(appreciationDTO.getId());
        appreciation.setCommentaire(appreciationDTO.getCommentaire());
        appreciation.setTuteur(tuteur);
        appreciation.setPeriode(periode);
        return appreciation;
    }

    /**
     * Conversion CompetenceDTO -> Competence
     */
    public Competence toCompetence(CompetenceDTO competenceDTO, Appreciation appreciation) {
        Competence competence = new Competence();
        competence.setId(competenceDTO.getId());
        competence.setIntitule(competenceDTO.getIntitule());
        competence.setNote(competenceDTO.getNote());
        competence.setAppreciation(appreciation);
        return competence;
    }

    /**
     * Conversion CategorieDTO -> Categorie
     */
    public Categorie toCategorie(CategorieDTO categorieDTO, Competence competence) {
        Categorie categorie = new Categorie();
        categorie.setId(categorieDTO.getId());
        categorie.setIntitule(categorieDTO.getIntitule());
        categorie.setValeur(categorieDTO.getValeur());
        categorie.setCompetence(competence);
        return categorie;
    }

    /**
     * Conversion EvaluationDTO -> Evaluation
     */
    public Evaluation toEvaluation(EvaluationDTO evaluationDTO, Appreciation appreciation) {
        Evaluation evaluation = new Evaluation();
        evaluation.setId(evaluationDTO.getId());
        evaluation.setCategorie(evaluationDTO.getCategorie());
        evaluation.setValeur(evaluationDTO.getValeur());
        evaluation.setAppreciation(appreciation);
        return evaluation;
    }

    // Méthodes utilitaires pour convertir des listes

    public List<UserDTO> toUserDTOs(List<User> users) {
        return users.stream().map(this::toUserDTO).collect(Collectors.toList());
    }

    public List<StageDTO> toStageDTOs(List<Stage> stages) {
        return stages.stream().map(this::toStageDTO).collect(Collectors.toList());
    }

    public List<PeriodeDTO> toPeriodeDTOs(List<Periode> periodes) {
        return periodes.stream().map(this::toPeriodeDTO).collect(Collectors.toList());
    }

    public List<AppreciationDTO> toAppreciationDTOs(List<Appreciation> appreciations) {
        return appreciations.stream().map(this::toAppreciationDTO).collect(Collectors.toList());
    }

    public List<CompetenceDTO> toCompetenceDTOs(List<Competence> competences) {
        return competences.stream().map(this::toCompetenceDTO).collect(Collectors.toList());
    }

    public List<CategorieDTO> toCategorieDTOs(List<Categorie> categories) {
        return categories.stream().map(this::toCategorieDTO).collect(Collectors.toList());
    }

    public List<EvaluationDTO> toEvaluationDTOs(List<Evaluation> evaluations) {
        return evaluations.stream().map(this::toEvaluationDTO).collect(Collectors.toList());
    }

    /**
     * Conversion TuteurDTO -> Tuteur
     */
    public Tuteur toTuteur(TuteurDTO tuteurDTO) {
        Tuteur tuteur = new Tuteur();
        tuteur.setUsername(tuteurDTO.getUsername());
        tuteur.setFirstname(tuteurDTO.getFirstname());
        tuteur.setLastname(tuteurDTO.getLastname());
        tuteur.setEntreprise(tuteurDTO.getEntreprise());
        return tuteur;
    }

}

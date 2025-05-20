package com.example.GestionStages.mappers;

import com.example.GestionStages.dto.*;
import com.example.GestionStages.models.*;
import jakarta.persistence.DiscriminatorValue;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MapperService {

    // User mapping
    public UserDTO toUserDTO(User user) {
        if (user == null) return null;
        UserDTO dto = new UserDTO();
        dto.setUsername(user.getUsername());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setType(user.getClass().getAnnotation(DiscriminatorValue.class).value());
        return dto;
    }

    // Admin mapping
    public AdminDTO toAdminDTO(Admin admin) {
        if (admin == null) return null;
        return new AdminDTO(
                admin.getUsername(),
                admin.getFirstname(),
                admin.getLastname()
        );
    }

    public Admin toAdmin(AdminDTO dto) {
        if (dto == null) return null;
        return new Admin(
                dto.getUsername(),
                null, // Password not included in DTO for security
                dto.getFirstname(),
                dto.getLastname()
        );
    }

    // Stagiaire mapping
    public StagiaireDTO toStagiaireDTO(Stagiaire stagiaire) {
        if (stagiaire == null) return null;

        List<Long> periodeIds = stagiaire.getPeriodes() != null ?
                stagiaire.getPeriodes().stream()
                        .map(Periode::getId)
                        .collect(Collectors.toList()) :
                null;

        return new StagiaireDTO(
                stagiaire.getUsername(),
                stagiaire.getFirstname(),
                stagiaire.getLastname(),
                stagiaire.getInstitution(),
                periodeIds
        );
    }

    public Stagiaire toStagiaire(StagiaireDTO dto) {
        if (dto == null) return null;
        Stagiaire stagiaire = new Stagiaire(
                dto.getUsername(),
                null, // Password not included in DTO for security
                dto.getFirstname(),
                dto.getLastname()
        );
        stagiaire.setInstitution(dto.getInstitution());
        return stagiaire;
    }

    // Tuteur mapping
    public TuteurDTO toTuteurDTO(Tuteur tuteur) {
        if (tuteur == null) return null;

        List<Long> appreciationIds = tuteur.getAppreciations() != null ?
                tuteur.getAppreciations().stream()
                        .map(Appreciation::getId)
                        .collect(Collectors.toList()) :
                null;

        return new TuteurDTO(
                tuteur.getUsername(),
                tuteur.getFirstname(),
                tuteur.getLastname(),
                tuteur.getEntreprise(),
                appreciationIds
        );
    }

    public Tuteur toTuteur(TuteurDTO dto) {
        if (dto == null) return null;
        Tuteur tuteur = new Tuteur(
                dto.getUsername(),
                null, // Password not included in DTO for security
                dto.getFirstname(),
                dto.getLastname()
        );
        tuteur.setEntreprise(dto.getEntreprise());
        return tuteur;
    }

    // Stage mapping
    public StageDTO toStageDTO(Stage stage) {
        if (stage == null) return null;

        List<Long> periodeIds = stage.getPeriodes() != null ?
                stage.getPeriodes().stream()
                        .map(Periode::getId)
                        .collect(Collectors.toList()) :
                null;

        return new StageDTO(
                stage.getId(),
                stage.getDescription(),
                stage.getObjectif(),
                stage.getEntreprise(),
                periodeIds
        );
    }

    public Stage toStage(StageDTO dto) {
        if (dto == null) return null;
        return new Stage(
                dto.getDescription(),
                dto.getObjectif(),
                dto.getEntreprise(),
                null // Periodes will be set separately
        );
    }

    // Periode mapping
    public PeriodeDTO toPeriodeDTO(Periode periode) {
        if (periode == null) return null;

        List<Long> appreciationIds = periode.getAppreciations() != null ?
                periode.getAppreciations().stream()
                        .map(Appreciation::getId)
                        .collect(Collectors.toList()) :
                null;

        return new PeriodeDTO(
                periode.getId(),
                periode.getDateDebut(),
                periode.getDateFin(),
                periode.getStage() != null ? periode.getStage().getId() : null,
                periode.getStagiaire() != null ? periode.getStagiaire().getUsername() : null,
                appreciationIds
        );
    }

    public Periode toPeriode(PeriodeDTO dto, Stage stage, Stagiaire stagiaire) {
        if (dto == null) return null;
        return new Periode(
                dto.getDateDebut(),
                dto.getDateFin(),
                stage,
                stagiaire
        );
    }

    // Appreciation mapping
    public AppreciationDTO toAppreciationDTO(Appreciation appreciation) {
        if (appreciation == null) return null;
        return new AppreciationDTO(
                appreciation.getId(),
//                appreciation.getCommentaire(),
                appreciation.getTuteur() != null ? appreciation.getTuteur().getUsername() : null,
                appreciation.getPeriode() != null ? appreciation.getPeriode().getId() : null,
                appreciation.getEvaluation() != null ? appreciation.getEvaluation().getId() : null,
                appreciation.getCompetence() != null ? appreciation.getCompetence().getId() : null
        );
    }

    public Appreciation toAppreciation(AppreciationDTO dto, Tuteur tuteur, Periode periode) {
        if (dto == null) return null;
        return new Appreciation(
//                dto.getCommentaire(),
                tuteur,
                periode
        );
    }

    // Competence mapping
    public CompetenceDTO toCompetenceDTO(Competence competence) {
        if (competence == null) return null;

        List<Long> appreciationIds = competence.getAppreciations() != null ?
                competence.getAppreciations().stream()
                        .map(Appreciation::getId)
                        .collect(Collectors.toList()) :
                null;

        return new CompetenceDTO(
                competence.getId(),
                competence.getIntitule(),
                competence.getNote(),
                competence.getCategorie() != null ? competence.getCategorie().name() : null,
                appreciationIds
        );
    }

    public Competence toCompetence(CompetenceDTO dto) {
        if (dto == null) return null;
        return new Competence(
                dto.getIntitule(),
                dto.getNote(),
                null,
                dto.getCategorie() != null ? Categorie.valueOf(dto.getCategorie()) : null
        );
    }

    // Evaluation mapping
    public EvaluationDTO toEvaluationDTO(Evaluation evaluation) {
        if (evaluation == null) return null;

        List<Long> appreciationIds = evaluation.getAppreciations() != null ?
                evaluation.getAppreciations().stream()
                        .map(Appreciation::getId)
                        .collect(Collectors.toList()) :
                null;

        return new EvaluationDTO(
                evaluation.getId(),
                evaluation.getCategorie(),
                evaluation.getValeur(),
                appreciationIds
        );
    }

    public Evaluation toEvaluation(EvaluationDTO dto) {
        if (dto == null) return null;
        return new Evaluation(
                dto.getCategorie(),
                dto.getValeur(),
                null
        );
    }

    // Categorie mapping
    public CategorieDTO toCategorieDTO(Categorie categorie) {
        if (categorie == null) return null;
        return new CategorieDTO(
                categorie.name(),
                categorie.getMessage()
        );
    }

    // List mappings
    public List<UserDTO> toUserDTOList(List<User> users) {
        return users.stream().map(this::toUserDTO).collect(Collectors.toList());
    }

    public List<AdminDTO> toAdminDTOList(List<Admin> admins) {
        return admins.stream().map(this::toAdminDTO).collect(Collectors.toList());
    }

    public List<StagiaireDTO> toStagiaireDTOList(List<Stagiaire> stagiaires) {
        return stagiaires.stream().map(this::toStagiaireDTO).collect(Collectors.toList());
    }

    public List<TuteurDTO> toTuteurDTOList(List<Tuteur> tuteurs) {
        return tuteurs.stream().map(this::toTuteurDTO).collect(Collectors.toList());
    }

    public List<StageDTO> toStageDTOList(List<Stage> stages) {
        return stages.stream().map(this::toStageDTO).collect(Collectors.toList());
    }

    public List<PeriodeDTO> toPeriodeDTOList(List<Periode> periodes) {
        return periodes.stream().map(this::toPeriodeDTO).collect(Collectors.toList());
    }

    public List<AppreciationDTO> toAppreciationDTOList(List<Appreciation> appreciations) {
        return appreciations.stream().map(this::toAppreciationDTO).collect(Collectors.toList());
    }

    public List<CompetenceDTO> toCompetenceDTOList(List<Competence> competences) {
        return competences.stream().map(this::toCompetenceDTO).collect(Collectors.toList());
    }

    public List<EvaluationDTO> toEvaluationDTOList(List<Evaluation> evaluations) {
        return evaluations.stream().map(this::toEvaluationDTO).collect(Collectors.toList());
    }

    public List<CategorieDTO> toCategorieDTOList(List<Categorie> categories) {
        return categories.stream().map(this::toCategorieDTO).collect(Collectors.toList());
    }
}
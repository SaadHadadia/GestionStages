package com.example.GestionStages.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationStageDTO {
    private Long periodeId;
    private String tuteurUsername;
    private StageInputDTO stage;
    private List<AppreciationInputDTO> appreciations;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AppreciationInputDTO {
        private Long competenceId;
        private Long evaluationId;
        private String commentaire;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StageInputDTO {
        private String stagiaireId;
        private String description;
        private String objectif;
    }

}
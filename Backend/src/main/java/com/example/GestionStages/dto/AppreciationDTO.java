package com.example.GestionStages.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppreciationDTO {
    private Long id;
    private String commentaire;
    private String tuteurUsername;
    private Long periodeId;
    private List<EvaluationDTO> evaluations;
    private List<CompetenceDTO> competences;
}

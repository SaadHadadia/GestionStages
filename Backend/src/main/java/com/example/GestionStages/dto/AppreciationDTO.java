package com.example.GestionStages.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppreciationDTO {
    private Long id;
    private String commentaire;
    private String tuteurUsername;
    private Long periodeId;
    private Long evaluationId;
    private Long competenceId;
}
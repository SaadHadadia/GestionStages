package com.example.GestionStages.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationDTO {
    private Long id;
    private String categorie;
    private String valeur;
    private List<Long> appreciationIds;
}
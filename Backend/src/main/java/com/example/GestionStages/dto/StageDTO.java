package com.example.GestionStages.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StageDTO {
    private Long id;
    private String description;
    private String objectif;
    private String entreprise;
    private List<PeriodeDTO> periodes;
}

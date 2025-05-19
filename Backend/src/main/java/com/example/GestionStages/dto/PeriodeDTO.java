package com.example.GestionStages.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeriodeDTO {
    private Long id;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Long stageId;
    private String stagiaireUsername;
    private List<Long> appreciationIds;
}
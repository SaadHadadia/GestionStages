package com.example.GestionStages.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttribuerStageDTO {
    private String tuteurId;
    private String stagiaireId;
    private LocalDate dateDebut;
    private LocalDate dateFin;
}

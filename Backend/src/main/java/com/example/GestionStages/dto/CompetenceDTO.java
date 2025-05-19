package com.example.GestionStages.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompetenceDTO {
    private Long id;
    private String intitule;
    private Integer note;
    private String categorie;
    private List<Long> appreciationIds;
}
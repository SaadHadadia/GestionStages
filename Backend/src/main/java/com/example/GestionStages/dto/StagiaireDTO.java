package com.example.GestionStages.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StagiaireDTO {
    private String username;
    private String firstname;
    private String lastname;
    private String institution;
    private List<Long> periodeIds;
}
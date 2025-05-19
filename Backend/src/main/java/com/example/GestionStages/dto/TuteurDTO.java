package com.example.GestionStages.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TuteurDTO {
    private String username;
    private String firstname;
    private String lastname;
    private String entreprise;
    private List<Long> appreciationIds;
}
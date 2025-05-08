package com.example.GestionStages.dto;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class StagiaireDTO extends UserDTO {
    private String institution;

    public StagiaireDTO(String username, String firstname, String lastname, String institution) {
        super(username, firstname, lastname, "Stagiaire");
        this.institution = institution;
    }
}


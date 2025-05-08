package com.example.GestionStages.dto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TuteurDTO extends UserDTO {
    private String entreprise;

    public TuteurDTO(String username, String firstname, String lastname, String entreprise) {
        super(username, firstname, lastname, "Tuteur");
        this.entreprise = entreprise;
    }
}


package com.example.GestionStages.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("Stagaire")
public class Stagaire extends User {

    private String Institution;

    public Stagaire(String username, String password, String firstname, String lastname, String institution) {
        super(username, password, firstname, lastname);
        Institution = institution;
    }
}

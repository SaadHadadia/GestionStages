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
@DiscriminatorValue("Tuteur")
public class Tuteur extends User {

    public String entreprise;

    public Tuteur(String username, String password, String firstname, String lastname, String entreprise) {
        super(username, password, firstname, lastname);
        this.entreprise = entreprise;
    }
}

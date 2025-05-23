package com.example.GestionStages.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("Tuteur")
public class Tuteur extends User {

    private String entreprise;

    @JsonManagedReference(value = "tuteur-appreciation")
    @OneToMany(mappedBy = "tuteur", cascade = CascadeType.ALL)
    private List<Appreciation> appreciations;

    public Tuteur(String username, String password, String firstname, String lastname) {
        super(username, password, firstname, lastname);
        this.entreprise = entreprise;
        this.appreciations = appreciations;
    }
}

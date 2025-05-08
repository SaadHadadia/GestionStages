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
@DiscriminatorValue("Stagiaire")
public class Stagiaire extends User {

    private String institution;

    @JsonManagedReference(value = "stagiaire-periode")
    @OneToMany(mappedBy = "stagiaire", cascade = CascadeType.ALL)
    private List<Periode> periodes;

    public Stagiaire(String username, String password, String firstname, String lastname) {
        super(username, password, firstname, lastname);
        this.institution = institution;
        this.periodes = periodes;
    }
}

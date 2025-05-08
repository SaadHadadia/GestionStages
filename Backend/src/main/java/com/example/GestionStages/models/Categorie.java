package com.example.GestionStages.models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String intitule;
    private String valeur;

    @ManyToOne
    @JoinColumn(name = "competence_id")
    @JsonBackReference(value = "competence-categorie")
    private Competence competence;

    public Categorie(String intitule, String valeur, Competence competence) {
        this.intitule = intitule;
        this.valeur = valeur;
        this.competence = competence;
    }
}
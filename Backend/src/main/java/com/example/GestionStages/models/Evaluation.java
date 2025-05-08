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
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categorie;
    private String valeur;

    @ManyToOne
    @JoinColumn(name = "appreciation_id")
    @JsonBackReference(value = "appreciation-evaluation")
    private Appreciation appreciation;

    public Evaluation(String categorie, String valeur, Appreciation appreciation) {
        this.categorie = categorie;
        this.valeur = valeur;
        this.appreciation = appreciation;
    }
}
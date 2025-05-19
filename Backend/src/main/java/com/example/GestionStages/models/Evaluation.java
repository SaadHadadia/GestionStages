package com.example.GestionStages.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    @JsonManagedReference(value = "appreciation-evaluation")
    @OneToMany(mappedBy = "evaluation", cascade = CascadeType.ALL)
    private List<Appreciation> appreciations;

    public Evaluation(String categorie, String valeur, List<Appreciation> appreciations) {
        this.categorie = categorie;
        this.valeur = valeur;
        this.appreciations = appreciations;
    }
}
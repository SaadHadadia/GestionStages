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
public class Competence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String intitule;
    private Integer note;

    @JsonManagedReference(value = "appreciation-competence")
    @OneToMany(mappedBy = "competence", cascade = CascadeType.ALL)
    private List<Appreciation> appreciations;

    private Categorie categorie;

    public Competence(String intitule, Integer note,List<Appreciation> appreciations, Categorie categorie) {
        this.intitule = intitule;
        this.note = note;
        this.appreciations = appreciations;
        this.categorie = categorie;
    }
}
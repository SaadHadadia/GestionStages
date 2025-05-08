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

    @ManyToOne
    @JoinColumn(name = "appreciation_id")
    @JsonBackReference(value = "appreciation-competence")
    private Appreciation appreciation;

    @JsonManagedReference(value = "competence-categorie")
    @OneToMany(mappedBy = "competence", cascade = CascadeType.ALL)
    private List<Categorie> categories;

    public Competence(String intitule, Integer note, Appreciation appreciation,List<Categorie> categories) {
        this.intitule = intitule;
        this.note = note;
        this.appreciation = appreciation;
        this.categories = categories;
    }
}
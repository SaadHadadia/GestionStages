package com.example.GestionStages.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Periode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    @ManyToOne
    @JoinColumn(name = "stage_id")
    @JsonBackReference(value = "stage-periode")
    private Stage stage;

    @ManyToOne
    @JoinColumn(name = "stagiaire_username")
    @JsonBackReference(value = "stagiaire-periode")
    private Stagiaire stagiaire;

    @JsonManagedReference(value = "periode-appreciation")
    @OneToMany(mappedBy = "periode", cascade = CascadeType.ALL)
    private List<Appreciation> appreciations;

    public Periode(LocalDate dateDebut, LocalDate dateFin, Stage stage, Stagiaire stagiaire) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.stage = stage;
        this.stagiaire = stagiaire;
    }
}

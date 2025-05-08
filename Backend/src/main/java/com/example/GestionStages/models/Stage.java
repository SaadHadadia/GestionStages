package com.example.GestionStages.models;
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
public class Stage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String objectif;
    private String entreprise;

    @JsonManagedReference(value = "stage-periode")
    @OneToMany(mappedBy = "stage", cascade = CascadeType.ALL)
    private List<Periode> periodes;

    public Stage(String description, String objectif, String entreprise, List<Periode> periodes) {
        this.description = description;
        this.objectif = objectif;
        this.entreprise = entreprise;
        this.periodes = periodes;
    }
}

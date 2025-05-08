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
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"tuteur_username", "periode_id"})
})
public class Appreciation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String commentaire;

    @ManyToOne
    @JoinColumn(name = "tuteur_username")
    @JsonBackReference(value = "tuteur-appreciation")
    private Tuteur tuteur;

    @ManyToOne
    @JoinColumn(name = "periode_id")
    @JsonBackReference(value = "periode-appreciation")
    private Periode periode;

    @JsonManagedReference(value = "appreciation-evaluation")
    @OneToMany(mappedBy = "appreciation", cascade = CascadeType.ALL)
    private List<Evaluation> evaluations;

    @JsonManagedReference(value = "appreciation-competence")
    @OneToMany(mappedBy = "appreciation", cascade = CascadeType.ALL)
    private List<Competence> competences;

    public Appreciation(String commentaire, Tuteur tuteur, Periode periode) {
        this.commentaire = commentaire;
        this.tuteur = tuteur;
        this.periode = periode;
    }
}
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

    @ManyToOne
    @JoinColumn(name = "evaluation_id")
    @JsonBackReference(value = "appreciation-evaluation")
    private Evaluation evaluation;

    @ManyToOne
    @JoinColumn(name = "competence_id")
    @JsonBackReference(value = "appreciation-competence")
    private Competence competence;

    public Appreciation(String commentaire, Tuteur tuteur, Periode periode) {
        this.commentaire = commentaire;
        this.tuteur = tuteur;
        this.periode = periode;
    }
}
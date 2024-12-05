package fr.uga.l3miage.pc.prisonersdilemma.models;
import lombok.*;

import javax.persistence.*;
import java.util.List;
@Table
@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class PartieEntity {
   @SequenceGenerator(
            name = "sequence_partie",
            sequenceName = "partie_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence_partie"
    )
    @Id
    private Integer id;
    private Integer nbTours;

    @OneToMany(mappedBy = "partie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TourEntity> tours;

    @OneToOne(cascade = CascadeType.PERSIST)
    private JoueurEntity joueur1;
    @OneToOne(cascade = CascadeType.PERSIST)
    private JoueurEntity joueur2;
    @Transient
    private Strategie strategieJoueur1; //ne reste pas dans la base

    @Transient
    private Strategie strategieJoueur2; //ne reste pas dans la base

    private String typeStrategieJoueur1;
    private String typeStrategieJoueur2;

    private boolean estPret;

}
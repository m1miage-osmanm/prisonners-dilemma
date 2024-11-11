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

    @OneToMany(mappedBy = "partie")
    private List<TourEntity> tours;

    @OneToOne(cascade = CascadeType.PERSIST)
    private JoueurEntity joueur1;
    @OneToOne(cascade = CascadeType.PERSIST)
    private JoueurEntity joueur2;
    private Integer idStrategie1;
    private Integer idStrategie2;
    private boolean estPret;

}
package fr.uga.l3miage.pc.prisonersdilemma.domain.models;
import lombok.*;
import javax.persistence.*;

@Table
@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class JoueurEntity {
    @SequenceGenerator(
            name = "sequence_joueur",
            sequenceName = "joueur_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence_joueur"
    )
    @Id
    private Long id;
    private String username;

    @ManyToOne
    @JoinColumn(name = "partie_id")
    private PartieEntity partie;




}

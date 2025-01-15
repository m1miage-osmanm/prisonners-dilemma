package fr.uga.l3miage.pc.prisonersdilemma.domain.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TourEntity {
    @Id
    private int id;

    private TypeDecision decisionJoueur1; // coopérer ou trahir
    private TypeDecision decisionJoueur2;

    private Integer scoreJoueur1;
    private Integer scoreJoueur2;

    @ManyToOne
    private PartieEntity partie;

    public boolean isTourComplet() {
        return decisionJoueur1 != null && decisionJoueur2 != null;
    }

}
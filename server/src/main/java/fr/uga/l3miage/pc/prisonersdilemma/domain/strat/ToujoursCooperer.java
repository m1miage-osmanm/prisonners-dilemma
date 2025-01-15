package fr.uga.l3miage.pc.prisonersdilemma.domain.strat;



import fr.uga.l3miage.pc.prisonersdilemma.domain.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.TourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.TypeDecision;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ToujoursCooperer extends Strategie {
    public ToujoursCooperer() {
        super("ToujoursTrahir", "-trahir l'adversaire toujours");
    }
    @Override
    public TypeDecision determinerDecision(List<TourEntity> tours,
                                           JoueurEntity joueur) {
        return TypeDecision.COOPERER;
    }
}

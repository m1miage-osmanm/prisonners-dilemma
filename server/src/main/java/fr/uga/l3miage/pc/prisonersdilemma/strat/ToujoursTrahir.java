package fr.uga.l3miage.pc.prisonersdilemma.strat;


import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.models.TourEntity;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ToujoursTrahir extends Strategie {
    public ToujoursTrahir() {
        super("ToujoursTrahir", "-trahir l'adversaire toujours");
    }
    @Override
    public String determinerDecision(List<TourEntity> tours,
                                     JoueurEntity joueur1) {
        return "t";
    }
}

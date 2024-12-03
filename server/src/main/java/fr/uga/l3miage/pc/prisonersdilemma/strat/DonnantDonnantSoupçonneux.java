package fr.uga.l3miage.pc.prisonersdilemma.strat;


import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.models.TourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.TypeDecision;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class DonnantDonnantSoupçonneux extends Strategie {

    public DonnantDonnantSoupçonneux() {
        super(" DonnantDonnantSoupçonneux","Comme donnant donnant, mais commencer par trahir ");
    }

    @Override
    public TypeDecision determinerDecision(List<TourEntity> tours, JoueurEntity joueur) {
        if (tours.isEmpty()) {
            return TypeDecision.TRAHIR;}
        TourEntity dernierTour = tours.get(tours.size() - 1);

        if (joueur.equals(dernierTour.getPartie().getJoueur1())) {
            return dernierTour.getDecisionJoueur2();
        } else {
            return dernierTour.getDecisionJoueur1();
        }


    }
}

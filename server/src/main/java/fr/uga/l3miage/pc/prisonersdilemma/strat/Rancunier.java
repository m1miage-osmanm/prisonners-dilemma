package fr.uga.l3miage.pc.prisonersdilemma.strat;

import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.models.TourEntity;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class Rancunier extends Strategie {

    private boolean trahison = false;

    public Rancunier() {
        super("Rancunier", "Coopérer jusqu'à ce que l'adversaire trahisse. Ensuite, toujours trahir.");
    }

    @Override
    public String determinerDecision(List<TourEntity> tours, JoueurEntity joueur) {
        if (!trahison && !tours.isEmpty()) {
            TourEntity dernierTour = tours.get(tours.size() - 1);
            String decisionAdversaireDernierTour = joueur.equals(dernierTour.getPartie().getJoueur1())
                    ? dernierTour.getDecisionJoueur2()
                    : dernierTour.getDecisionJoueur1();

            if ("t".equals(decisionAdversaireDernierTour)) {
                trahison = true;
            }
        }

        return trahison ? "t" : "c";
    }
}

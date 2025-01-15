package fr.uga.l3miage.pc.prisonersdilemma.domain.strat;

import fr.uga.l3miage.pc.prisonersdilemma.domain.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.TourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.TypeDecision;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class Rancunier extends Strategie {

    private boolean trahison = false;

    public Rancunier() {
        super("Rancunier", "Coopérer jusqu'à ce que l'adversaire trahisse. Ensuite, toujours trahir.");
    }

    @Override
    public TypeDecision determinerDecision(List<TourEntity> tours, JoueurEntity joueur) {
        if (!trahison && !tours.isEmpty()) {
            TourEntity dernierTour = tours.get(tours.size() - 1);
            TypeDecision decisionAdversaireDernierTour = joueur.equals(dernierTour.getPartie().getJoueur1())
                    ? dernierTour.getDecisionJoueur2()
                    : dernierTour.getDecisionJoueur1();

            if (TypeDecision.TRAHIR.equals(decisionAdversaireDernierTour)) {
                trahison = true;
            }
        }

        return trahison ? TypeDecision.TRAHIR : TypeDecision.COOPERER;
    }
}

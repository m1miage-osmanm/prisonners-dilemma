package fr.uga.l3miage.pc.prisonersdilemma.strat;


import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.models.TourEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
@Component
public class VraiPacificateur extends Strategie {

    private final Random random = new Random();

    public VraiPacificateur() {
        super("VraiPacificateur",
                "Coopérer si l'adversaire ne trahit pas deux fois de suite. Trahir alors immédiatement mais essayer parfois de faire la paix en coopérant au lieu de trahir.");
    }

    @Override
    public String determinerDecision(List<TourEntity> tours,
                                     JoueurEntity joueur) {

        if (tours.size() < 2) {
            return "c";
        }

        TourEntity dernierTour = tours.get(tours.size() - 1);
        TourEntity avantDernierTour = tours.get(tours.size() - 2);

        String decisionAdversaireDernierTour;
        String decisionAdversaireAvantDernierTour;

        if (joueur.equals(dernierTour.getPartie().getJoueur1())) {
            decisionAdversaireDernierTour = dernierTour.getDecisionJoueur2();
            decisionAdversaireAvantDernierTour = avantDernierTour.getDecisionJoueur2();
        } else {
            decisionAdversaireDernierTour = dernierTour.getDecisionJoueur1();
            decisionAdversaireAvantDernierTour = avantDernierTour.getDecisionJoueur1();
        }

        if (decisionAdversaireDernierTour.equals("t") && decisionAdversaireAvantDernierTour.equals("t")) {
            // Trahir la plupart du temps et coopérer parfois (proba de 20% ici)
            return random.nextInt(10) < 2 ? "c" : "t";
        } else {
            return "c";
        }
    }
}

package fr.uga.l3miage.pc.prisonersdilemma.strat;


import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.models.TourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.TypeDecision;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
@Component
public class DonnantDeuxDonnantRandom extends Strategie {

    private final Random random = new Random();

    public DonnantDeuxDonnantRandom() {
        super("DonnantDeuxDonnantRandom", "Comme donnant-donnant sauf que l'adversaire doit faire le " +
                "même choix deux fois de suite avant la réciprocité. Joue parfois un coup au hasard.");
    }

    @Override
    public TypeDecision determinerDecision(List<TourEntity> tours,
                                           JoueurEntity joueur) {
        if (tours.size() < 2) {
            return TypeDecision.COOPERER;
        }

        if (random.nextInt(5) == 0) {
            return random.nextBoolean() ? TypeDecision.COOPERER : TypeDecision.TRAHIR;
        }

        TourEntity dernierTour = tours.get(tours.size() - 1);
        TourEntity avantDernierTour = tours.get(tours.size() - 2);

        TypeDecision decisionAdversaireDernierTour;
        TypeDecision decisionAdversaireAvantDernierTour;

        if (joueur.equals(dernierTour.getPartie().getJoueur1())) {
            decisionAdversaireDernierTour = dernierTour.getDecisionJoueur2();
            decisionAdversaireAvantDernierTour = avantDernierTour.getDecisionJoueur2();
        } else {
            decisionAdversaireDernierTour = dernierTour.getDecisionJoueur1();
            decisionAdversaireAvantDernierTour = avantDernierTour.getDecisionJoueur1();
        }

        if (decisionAdversaireDernierTour.equals(decisionAdversaireAvantDernierTour)) {
            return decisionAdversaireDernierTour;  // Réciprocité
        } else {
            return TypeDecision.COOPERER;  // Coopération par défaut sinon
        }
    }
}

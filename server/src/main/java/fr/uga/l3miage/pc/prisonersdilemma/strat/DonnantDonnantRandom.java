package fr.uga.l3miage.pc.prisonersdilemma.strat;


import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.models.TourEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
@Component
public class DonnantDonnantRandom extends Strategie {

    private final Random random = new Random();

    public DonnantDonnantRandom() {
        super("DonnantDonnantRandom", "Jouer comme le dernier coup de l'adversaire, mais jouer parfois un coup au\n" +
                "hasard");
    }

    @Override
    public String determinerDecision(List<TourEntity> tours,
                                     JoueurEntity joueur) {
        if (tours.isEmpty()) {
            return "c";   // Premier tour : coopérer
        }
        TourEntity dernierTour = tours.get(tours.size()-1);


        if (random.nextInt(5) == 0) {
            return random.nextBoolean() ? "c" : "t";
        }

        if (joueur.equals(dernierTour.getPartie().getJoueur1())) {
            return dernierTour.getDecisionJoueur2();
        } else {
            return dernierTour.getDecisionJoueur1();
        }
    }


}


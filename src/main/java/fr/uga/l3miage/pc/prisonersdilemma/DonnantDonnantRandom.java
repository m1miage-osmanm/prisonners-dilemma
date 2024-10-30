package fr.uga.l3miage.pc.prisonersdilemma;

import java.util.List;
import java.util.Random;

public class DonnantDonnantRandom extends Strategie {

    private final Random random = new Random();

    public DonnantDonnantRandom() {
        super("DonnantDonnantRandom", "Jouer comme le dernier coup de l'adversaire, mais jouer parfois un coup au\n" +
                "hasard");
    }

    @Override
    public String determinerDecision(List<Tour> tours,
                                     Joueur joueur) {
        if (tours.isEmpty()) {
            return "c";   // Premier tour : coopérer
        }
        Tour dernierTour = tours.get(tours.size()-1);


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


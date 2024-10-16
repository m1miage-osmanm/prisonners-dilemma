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
    public String determinerDecision(List<Tours> tours,
                                     Joueur joueur,
                                     Joueur adversaire) {
        if (tours.isEmpty()) {
            return "c";   // Premier tour : coopérer
        }
        Tours dernierTour = tours.get(tours.size()-1);

        boolean coupAleatoire = random.nextInt(2) == 0;

        if (coupAleatoire) {
            return random.nextBoolean()? "c" : "t";
        }
        if (joueur.equals(dernierTour.getPartie().getJoueur1())) {
            return dernierTour.getDecisionJoueur2(); // Adversaire est joueur2
        } else {
            return dernierTour.getDecisionJoueur1(); // Adversaire est joueur1
        }
    }


}


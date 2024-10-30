package fr.uga.l3miage.pc.prisonersdilemma;

import java.util.List;
import java.util.Random;

public class PavlovRandom extends Strategie {

    private final Random random = new Random();

    public PavlovRandom() {
        super("PavlovRandom", "Si 5 ou 3 points ont été obtenus au tour précédent, répéter le dernier choix, mais faire parfois des choix aléatoires.");
    }

    @Override
    public String determinerDecision(List<Tour> tours, Joueur joueur) {
        if (tours.isEmpty()) {
            return "c";
        }


        Tour dernierTour = tours.get(tours.size() - 1);
        String decisionPrecedente;
        int scorePrecedent;


        if (joueur.equals(dernierTour.getPartie().getJoueur1())) {
            decisionPrecedente = dernierTour.getDecisionJoueur1();
            scorePrecedent = dernierTour.getScoreJoueur1();
        } else {
            decisionPrecedente = dernierTour.getDecisionJoueur2();
            scorePrecedent = dernierTour.getScoreJoueur2();
        }

        if (scorePrecedent == 5 || scorePrecedent == 3) {

            if (random.nextInt(5) == 0) {
                return random.nextBoolean() ? "c" : "t";
            }
            return decisionPrecedente;
        } else {
            return "c";
        }
    }
}

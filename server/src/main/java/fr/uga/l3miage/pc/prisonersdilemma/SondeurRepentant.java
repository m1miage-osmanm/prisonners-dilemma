package fr.uga.l3miage.pc.prisonersdilemma;

import java.util.List;
import java.util.Random;

public class SondeurRepentant extends Strategie {

    final private Random random = new Random();
    private boolean trahisonTest = false;

    public SondeurRepentant() {
        super("SondeurRepentant", "Jouer comme le dernier coup de l'adversaire, mais parfois trahir au lieu de coopérer. \n" +
                "Si l'adversaire trahit en réponse au test, se montrer repentant en coopérant immédiatement.");
    }

    @Override
    public String determinerDecision(List<Tour> tours, Joueur joueur) {
        if (tours.isEmpty()) {
            return "c";
        }

        Tour dernierTour = tours.get(tours.size() - 1);
        String decisionAdversaireDernierTour = joueur.equals(dernierTour.getPartie().getJoueur1())
                ? dernierTour.getDecisionJoueur2()
                : dernierTour.getDecisionJoueur1();

        if (trahisonTest && decisionAdversaireDernierTour.equals("t")) {
            trahisonTest = false;
            return "c";
        }

        if (!trahisonTest && random.nextInt(5) == 0) {
            trahisonTest = true;
            return "t";
        }

        trahisonTest = false;
        return decisionAdversaireDernierTour;
    }
}

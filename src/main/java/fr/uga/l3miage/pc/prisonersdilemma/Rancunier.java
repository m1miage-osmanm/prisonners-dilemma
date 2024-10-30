package fr.uga.l3miage.pc.prisonersdilemma;

import java.util.List;

public class Rancunier extends Strategie {

    private boolean trahison = false;

    public Rancunier() {
        super("Rancunier", "Coopérer jusqu'à ce que l'adversaire trahisse. Ensuite, toujours trahir.");
    }

    @Override
    public String determinerDecision(List<Tour> tours, Joueur joueur) {
        if (!trahison && !tours.isEmpty()) {
            Tour dernierTour = tours.get(tours.size() - 1);
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

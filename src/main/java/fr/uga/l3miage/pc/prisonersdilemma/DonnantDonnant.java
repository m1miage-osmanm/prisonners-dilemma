package fr.uga.l3miage.pc.prisonersdilemma;

import java.util.List;

public class DonnantDonnant extends Strategie{



        public DonnantDonnant() {
            super("Donnant-Donnant", "jouer comme le dernier coup de l'adversaire.");
        }

        @Override
        public String determinerDecision(List<Tours> tours,
                                         Joueur joueur1,
                                         Joueur joueur2) {


            if (tours.isEmpty()) {
                return "c";   // Premier tour : coopérer
            }

            Tours dernierTour = tours.get(tours.size() - 1);

            if (joueur1.equals(dernierTour.getPartie().getJoueur1())) {
                return dernierTour.getDecisionJoueur2(); // Adversaire est joueur2
            } else {
                return dernierTour.getDecisionJoueur1(); // Adversaire est joueur1
            }
        }
}

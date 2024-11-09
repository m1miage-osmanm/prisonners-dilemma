package fr.uga.l3miage.pc.prisonersdilemma;

import java.util.List;

public class DonnantDonnant extends Strategie{



        public DonnantDonnant() {
            super("Donnant-Donnant", "jouer comme le dernier coup de l'adversaire.");
        }

        @Override
        public String determinerDecision(List<Tour> tours,
                                         Joueur joueur) {


            if (tours.isEmpty()) {
                return "c";   // Premier tour : coopérer
            }

            Tour dernierTour = tours.get(tours.size() - 1);

            if (joueur.equals(dernierTour.getPartie().getJoueur1())) {
                return dernierTour.getDecisionJoueur2(); // Adversaire est joueur2
            } else {
                return dernierTour.getDecisionJoueur1(); // Adversaire est joueur1
            }
        }
}

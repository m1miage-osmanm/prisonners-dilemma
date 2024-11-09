package fr.uga.l3miage.pc.prisonersdilemma;

import java.util.List;

public class DonnantDonnantSoupçonneux extends Strategie{

    public DonnantDonnantSoupçonneux() {
        super(" DonnantDonnantSoupçonneux","Comme donnant donnant, mais commencer par trahir ");
    }

    @Override
    public String determinerDecision(List<Tour> tours, Joueur joueur) {
        if (tours.isEmpty()) {
            return "t";}
        Tour dernierTour = tours.get(tours.size() - 1);

        if (joueur.equals(dernierTour.getPartie().getJoueur1())) {
            return dernierTour.getDecisionJoueur2();
        } else {
            return dernierTour.getDecisionJoueur1();
        }


    }
}

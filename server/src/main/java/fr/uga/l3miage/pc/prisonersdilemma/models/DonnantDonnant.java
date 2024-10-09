package fr.uga.l3miage.pc.prisonersdilemma.models;

import java.util.List;

public class DonnantDonnant extends StrategieEntity{
    public DonnantDonnant() {
        super("Donnant-Donnant", "jouer comme le dernier coup de l'adversaire.");
    }

    @Override
    public String determinerDecision(List<TourEntity> tours,
                                     JoueurEntity joueur,
                                     JoueurEntity adversaire) {


        if (tours.isEmpty()) {
            return "c";   // Premier tour : coopérer
        }

        TourEntity dernierTour = tours.get(tours.size() - 1);

        if (joueur.equals(dernierTour.getPartie().getJoueur1())) {
            return dernierTour.getDecisionJoueur2(); // Adversaire est joueur2
        } else {
            return dernierTour.getDecisionJoueur1(); // Adversaire est joueur1
        }
    }
}






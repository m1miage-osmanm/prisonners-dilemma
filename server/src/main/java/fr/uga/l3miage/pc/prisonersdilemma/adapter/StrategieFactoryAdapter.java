package fr.uga.l3miage.pc.prisonersdilemma.adapter;

import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.models.TourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.TypeDecision;
import fr.uga.l3miage.pc.stratégies.StrategieFactory;

import java.util.List;

public class StrategieFactoryAdapter {

    public static String[] tourToHistorique(List<TourEntity> tours, JoueurEntity joueur) {
        return tours.stream()
                .map(tour -> joueur.equals(tour.getPartie().getJoueur1())
                        ? convertToExternalFormat(tour.getDecisionJoueur2())
                        : convertToExternalFormat(tour.getDecisionJoueur1()))
                .toArray(String[]::new);
    }

    private static String convertToExternalFormat(TypeDecision decision) {
        return decision == TypeDecision.COOPERER ? "c" : "t";
    }

    public static Strategie getStrategie(String choixStrategie, List<TourEntity> tours, JoueurEntity joueur) {
        String[] historique = tourToHistorique(tours, joueur);

        fr.uga.l3miage.pc.stratégies.Strategie strategieExterne = StrategieFactory.getStrategie(choixStrategie, historique);

        return new StrategieAdapter(strategieExterne);
    }
}

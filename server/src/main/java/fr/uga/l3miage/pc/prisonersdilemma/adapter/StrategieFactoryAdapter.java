package fr.uga.l3miage.pc.prisonersdilemma.adapter;

import fr.uga.l3miage.pc.prisonersdilemma.domain.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.TourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.TypeDecision;



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

        // Utilisation de la bonne classe de la factory externe
        fr.uga.l3miage.pc.stratégies.Strategie strategieExterne = fr.uga.l3miage.pc.stratégies.StrategieFactory.getStrategie(choixStrategie, historique);

        // Retourne un adapter
        return new StrategieAdapter(strategieExterne);
    }
}

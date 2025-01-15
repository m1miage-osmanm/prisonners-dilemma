package fr.uga.l3miage.pc.prisonersdilemma.adapter;

import fr.uga.l3miage.pc.prisonersdilemma.domain.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.TourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.TypeDecision;

import java.util.List;

public class StrategieAdapter extends Strategie {
    private final fr.uga.l3miage.pc.stratégies.Strategie strategieExterne;  // Correction de l'import

    public StrategieAdapter(fr.uga.l3miage.pc.stratégies.Strategie strategieExterne) {
        super("Adapted " + strategieExterne.getClass().getSimpleName(),
                "Adapted strategy from external system");
        this.strategieExterne = strategieExterne;
    }

    @Override
    public TypeDecision determinerDecision(List<TourEntity> tours, JoueurEntity joueur) {
        // Appel de la méthode externe pour obtenir la décision
        String decision = strategieExterne.prochainCoup();

        // Conversion du format externe vers le format interne
        return convertToInternalFormat(decision);
    }

    private TypeDecision convertToInternalFormat(String decision) {
        // Conversion d'une décision de type "c" ou "t" en TypeDecision
        return "c".equals(decision) ? TypeDecision.COOPERER : TypeDecision.TRAHIR;
    }
}

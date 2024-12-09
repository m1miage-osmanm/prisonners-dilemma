package fr.uga.l3miage.pc.prisonersdilemma.adapter;

import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.models.TourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.TypeDecision;

import java.util.List;

public class StrategieAdapter extends Strategie {
    private final fr.uga.l3miage.pc.stratégies.Strategie strategieExterne;

    public StrategieAdapter(fr.uga.l3miage.pc.stratégies.Strategie strategieExterne) {
        super("Adapted " + strategieExterne.getClass().getSimpleName(),
                "Adapted strategy from external system");
        this.strategieExterne = strategieExterne;
    }

    @Override
    public TypeDecision determinerDecision(List<TourEntity> tours, JoueurEntity joueur) {
        String decision = strategieExterne.prochainCoup();

        return convertToInternalFormat(decision);
    }

    private TypeDecision convertToInternalFormat(String decision) {
        return "c".equals(decision) ? TypeDecision.COOPERER : TypeDecision.TRAHIR;
    }
}

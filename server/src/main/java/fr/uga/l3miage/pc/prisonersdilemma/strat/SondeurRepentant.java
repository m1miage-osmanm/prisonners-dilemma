package fr.uga.l3miage.pc.prisonersdilemma.strat;


import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.models.TourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.TypeDecision;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
@Component
public class SondeurRepentant extends Strategie {

    private final RandomAdapter random ;
    private boolean trahisonTest = false;

    public SondeurRepentant() {
        super("SondeurRepentant", "Jouer comme le dernier coup de l'adversaire, mais parfois trahir au lieu de coopérer. \n" +
                "Si l'adversaire trahit en réponse au test, se montrer repentant en coopérant immédiatement.");
    random = new RandomAdapter();
    }
    public SondeurRepentant(RandomAdapter random) {
        super("SondeurRepentant", "Jouer comme le dernier coup de l'adversaire, mais parfois trahir au lieu de coopérer. \n" +
                "Si l'adversaire trahit en réponse au test, se montrer repentant en coopérant immédiatement.");
        this.random = random;
    }
    @Override
    public TypeDecision determinerDecision(List<TourEntity> tours, JoueurEntity joueur) {
        if (tours.isEmpty()) {
            return TypeDecision.COOPERER;
        }

        TourEntity dernierTour = tours.get(tours.size() - 1);
        TypeDecision decisionAdversaireDernierTour = joueur.equals(dernierTour.getPartie().getJoueur1())
                ? dernierTour.getDecisionJoueur2()
                : dernierTour.getDecisionJoueur1();

        if (trahisonTest && decisionAdversaireDernierTour.equals(TypeDecision.TRAHIR)) {
            trahisonTest = false;
            return TypeDecision.COOPERER;
        }

        if (!trahisonTest && random.nextInt(5) == 0) {
            trahisonTest = true;
            return TypeDecision.TRAHIR;
        }

        trahisonTest = false;
        return decisionAdversaireDernierTour;
    }
}

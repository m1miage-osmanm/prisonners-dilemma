package fr.uga.l3miage.pc.prisonersdilemma.domain.strat;


import fr.uga.l3miage.pc.prisonersdilemma.domain.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.TourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.TypeDecision;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VraiPacificateur extends Strategie {

    private final RandomAdapter random ;

    public VraiPacificateur() {
        super("VraiPacificateur",
                "Coopérer si l'adversaire ne trahit pas deux fois de suite. Trahir alors immédiatement mais essayer parfois de faire la paix en coopérant au lieu de trahir.");
        random = new RandomAdapter();
    }
    public VraiPacificateur(RandomAdapter randomAdapter) {
        super("VraiPacificateur",
                "Coopérer si l'adversaire ne trahit pas deux fois de suite. Trahir alors immédiatement mais essayer parfois de faire la paix en coopérant au lieu de trahir.");
        this.random = randomAdapter;
    }

    @Override
    public TypeDecision determinerDecision(List<TourEntity> tours,
                                           JoueurEntity joueur) {

        if (tours.size() < 2) {
            return TypeDecision.COOPERER;
        }

        TourEntity dernierTour = tours.get(tours.size() - 1);
        TourEntity avantDernierTour = tours.get(tours.size() - 2);

        TypeDecision decisionAdversaireDernierTour;
        TypeDecision decisionAdversaireAvantDernierTour;

        if (joueur.equals(dernierTour.getPartie().getJoueur1())) {
            decisionAdversaireDernierTour = dernierTour.getDecisionJoueur2();
            decisionAdversaireAvantDernierTour = avantDernierTour.getDecisionJoueur2();
        } else {
            decisionAdversaireDernierTour = dernierTour.getDecisionJoueur1();
            decisionAdversaireAvantDernierTour = avantDernierTour.getDecisionJoueur1();
        }

        if (decisionAdversaireDernierTour.equals(TypeDecision.TRAHIR) && decisionAdversaireAvantDernierTour.equals(TypeDecision.TRAHIR)) {
            // Trahir la plupart du temps et coopérer parfois (proba de 20% ici)
            return random.nextInt(10) < 2 ? TypeDecision.COOPERER : TypeDecision.TRAHIR;
        } else {
            return TypeDecision.COOPERER;
        }
    }
}

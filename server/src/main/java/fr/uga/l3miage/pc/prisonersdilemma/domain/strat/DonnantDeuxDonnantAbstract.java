package fr.uga.l3miage.pc.prisonersdilemma.domain.strat;

import fr.uga.l3miage.pc.prisonersdilemma.domain.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.TourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.TypeDecision;

import java.util.List;

public abstract class DonnantDeuxDonnantAbstract extends Strategie {

    public DonnantDeuxDonnantAbstract(String nom, String description) {
        super(nom, description);
    }

    @Override
    public TypeDecision determinerDecision(List<TourEntity> tours, JoueurEntity joueur) {
        if (tours.size() < 2) {
            return TypeDecision.COOPERER;
        }

        TourEntity dernierTour = tours.get(tours.size() - 1);
        TourEntity avantDernierTour = tours.get(tours.size() - 2);

        TypeDecision decisionAdversaireDernierTour = getDecisionAdversaire(dernierTour, joueur);
        TypeDecision decisionAdversaireAvantDernierTour = getDecisionAdversaire(avantDernierTour, joueur);

        if (decisionAdversaireDernierTour.equals(decisionAdversaireAvantDernierTour)) {
            return decisionAvecRéciprocité(decisionAdversaireDernierTour);
        } else {
            return TypeDecision.COOPERER; // Coopération par défaut
        }
    }


    protected TypeDecision getDecisionAdversaire(TourEntity tour, JoueurEntity joueur) {
        if (joueur.equals(tour.getPartie().getJoueur1())) {
            return tour.getDecisionJoueur2();
        } else {
            return tour.getDecisionJoueur1();
        }
    }


    protected TypeDecision decisionAvecRéciprocité(TypeDecision decisionAdversaire) {
        return decisionAdversaire; // Par défaut, simplement réciproquer
    }
}

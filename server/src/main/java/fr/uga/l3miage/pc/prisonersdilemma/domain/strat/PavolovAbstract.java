package fr.uga.l3miage.pc.prisonersdilemma.domain.strat;

import fr.uga.l3miage.pc.prisonersdilemma.domain.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.TourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.TypeDecision;

import java.util.List;

public abstract class PavolovAbstract extends Strategie {

    protected PavolovAbstract(String nom, String description) {
        super(nom, description);
    }

    @Override
    public TypeDecision determinerDecision(List<TourEntity> tours, JoueurEntity joueur) {
        if (tours.isEmpty()) {
            return TypeDecision.COOPERER;
        }

        // Obtenir les informations du dernier tour
        TourEntity dernierTour = tours.get(tours.size() - 1);
        DecisionEtScore decisionEtScore = getDecisionEtScore(dernierTour, joueur);

        // Vérifier si le score précédent est 5 ou 3
        if (decisionEtScore.scorePrecedent == 5 || decisionEtScore.scorePrecedent == 3) {
            return decisionAvecScorePositif(decisionEtScore.decisionPrecedente);
        } else {
            return decisionAvecScoreNegatif();
        }
    }


    private DecisionEtScore getDecisionEtScore(TourEntity dernierTour, JoueurEntity joueur) {
        if (joueur.equals(dernierTour.getPartie().getJoueur1())) {
            return new DecisionEtScore(dernierTour.getDecisionJoueur1(), dernierTour.getScoreJoueur1());
        } else {
            return new DecisionEtScore(dernierTour.getDecisionJoueur2(), dernierTour.getScoreJoueur2());
        }
    }


    protected abstract TypeDecision decisionAvecScorePositif(TypeDecision decisionPrecedente);


    protected abstract TypeDecision decisionAvecScoreNegatif();

    private static class DecisionEtScore {
        private final TypeDecision decisionPrecedente;
        private final int scorePrecedent;

        public DecisionEtScore(TypeDecision decisionPrecedente, int scorePrecedent) {
            this.decisionPrecedente = decisionPrecedente;
            this.scorePrecedent = scorePrecedent;
        }
    }
}

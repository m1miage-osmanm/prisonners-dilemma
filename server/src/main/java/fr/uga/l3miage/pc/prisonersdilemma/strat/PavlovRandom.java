package fr.uga.l3miage.pc.prisonersdilemma.strat;


import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.models.TourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.TypeDecision;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
@Component
public class PavlovRandom extends Strategie {

    private final Random random = new Random();

    public PavlovRandom() {
        super("PavlovRandom", "Si 5 ou 3 points ont été obtenus au tour précédent, répéter le dernier choix, mais faire parfois des choix aléatoires.");
    }

    @Override
    public TypeDecision determinerDecision(List<TourEntity> tours, JoueurEntity joueur) {
        if (tours.isEmpty()) {
            return TypeDecision.COOPERER;
        }


        TourEntity dernierTour = tours.get(tours.size() - 1);
        TypeDecision decisionPrecedente;
        int scorePrecedent;


        if (joueur.equals(dernierTour.getPartie().getJoueur1())) {
            decisionPrecedente = dernierTour.getDecisionJoueur1();
            scorePrecedent = dernierTour.getScoreJoueur1();
        } else {
            decisionPrecedente = dernierTour.getDecisionJoueur2();
            scorePrecedent = dernierTour.getScoreJoueur2();
        }

        if (scorePrecedent == 5 || scorePrecedent == 3) {

            if (random.nextInt(5) == 0) {
                return random.nextBoolean() ? TypeDecision.COOPERER : TypeDecision.TRAHIR;
            }
            return decisionPrecedente;
        } else {
            return TypeDecision.COOPERER;
        }
    }
}

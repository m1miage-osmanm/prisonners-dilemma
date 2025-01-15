package fr.uga.l3miage.pc.prisonersdilemma.domain.strat;


import fr.uga.l3miage.pc.prisonersdilemma.domain.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.TourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.TypeDecision;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class DonnantDonnant extends Strategie {



        public DonnantDonnant() {
            super("Donnant-Donnant", "jouer comme le dernier coup de l'adversaire.");
        }

        @Override
        public TypeDecision determinerDecision(List<TourEntity> tours,
                                               JoueurEntity joueur) {


            if (tours.isEmpty()) {
                return TypeDecision.COOPERER;   // Premier tour : coopérer
            }

            TourEntity dernierTour = tours.get(tours.size() - 1);

            if (joueur.equals(dernierTour.getPartie().getJoueur1())) {
                return dernierTour.getDecisionJoueur2(); // Adversaire est joueur2
            } else {
                return dernierTour.getDecisionJoueur1(); // Adversaire est joueur1
            }
        }
}

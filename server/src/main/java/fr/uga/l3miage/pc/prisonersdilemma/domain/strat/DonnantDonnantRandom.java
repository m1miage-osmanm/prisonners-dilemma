package fr.uga.l3miage.pc.prisonersdilemma.domain.strat;

import fr.uga.l3miage.pc.prisonersdilemma.domain.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.TourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.TypeDecision;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DonnantDonnantRandom extends Strategie {

    private final RandomAdapter randomGenerator;

    public DonnantDonnantRandom(){
        super("DonnantDonnantRandom", "Jouer comme le dernier coup de l'adversaire, mais jouer parfois un coup au hasard");
        this.randomGenerator = new RandomAdapter();
    }
    public DonnantDonnantRandom(RandomAdapter randomGenerator) {
        super("DonnantDonnantRandom", "Jouer comme le dernier coup de l'adversaire, mais jouer parfois un coup au hasard");
        this.randomGenerator = randomGenerator;
    }

    @Override
    public TypeDecision determinerDecision(List<TourEntity> tours, JoueurEntity joueur) {
        if (tours.isEmpty()) {
            return TypeDecision.COOPERER;   // Premier tour : coopérer
        }
        TourEntity dernierTour = tours.get(tours.size() - 1);

        // Coup aléatoire
        if (randomGenerator.nextInt(5) == 0) {
            return randomGenerator.nextInt(2) == 0 ? TypeDecision.COOPERER : TypeDecision.TRAHIR;
        }

        // Jouer comme l'adversaire au tour précédent
        if (joueur.equals(dernierTour.getPartie().getJoueur1())) {
            return dernierTour.getDecisionJoueur2();
        } else {
            return dernierTour.getDecisionJoueur1();
        }
    }
}

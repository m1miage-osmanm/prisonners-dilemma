package fr.uga.l3miage.pc.prisonersdilemma.strat;

import fr.uga.l3miage.pc.prisonersdilemma.models.TypeDecision;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class PavlovRandom extends PavolovAbstract {

    private final Random random = new Random();

    public PavlovRandom() {
        super("PavlovRandom", "Si 5 ou 3 points ont été obtenus au tour précédent, répéter le dernier choix, mais faire parfois des choix aléatoires.");
    }

    @Override
    protected TypeDecision decisionAvecScorePositif(TypeDecision decisionPrecedente) {
        if (random.nextInt(5) == 0) {
            return random.nextBoolean() ? TypeDecision.COOPERER : TypeDecision.TRAHIR;
        }
        return decisionPrecedente;
    }

    @Override
    protected TypeDecision decisionAvecScoreNegatif() {
        return TypeDecision.COOPERER;
    }
}

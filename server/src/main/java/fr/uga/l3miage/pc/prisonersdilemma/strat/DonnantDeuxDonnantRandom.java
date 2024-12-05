package fr.uga.l3miage.pc.prisonersdilemma.strat;

import fr.uga.l3miage.pc.prisonersdilemma.models.TypeDecision;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class DonnantDeuxDonnantRandom extends DonnantDeuxDonnantAbstract {

    private final Random random = new Random();

    public DonnantDeuxDonnantRandom() {
        super(
                "DonnantDeuxDonnantRandom",
                "Comme donnant-donnant sauf que l'adversaire doit faire le même choix deux fois de suite avant la réciprocité. Joue parfois un coup au hasard."
        );
    }

    @Override
    protected TypeDecision decisionAvecRéciprocité(TypeDecision decisionAdversaire) {
        // Avec une probabilité de 1/5, jouer aléatoirement
        if (random.nextInt(5) == 0) {
            return random.nextBoolean() ? TypeDecision.COOPERER : TypeDecision.TRAHIR;
        }
        return decisionAdversaire; // Sinon, réciprocité
    }
}

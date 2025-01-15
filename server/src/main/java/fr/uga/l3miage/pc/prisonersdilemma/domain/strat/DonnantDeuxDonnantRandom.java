package fr.uga.l3miage.pc.prisonersdilemma.domain.strat;

import fr.uga.l3miage.pc.prisonersdilemma.domain.models.TypeDecision;
import org.springframework.stereotype.Component;

@Component
public class DonnantDeuxDonnantRandom extends DonnantDeuxDonnantAbstract {

    private final RandomAdapter randomGenerator;

    public DonnantDeuxDonnantRandom(RandomAdapter randomGenerator) {
        super(
                "DonnantDeuxDonnantRandom",
                "Comme donnant-donnant sauf que l'adversaire doit faire le même choix deux fois de suite avant la réciprocité. Joue parfois un coup au hasard."
        );
        this.randomGenerator = randomGenerator;
    }
    public DonnantDeuxDonnantRandom(){
        super(
                "DonnantDeuxDonnantRandom",
                "Comme donnant-donnant sauf que l'adversaire doit faire le même choix deux fois de suite avant la réciprocité. Joue parfois un coup au hasard."
        );
        this.randomGenerator = new RandomAdapter();
    }

    @Override
    public TypeDecision decisionAvecRéciprocité(TypeDecision decisionAdversaire) {
        // Avec une probabilité de 1/5, jouer aléatoirement
        if (randomGenerator.nextInt(5) == 0) {
            return randomGenerator.nextBoolean() ? TypeDecision.COOPERER : TypeDecision.TRAHIR;
        }
        return decisionAdversaire; // Sinon, réciprocité
    }
}

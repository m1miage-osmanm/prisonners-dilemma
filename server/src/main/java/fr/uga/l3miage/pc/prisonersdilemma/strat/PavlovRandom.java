package fr.uga.l3miage.pc.prisonersdilemma.strat;

import fr.uga.l3miage.pc.prisonersdilemma.models.TypeDecision;
import org.springframework.stereotype.Component;



@Component
public class PavlovRandom extends PavolovAbstract {

    private final RandomAdapter randomAdapter;
    public PavlovRandom(){
        super("PavlovRandom", "Si 5 ou 3 points ont été obtenus au tour précédent, répéter le dernier choix, mais faire parfois des choix aléatoires.");
        this.randomAdapter = new RandomAdapter();
    }
    public PavlovRandom(RandomAdapter randomAdapter) {
        super("PavlovRandom", "Si 5 ou 3 points ont été obtenus au tour précédent, répéter le dernier choix, mais faire parfois des choix aléatoires.");
        this.randomAdapter = randomAdapter;
    }

    @Override
    public TypeDecision decisionAvecScorePositif(TypeDecision decisionPrecedente) {
        if (randomAdapter.nextInt(5) == 0) {
            return randomAdapter.nextBoolean() ? TypeDecision.COOPERER : TypeDecision.TRAHIR;
        }
        return decisionPrecedente;
    }

    @Override
    public TypeDecision decisionAvecScoreNegatif() {
        return TypeDecision.COOPERER;
    }
}

package fr.uga.l3miage.pc.prisonersdilemma.strat;

import fr.uga.l3miage.pc.prisonersdilemma.models.TypeDecision;
import org.springframework.stereotype.Component;

@Component
public class Pavlov extends PavolovAbstract {

    private final Aleatoire aleatoire;

    public Pavlov() {
        super("Pavlov", "Si 5 ou 3 points ont été obtenus au tour précédent, répéter le dernier choix, sinon choisir aléatoirement.");
        this.aleatoire = new Aleatoire();
    }

    @Override
    protected TypeDecision decisionAvecScorePositif(TypeDecision decisionPrecedente) {
        return decisionPrecedente;
    }

    @Override
    protected TypeDecision decisionAvecScoreNegatif() {
        return aleatoire.determinerDecision(null, null); // Appel aléatoire
    }
}

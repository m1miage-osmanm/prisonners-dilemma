package fr.uga.l3miage.pc.prisonersdilemma;

import java.util.List;

public class Trahison extends Strategie{
    public Trahison() {
        super("trahison", "-trahir l'adversaire toujours");
    }
    @Override
    public String determinerDecision(List<Tours> tours,
                                     Joueur joueur1,
                                     Joueur joueur2) {
        return "t";
    }
}

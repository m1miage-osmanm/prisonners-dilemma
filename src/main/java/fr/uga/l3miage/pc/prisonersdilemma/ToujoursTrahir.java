package fr.uga.l3miage.pc.prisonersdilemma;

import java.util.List;

public class ToujoursTrahir extends Strategie{
    public ToujoursTrahir() {
        super("ToujoursTrahir", "-trahir l'adversaire toujours");
    }
    @Override
    public String determinerDecision(List<Tours> tours,
                                     Joueur joueur1,
                                     Joueur joueur2) {
        return "t";
    }
}

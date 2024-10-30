package fr.uga.l3miage.pc.prisonersdilemma;

import java.util.List;

public class ToujoursCooperer extends Strategie{
    public ToujoursCooperer() {
        super("ToujoursTrahir", "-trahir l'adversaire toujours");
    }
    @Override
    public String determinerDecision(List<Tour> tours,
                                     Joueur joueur) {
        return "c";
    }
}

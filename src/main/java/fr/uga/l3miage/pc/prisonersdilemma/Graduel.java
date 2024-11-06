package fr.uga.l3miage.pc.prisonersdilemma;

import java.util.List;

public class Graduel extends Strategie{

    public Graduel() {
        super ("Graduel", " Coopérer jusqu'à ce que l'adversaire trahisse; dans ce cas, trahir aurtant de fois que l'adversaire \n" +
                "a déjà trahi dans la partie, puis continuer par deux coopérations.") ;
    }

    @Override
    public String determinerDecision(List<Tour> tours, Joueur joueur) {
        return "";
    }
}

package fr.uga.l3miage.pc.prisonersdilemma;

import java.util.List;
import java.util.Random;

public class Aleatoire extends Strategie{
    public Aleatoire() {
        super("Aléatoire", "Trahir ou coopérer avec une probabilité de 50%");
    }
    Random random = new Random();
    @Override
    public String determinerDecision(List<Tours> tours,
                                     Joueur joueur,
                                     Joueur adversaire) {
        return random.nextDouble() < 0.5 ? "c" : "t";
    }




}

package fr.uga.l3miage.pc.prisonersdilemma;

import java.util.List;
import java.util.Random;

public class Aleatoire extends Strategie{

    private final Random random = new Random();


    public Aleatoire() {

        super("Aléatoire", "Trahir ou coopérer avec une probabilité de 50%");
    }

    @Override
    public String determinerDecision(List<Tour> tours,
                                     Joueur joueur) {
        return random.nextDouble() < 0.5 ? "c" : "t";
    }




}

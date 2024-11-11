package fr.uga.l3miage.pc.prisonersdilemma.strat;



import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.models.TourEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
@Component
public class Aleatoire extends Strategie {

    private final Random random = new Random();


    public Aleatoire() {

        super("Aléatoire", "Trahir ou coopérer avec une probabilité de 50%");
    }

    @Override
    public String determinerDecision(List<TourEntity> tours,
                                     JoueurEntity joueur) {
        return random.nextDouble() < 0.5 ? "c" : "t";
    }



}

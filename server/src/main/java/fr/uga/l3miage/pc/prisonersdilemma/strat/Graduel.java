package fr.uga.l3miage.pc.prisonersdilemma.strat;



import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.models.TourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.TypeDecision;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class Graduel extends Strategie {

    public Graduel() {
        super ("Graduel", " Coopérer jusqu'à ce que l'adversaire trahisse; dans ce cas, trahir aurtant de fois que l'adversaire \n" +
                "a déjà trahi dans la partie, puis continuer par deux coopérations.") ;
    }

    @Override
    public TypeDecision determinerDecision(List<TourEntity> tours, JoueurEntity joueur) {
        return TypeDecision.COOPERER;
    } //a revoir
}

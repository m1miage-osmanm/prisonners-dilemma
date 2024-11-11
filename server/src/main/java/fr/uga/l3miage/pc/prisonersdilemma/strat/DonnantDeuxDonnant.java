package fr.uga.l3miage.pc.prisonersdilemma.strat;


import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.models.TourEntity;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class DonnantDeuxDonnant extends Strategie {

public DonnantDeuxDonnant(){
    super ("DonnantDeuxDonnant","Comme donnant donnant sauf que l'adversaire doit faire le même choix\n" +
        "deux fois de suite avant la réciprocité.");
}

@Override
public String determinerDecision(List<TourEntity> tours, JoueurEntity joueur)
{
    if (tours.size() < 2) {
        return "c";
    }

    TourEntity avantDernierTour = tours.get(tours.size() - 2);
    TourEntity  dernierTour = tours.get(tours.size() - 1);

    String decisionAdversaireDernierTour;
    String decisionAdversaireAvantDernierTour;

    if (joueur.equals(dernierTour.getPartie().getJoueur1())) {
        decisionAdversaireDernierTour = dernierTour.getDecisionJoueur2();
        decisionAdversaireAvantDernierTour = avantDernierTour.getDecisionJoueur2();
    } else {
        decisionAdversaireDernierTour = dernierTour.getDecisionJoueur1();
        decisionAdversaireAvantDernierTour = avantDernierTour.getDecisionJoueur1();
    }

    if (decisionAdversaireDernierTour.equals(decisionAdversaireAvantDernierTour)) {
        return decisionAdversaireDernierTour;
    } else {
        return "c";
    }
}














}

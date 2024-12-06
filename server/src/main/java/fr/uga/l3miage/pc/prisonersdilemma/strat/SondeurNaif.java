package fr.uga.l3miage.pc.prisonersdilemma.strat;


import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.models.TourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.TypeDecision;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class SondeurNaif extends Strategie {

public SondeurNaif() {
    super("SondeurNaif","Jouer comme le dernier coup de l'adversaire mais parfois trahir au lieu de coopérer");
    random = new RandomAdapter();
}

RandomAdapter random ;
public SondeurNaif(RandomAdapter random) {
    super("SondeurNaif","Jouer comme le dernier coup de l'adversaire mais parfois trahir au lieu de coopérer");
    this.random = random;
}

@Override
public TypeDecision determinerDecision(List<TourEntity> tours, JoueurEntity joueur)
{
  if (tours.isEmpty())
{
    return TypeDecision.TRAHIR;
}
TourEntity dernierTour = tours.get(tours.size()-1);
boolean coupAleatoire = random.nextInt(2) == 0;

    TypeDecision decisionAdversaire;
    if (joueur.equals(dernierTour.getPartie().getJoueur1())) {
        decisionAdversaire = dernierTour.getDecisionJoueur2();
    } else {
        decisionAdversaire = dernierTour.getDecisionJoueur1();
    }

    if (TypeDecision.COOPERER.equals(decisionAdversaire) && coupAleatoire) {
        return TypeDecision.TRAHIR;
    }

    return decisionAdversaire;
}



















}

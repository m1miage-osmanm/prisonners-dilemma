package fr.uga.l3miage.pc.prisonersdilemma;

import java.util.List;
import java.util.Random;

public class SondeurNaif extends Strategie{

public SondeurNaif() {
    super("SondeurNaif","Jouer comme le dernier coup de l'adversaire mais parfois trahir au lieu de coopérer"); }

Random random = new Random();


@Override
public String determinerDecision(List<Tour> tours, Joueur joueur)
{
  if (tours.isEmpty())
{
    return "t";
}
Tour dernierTour = tours.get(tours.size()-1);
boolean coupAleatoire = random.nextInt(2) == 0;

    String decisionAdversaire;
    if (joueur.equals(dernierTour.getPartie().getJoueur1())) {
        decisionAdversaire = dernierTour.getDecisionJoueur2();
    } else {
        decisionAdversaire = dernierTour.getDecisionJoueur1();
    }

    if ("c".equals(decisionAdversaire) && coupAleatoire) {
        return "t";
    }

    return decisionAdversaire;
}



















}

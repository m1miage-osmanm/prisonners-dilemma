package fr.uga.l3miage.pc.prisonersdilemma;

import java.util.List;
import java.util.Random;

public class PacificateurNaif extends Strategie{
    public PacificateurNaif() {
    super("PacificateurNaif","Jouer comme le dernier coup de l'adversaire mais parfois coopérer au lieu de trahir"); }

    Random random = new Random();


    @Override
    public String determinerDecision(List<Tours> tours, Joueur joueur, Joueur adversaire)
    {
        if (tours.isEmpty())
        {
            return "c";
        }
        Tours dernierTour = tours.get(tours.size()-1);
        boolean coupAleatoire = random.nextInt(2) == 0;

        String decisionAdversaire;
        if (joueur.equals(dernierTour.getPartie().getJoueur1())) {
            decisionAdversaire = dernierTour.getDecisionJoueur2();
        } else {
            decisionAdversaire = dernierTour.getDecisionJoueur1();
        }

        if ("t".equals(decisionAdversaire) && coupAleatoire) {
            return "c";
        }

        return decisionAdversaire;
}}

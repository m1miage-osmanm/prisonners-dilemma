package fr.uga.l3miage.pc.prisonersdilemma.strat;


import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.models.TourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.TypeDecision;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
@Component
public class PacificateurNaif extends Strategie {
    public PacificateurNaif() {
    super("PacificateurNaif","Jouer comme le dernier coup de l'adversaire mais parfois coopérer au lieu de trahir"); }

    Random random = new Random();


    @Override
    public TypeDecision determinerDecision(List<TourEntity> tours, JoueurEntity joueur)
    {
        if (tours.isEmpty())
        {
            return TypeDecision.COOPERER;
        }
        TourEntity dernierTour = tours.get(tours.size()-1);
        boolean coupAleatoire = random.nextInt(5) == 0;

        TypeDecision decisionAdversaire;
        if (joueur.equals(dernierTour.getPartie().getJoueur1())) {
            decisionAdversaire = dernierTour.getDecisionJoueur2();
        } else {
            decisionAdversaire = dernierTour.getDecisionJoueur1();
        }

        if (TypeDecision.TRAHIR.equals(decisionAdversaire) && coupAleatoire) {
            return TypeDecision.COOPERER;
        }

        return decisionAdversaire;
}}

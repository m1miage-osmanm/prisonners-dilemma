package fr.uga.l3miage.pc.prisonersdilemma;

import java.util.List;

public class Pavlov extends Strategie {

    private final Aleatoire aleatoire;

    public Pavlov() {
        super("Pavlov", "Si 5 ou 3 points ont été obtenus au tour précédent, répéter le dernier choix, sinon choisir aléatoirement.");
        this.aleatoire = new Aleatoire();
    }

    @Override
    public String determinerDecision(List<Tour> tours, Joueur joueur) {
        if (tours.isEmpty()) {
            return "c";
        }

        Tour dernierTour = tours.get(tours.size() - 1);
        String decisionPrecedente;
        int scorePrecedent;

        if (joueur.equals(dernierTour.getPartie().getJoueur1())) {
            decisionPrecedente = dernierTour.getDecisionJoueur1();
            scorePrecedent = dernierTour.getScoreJoueur1();
        } else {
            decisionPrecedente = dernierTour.getDecisionJoueur2();
            scorePrecedent = dernierTour.getScoreJoueur2();
        }

        if (scorePrecedent == 5 || scorePrecedent == 3) {
            return decisionPrecedente;
        } else {
            return aleatoire.determinerDecision(tours, joueur);
        }
    }
}
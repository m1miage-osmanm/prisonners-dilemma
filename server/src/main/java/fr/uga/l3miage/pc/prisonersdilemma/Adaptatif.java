package fr.uga.l3miage.pc.prisonersdilemma;

import java.util.List;

public class Adaptatif extends Strategie {

    private int i = 0;
    private int totalScoreC = 0;
    private int totalScoreT = 0;
    private int countC = 0;
    private int countT = 0;

    public Adaptatif() {
        super("Adaptatif", "Commencer avec c,c,c,c,c,c,t,t,t,t,t. Ensuite, choisir le choix (t ou c) qui a donné le meilleur score moyen.");
    }

    @Override
    public String determinerDecision(List<Tour> tours, Joueur joueur) {
        if (tours.isEmpty()) {
            i++;
            countC++;
            return "c";
        }

        Tour dernierTour = tours.get(tours.size() - 1);

        int dernierScore = joueur.equals(dernierTour.getPartie().getJoueur1())
                ? dernierTour.getScoreJoueur1()
                : dernierTour.getScoreJoueur2();

        if (i < 6) {
            totalScoreC += dernierScore;
            countC++;
            i++;
            return "c";
        }

        if (i >= 6 && i < 10) {
            totalScoreT += dernierScore;
            countT++;
            i++;
            return "t";
        }

        double avgScoreC = countC > 0 ? (double) totalScoreC / countC : 0;
        double avgScoreT = countT > 0 ? (double) totalScoreT / countT : 0;

        return avgScoreC > avgScoreT ? "c" : "t";
    }
}
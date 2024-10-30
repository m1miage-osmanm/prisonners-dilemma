package fr.uga.l3miage.pc.prisonersdilemma;

public class Tour {
    private static final int T = 5; // Gain si trahison et l'autre coopère
    private static final int D = 0; // Perte si coopère et l'autre trahit
    private static final int C = 3; // Gain si les deux coopèrent
    private static final int P = 1; // Gain si les deux trahissent

    private String decisionJoueur1;
    private String decisionJoueur2;
    private Partie partie;
    private int scoreJoueur1;
    private int scoreJoueur2;

    public Tour(Partie partie, String decisionJoueur1, String decisionJoueur2) {
        this.partie = partie;
        this.decisionJoueur1 = decisionJoueur1;
        this.decisionJoueur2 = decisionJoueur2;
        calculerScores();
    }

    private void calculerScores() {
        if ("t".equals(decisionJoueur1) && "c".equals(decisionJoueur2)) {
            scoreJoueur1 = T;
            scoreJoueur2 = D;
        } else if ("c".equals(decisionJoueur1) && "t".equals(decisionJoueur2)) {
            scoreJoueur1 = D;
            scoreJoueur2 = T;
        } else if ("c".equals(decisionJoueur1) && "c".equals(decisionJoueur2)) {
            scoreJoueur1 = C;
            scoreJoueur2 = C;
        } else if ("t".equals(decisionJoueur1) && "t".equals(decisionJoueur2)) {
            scoreJoueur1 = P;
            scoreJoueur2 = P;
        }
    }

    public boolean toursFinis() {
        return decisionJoueur1 != null && decisionJoueur2 != null;
    }

    public Partie getPartie() {
        return partie;
    }

    public String getDecisionJoueur1() {
        return decisionJoueur1;
    }

    public void setDecisionJoueur1(String decisionJoueur1) {
        this.decisionJoueur1 = decisionJoueur1;
        calculerScores();
    }

    public String getDecisionJoueur2() {
        return decisionJoueur2;
    }

    public void setDecisionJoueur2(String decisionJoueur2) {
        this.decisionJoueur2 = decisionJoueur2;
        calculerScores();
    }

    public int getScoreJoueur1() {
        return scoreJoueur1;
    }

    public int getScoreJoueur2() {
        return scoreJoueur2;
    }
}

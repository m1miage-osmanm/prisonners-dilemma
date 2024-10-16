package fr.uga.l3miage.pc.prisonersdilemma;

public class Tours {
    private String decisionJoueur1;
    private String decisionJoueur2;
    private Partie partie;

    public Tours(Partie partie, String decisionJoueur1, String decisionJoueur2){
        this.partie = partie;
        this.decisionJoueur1 = decisionJoueur1;
        this.decisionJoueur2 = decisionJoueur2;
    }
    public boolean toursFinis(){
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
    }

    public String getDecisionJoueur2() {
        return decisionJoueur2;
    }

    public void setDecisionJoueur2(String decisionJoueur2) {
        this.decisionJoueur2 = decisionJoueur2;
    }

}

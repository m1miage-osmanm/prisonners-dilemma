package fr.uga.l3miage.pc.prisonersdilemma;

public class Joueur {
    private final String username;
    private Integer level;

    public Joueur(String username){
        this.username=username;
        this.level=0;
    }

    public String getUsername() {
        return username;
    }

    public Integer getLevel() {
        return level;
    }
}

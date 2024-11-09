package fr.uga.l3miage.pc.prisonersdilemma;

import java.util.List;

public abstract class Strategie {
    private String nom;
    private String description;

    public Strategie(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }
    public abstract String determinerDecision(List<Tour> tours, Joueur joueur);
}

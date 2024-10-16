package fr.uga.l3miage.pc.prisonersdilemma;

import java.util.ArrayList;
import java.util.List;

public class Partie {
    private final Joueur joueur1;
    private final Joueur joueur2;
    private final Integer nombreTours;
    private final Strategie strategieJoueur1;
    private final Strategie strategieJoueur2;
    private List<Tours> tours;

    public Partie(Joueur joueur1, Joueur joueur2, Integer nombreTours, Strategie strategieJoueur1, Strategie strategieJoueur2) {
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        this.nombreTours = nombreTours;
        this.strategieJoueur1 = strategieJoueur1;
        this.strategieJoueur2 = strategieJoueur2;
        this.tours = new ArrayList<>();
    }

    public Strategie getStrategieJoueur1() {
        return strategieJoueur1;
    }

    public Strategie getStrategieJoueur2() {
        return strategieJoueur2;
    }

    public List<Tours> getTours() {
        return tours;
    }

    public void ajouterTour(Tours tour) {
        if (tours.size() < nombreTours) {
            tours.add(tour);
        } else {
            throw new IllegalStateException("Le nombre maximum de tours est atteint.");
        }
    }
    public Joueur getJoueur1() {
        return joueur1;
    }

    public Joueur getJoueur2() {
        return joueur2;
    }

    public Integer getNombreTours() {
        return nombreTours;
    }

}

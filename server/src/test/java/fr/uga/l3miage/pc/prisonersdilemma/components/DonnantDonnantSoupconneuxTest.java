package fr.uga.l3miage.pc.prisonersdilemma.components;


import fr.uga.l3miage.pc.prisonersdilemma.models.*;
import fr.uga.l3miage.pc.prisonersdilemma.strat.DonnantDonnantSoupconneux;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DonnantDonnantSoupconneuxTest {

    @Test
    void testPremiereDecision() {
        // Arrange
        DonnantDonnantSoupconneux strategie = new DonnantDonnantSoupconneux();
        JoueurEntity joueur = new JoueurEntity();
        List<TourEntity> tours = new ArrayList<>();

        // Act
        TypeDecision decision = strategie.determinerDecision(tours, joueur);

        // Assert
        assertEquals(TypeDecision.TRAHIR, decision, "La première décision devrait être TRAHIR.");
    }

    @Test
    void testDecisionApresUnTourJoueur1() {
        // Arrange
        DonnantDonnantSoupconneux strategie = new DonnantDonnantSoupconneux();
        JoueurEntity joueur1 = new JoueurEntity();
        JoueurEntity joueur2 = new JoueurEntity();

        PartieEntity partie = new PartieEntity();
        partie.setJoueur1(joueur1);
        partie.setJoueur2(joueur2);

        TourEntity dernierTour = new TourEntity();
        dernierTour.setPartie(partie);
        dernierTour.setDecisionJoueur1(TypeDecision.COOPERER);
        dernierTour.setDecisionJoueur2(TypeDecision.TRAHIR);

        List<TourEntity> tours = List.of(dernierTour);

        // Act
        TypeDecision decision = strategie.determinerDecision(tours, joueur1);

        // Assert
        assertEquals(TypeDecision.TRAHIR, decision, "La décision devrait suivre la décision de l'autre joueur.");
    }

    @Test
    void testDecisionApresUnTourJoueur2() {
        // Arrange
        DonnantDonnantSoupconneux strategie = new DonnantDonnantSoupconneux();
        JoueurEntity joueur1 = new JoueurEntity();
        JoueurEntity joueur2 = new JoueurEntity();

        PartieEntity partie = new PartieEntity();
        partie.setJoueur1(joueur1);
        partie.setJoueur2(joueur2);

        TourEntity dernierTour = new TourEntity();
        dernierTour.setPartie(partie);
        dernierTour.setDecisionJoueur1(TypeDecision.COOPERER);
        dernierTour.setDecisionJoueur2(TypeDecision.TRAHIR);

        List<TourEntity> tours = List.of(dernierTour);

        // Act
        TypeDecision decision = strategie.determinerDecision(tours, joueur2);

        // Assert
        assertEquals(TypeDecision.COOPERER, decision, "La décision devrait suivre la décision de l'autre joueur.");
    }

    @Test
    void testDecisionAvecPlusieursTours() {
        // Arrange
        DonnantDonnantSoupconneux strategie = new DonnantDonnantSoupconneux();
        JoueurEntity joueur1 = new JoueurEntity();
        JoueurEntity joueur2 = new JoueurEntity();

        PartieEntity partie = new PartieEntity();
        partie.setJoueur1(joueur1);
        partie.setJoueur2(joueur2);

        TourEntity tour1 = new TourEntity();
        tour1.setPartie(partie);
        tour1.setDecisionJoueur1(TypeDecision.COOPERER);
        tour1.setDecisionJoueur2(TypeDecision.COOPERER);

        TourEntity tour2 = new TourEntity();
        tour2.setPartie(partie);
        tour2.setDecisionJoueur1(TypeDecision.TRAHIR);
        tour2.setDecisionJoueur2(TypeDecision.COOPERER);

        List<TourEntity> tours = List.of(tour1, tour2);

        // Act
        TypeDecision decision = strategie.determinerDecision(tours, joueur1);

        // Assert
        assertEquals(TypeDecision.COOPERER, decision, "La décision devrait suivre la dernière décision de l'autre joueur.");
    }

}

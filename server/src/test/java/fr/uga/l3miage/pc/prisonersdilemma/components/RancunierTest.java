package fr.uga.l3miage.pc.prisonersdilemma.components;


import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.PartieEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.TourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.TypeDecision;
import fr.uga.l3miage.pc.prisonersdilemma.strat.Rancunier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RancunierTest {

    private Rancunier rancunier;
    private JoueurEntity joueur1;
    private JoueurEntity joueur2;

    @BeforeEach
    void setUp() {
        rancunier = new Rancunier();

        joueur1 = new JoueurEntity();
        joueur1.setId(1L);

        joueur2 = new JoueurEntity();
        joueur2.setId(2L);
    }

    @Test
    void testCooperationInitiale() {
        List<TourEntity> tours = new ArrayList<>();

        TypeDecision decision = rancunier.determinerDecision(tours, joueur1);
        assertEquals(TypeDecision.COOPERER, decision);
    }

    @Test
    void testContinueCooperationSiAdversaireCoopere() {
        List<TourEntity> tours = new ArrayList<>();
        tours.add(createTour(joueur1, joueur2, TypeDecision.COOPERER, TypeDecision.COOPERER));

        TypeDecision decision = rancunier.determinerDecision(tours, joueur1);
        assertEquals(TypeDecision.COOPERER, decision);
    }

    @Test
    void testTrahisonApresAdversaireTrahison() {
        List<TourEntity> tours = new ArrayList<>();
        tours.add(createTour(joueur1, joueur2, TypeDecision.COOPERER, TypeDecision.TRAHIR));

        TypeDecision decision = rancunier.determinerDecision(tours, joueur1);
        assertEquals(TypeDecision.TRAHIR, decision);
    }

    @Test
    void testToujoursTrahirApresPremiereTrahison() {
        List<TourEntity> tours = new ArrayList<>();
        tours.add(createTour(joueur1, joueur2, TypeDecision.COOPERER, TypeDecision.TRAHIR));
        tours.add(createTour(joueur1, joueur2, TypeDecision.COOPERER, TypeDecision.COOPERER));

        TypeDecision decision = rancunier.determinerDecision(tours, joueur1);
        assertEquals(TypeDecision.COOPERER, decision);
    }

    // Méthode utilitaire pour créer un tour simulé
    private TourEntity createTour(JoueurEntity joueur1, JoueurEntity joueur2,
                                  TypeDecision decisionJoueur1, TypeDecision decisionJoueur2) {
        PartieEntity partie = new PartieEntity();
        partie.setJoueur1(joueur1);
        partie.setJoueur2(joueur2);

        TourEntity tour = new TourEntity();
        tour.setPartie(partie);
        tour.setDecisionJoueur1(decisionJoueur1);
        tour.setDecisionJoueur2(decisionJoueur2);

        return tour;
    }
}

package fr.uga.l3miage.pc.prisonersdilemma.components;


import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.PartieEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.TourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.TypeDecision;
import fr.uga.l3miage.pc.prisonersdilemma.strat.DonnantDeuxDonnantAbstract;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DonnantDeuxDonnantAbstractTest {

    private DonnantDeuxDonnantAbstract strategy;
    private JoueurEntity joueur1;
    private JoueurEntity joueur2;

    @BeforeEach
    void setUp() {
        // Implémentation concrète minimale pour tester DonnantDeuxDonnantAbstract
        strategy = new DonnantDeuxDonnantAbstract("TestDonnantDeuxDonnant", "Test Description") {
            @Override
            protected TypeDecision decisionAvecRéciprocité(TypeDecision decisionAdversaire) {
                return decisionAdversaire; // Simplement imiter l'adversaire
            }
        };

        joueur1 = new JoueurEntity();
        joueur1.setId(1L);

        joueur2 = new JoueurEntity();
        joueur2.setId(2L);
    }

    @Test
    void testPremierTour() {
        List<TourEntity> tours = new ArrayList<>();
        TypeDecision decision = strategy.determinerDecision(tours, joueur1);
        assertEquals(TypeDecision.COOPERER, decision); // Toujours coopérer au premier tour
    }

    @Test
    void testDeuxiemeTour() {
        List<TourEntity> tours = new ArrayList<>();
        tours.add(createTour(joueur1, joueur2, TypeDecision.COOPERER, TypeDecision.COOPERER, 3, 3));

        TypeDecision decision = strategy.determinerDecision(tours, joueur1);
        assertEquals(TypeDecision.COOPERER, decision); // Toujours coopérer au deuxième tour
    }

    @Test
    void testDeuxDecisionsIdentiques() {
        List<TourEntity> tours = new ArrayList<>();
        tours.add(createTour(joueur1, joueur2, TypeDecision.COOPERER, TypeDecision.TRAHIR, 0, 5));
        tours.add(createTour(joueur1, joueur2, TypeDecision.TRAHIR, TypeDecision.TRAHIR, 0, 5));

        TypeDecision decision = strategy.determinerDecision(tours, joueur1);
        assertEquals(TypeDecision.TRAHIR, decision); // Adversaire a trahi deux fois, on réciproque
    }

    @Test
    void testDeuxDecisionsDifferentes() {
        List<TourEntity> tours = new ArrayList<>();
        tours.add(createTour(joueur1, joueur2, TypeDecision.COOPERER, TypeDecision.COOPERER, 3, 3));
        tours.add(createTour(joueur1, joueur2, TypeDecision.TRAHIR, TypeDecision.COOPERER, 0, 5));

        TypeDecision decision = strategy.determinerDecision(tours, joueur1);
        assertEquals(TypeDecision.COOPERER, decision); // Décisions adverses différentes, par défaut coopérer
    }

    @Test
    void testImitationAvecRéciprocité() {
        List<TourEntity> tours = new ArrayList<>();
        tours.add(createTour(joueur1, joueur2, TypeDecision.COOPERER, TypeDecision.TRAHIR, 0, 5));
        tours.add(createTour(joueur1, joueur2, TypeDecision.TRAHIR, TypeDecision.TRAHIR, 0, 5));

        TypeDecision decision = strategy.determinerDecision(tours, joueur2);
        assertEquals(TypeDecision.COOPERER, decision); // Adversaire a trahi deux fois, on réciproque
    }

    // Méthode utilitaire pour créer un tour simulé
    private TourEntity createTour(JoueurEntity joueur1, JoueurEntity joueur2,
                                  TypeDecision decisionJoueur1, TypeDecision decisionJoueur2,
                                  int scoreJoueur1, int scoreJoueur2) {
        PartieEntity partie = new PartieEntity();
        partie.setJoueur1(joueur1);
        partie.setJoueur2(joueur2);

        TourEntity tour = new TourEntity();
        tour.setPartie(partie);
        tour.setDecisionJoueur1(decisionJoueur1);
        tour.setDecisionJoueur2(decisionJoueur2);
        tour.setScoreJoueur1(scoreJoueur1);
        tour.setScoreJoueur2(scoreJoueur2);

        return tour;
    }
}

package fr.uga.l3miage.pc.prisonersdilemma.components;


import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.PartieEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.TourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.TypeDecision;
import fr.uga.l3miage.pc.prisonersdilemma.strat.DonnantDonnantRandom;
import fr.uga.l3miage.pc.prisonersdilemma.strat.RandomAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class DonnantDonnantRandomTest {

    private DonnantDonnantRandom donnantDonnantRandom;
    private JoueurEntity joueur1;
    private JoueurEntity joueur2;
    private RandomAdapter randomMock;

    @BeforeEach
    void setUp() {
        randomMock = Mockito.mock(RandomAdapter.class); // Mock pour contrôler le comportement aléatoire
        donnantDonnantRandom = new DonnantDonnantRandom(randomMock);

        joueur1 = new JoueurEntity();
        joueur1.setId(1L);

        joueur2 = new JoueurEntity();
        joueur2.setId(2L);
    }

    @Test
    void testPremierTour() {
        List<TourEntity> tours = new ArrayList<>();
        TypeDecision decision = donnantDonnantRandom.determinerDecision(tours, joueur1);
        assertEquals(TypeDecision.COOPERER, decision);
    }

    @Test
    void testImiterDecisionAdversaire() {
        List<TourEntity> tours = new ArrayList<>();
        tours.add(createTour(joueur1, joueur2, TypeDecision.COOPERER, TypeDecision.TRAHIR));

        when(randomMock.nextInt(5)).thenReturn(1); // Pas de coup aléatoire
        TypeDecision decision = donnantDonnantRandom.determinerDecision(tours, joueur1);
        assertEquals(TypeDecision.TRAHIR, decision);
    }

    @Test
    void testCoupAleatoireCooperer() {
        List<TourEntity> tours = new ArrayList<>();
        tours.add(createTour(joueur1, joueur2, TypeDecision.TRAHIR, TypeDecision.COOPERER));

        when(randomMock.nextInt(5)).thenReturn(0); // Coup aléatoire déclenché
        when(randomMock.nextInt(2)).thenReturn(0); // Décision aléatoire : COOPERER
        TypeDecision decision = donnantDonnantRandom.determinerDecision(tours, joueur1);
        assertEquals(TypeDecision.COOPERER, decision);
    }

    @Test
    void testCoupAleatoireTrahir() {
        List<TourEntity> tours = new ArrayList<>();
        tours.add(createTour(joueur1, joueur2, TypeDecision.COOPERER, TypeDecision.TRAHIR));

        when(randomMock.nextInt(5)).thenReturn(0); // Coup aléatoire déclenché
        when(randomMock.nextInt(2)).thenReturn(1); // Décision aléatoire : TRAHIR
        TypeDecision decision = donnantDonnantRandom.determinerDecision(tours, joueur1);
        assertEquals(TypeDecision.TRAHIR, decision);
    }

    @Test
    void testImiterDecisionAdversaireApresCoupAleatoire() {
        List<TourEntity> tours = new ArrayList<>();
        tours.add(createTour(joueur1, joueur2, TypeDecision.COOPERER, TypeDecision.COOPERER));

        when(randomMock.nextInt(5)).thenReturn(1); // Pas de coup aléatoire
        TypeDecision decision = donnantDonnantRandom.determinerDecision(tours, joueur1);
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

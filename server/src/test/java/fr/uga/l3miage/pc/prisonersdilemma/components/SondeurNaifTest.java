package fr.uga.l3miage.pc.prisonersdilemma.components;


import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.PartieEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.TourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.TypeDecision;
import fr.uga.l3miage.pc.prisonersdilemma.strat.RandomAdapter;
import fr.uga.l3miage.pc.prisonersdilemma.strat.SondeurNaif;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SondeurNaifTest {

    private RandomAdapter randomAdapterMock;
    private SondeurNaif sondeurNaif;
    private JoueurEntity joueur1;
    private JoueurEntity joueur2;

    @BeforeEach
    void setUp() {
        randomAdapterMock = Mockito.mock(RandomAdapter.class);
        sondeurNaif = new SondeurNaif(randomAdapterMock);

        joueur1 = new JoueurEntity();
        joueur1.setId(1L);

        joueur2 = new JoueurEntity();
        joueur2.setId(2L);
    }

    @Test
    void testPremierTour_TrahitToujours() {
        List<TourEntity> tours = new ArrayList<>();

        TypeDecision decision = sondeurNaif.determinerDecision(tours, joueur1);
        assertEquals(TypeDecision.TRAHIR, decision);
    }

    @Test
    void testAdversaireCoopere_Coopere() {
        List<TourEntity> tours = createTours(joueur1, joueur2, TypeDecision.COOPERER);

        Mockito.when(randomAdapterMock.nextInt(2)).thenReturn(1); // Pas de trahison aléatoire
        TypeDecision decision = sondeurNaif.determinerDecision(tours, joueur1);

        assertEquals(TypeDecision.COOPERER, decision);
    }

    @Test
    void testAdversaireCoopere_TrahitAleatoirement() {
        List<TourEntity> tours = createTours(joueur1, joueur2, TypeDecision.COOPERER);

        Mockito.when(randomAdapterMock.nextInt(2)).thenReturn(0); // Déclenchement de la trahison aléatoire
        TypeDecision decision = sondeurNaif.determinerDecision(tours, joueur1);

        assertEquals(TypeDecision.TRAHIR, decision);
    }

    @Test
    void testAdversaireTrahit_Trahit() {
        List<TourEntity> tours = createTours(joueur1, joueur2, TypeDecision.TRAHIR);

        Mockito.when(randomAdapterMock.nextInt(2)).thenReturn(1); // Peu importe ici, toujours suit TRAHIR
        TypeDecision decision = sondeurNaif.determinerDecision(tours, joueur1);

        assertEquals(TypeDecision.TRAHIR, decision);
    }

    // Méthode utilitaire pour créer un tour
    private List<TourEntity> createTours(JoueurEntity joueur1, JoueurEntity joueur2, TypeDecision decisionAdversaire) {
        List<TourEntity> tours = new ArrayList<>();
        PartieEntity partie = new PartieEntity();
        partie.setJoueur1(joueur1);
        partie.setJoueur2(joueur2);

        TourEntity tour = new TourEntity();
        tour.setPartie(partie);
        tour.setDecisionJoueur1(TypeDecision.COOPERER); // Décision du joueur courant
        tour.setDecisionJoueur2(decisionAdversaire);    // Décision de l'adversaire

        tours.add(tour);
        return tours;
    }
}

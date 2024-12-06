package fr.uga.l3miage.pc.prisonersdilemma.components;

import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.PartieEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.TourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.TypeDecision;
import fr.uga.l3miage.pc.prisonersdilemma.strat.RandomAdapter;
import fr.uga.l3miage.pc.prisonersdilemma.strat.SondeurRepentant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SondeurRepentantTest {

    private RandomAdapter randomAdapterMock;
    private SondeurRepentant sondeurRepentant;
    private JoueurEntity joueur1;
    private JoueurEntity joueur2;

    @BeforeEach
    void setUp() {
        randomAdapterMock = Mockito.mock(RandomAdapter.class);
        sondeurRepentant = new SondeurRepentant(randomAdapterMock);

        joueur1 = new JoueurEntity();
        joueur1.setId(1L);

        joueur2 = new JoueurEntity();
        joueur2.setId(2L);
    }

    @Test
    void testPremierTour_CoopereToujours() {
        List<TourEntity> tours = new ArrayList<>();

        TypeDecision decision = sondeurRepentant.determinerDecision(tours, joueur1);

        assertEquals(TypeDecision.COOPERER, decision);
    }

    @Test
    void testTrahisonAleatoire() {
        List<TourEntity> tours = createTours(joueur1, joueur2, TypeDecision.COOPERER);

        Mockito.when(randomAdapterMock.nextInt(5)).thenReturn(0); // Déclenchement de la trahison
        TypeDecision decision = sondeurRepentant.determinerDecision(tours, joueur1);

        assertEquals(TypeDecision.TRAHIR, decision);
    }

    @Test
    void testTrahisonAdversaireApresTest_Repentance() {
        List<TourEntity> tours = createTours(joueur1, joueur2, TypeDecision.TRAHIR);

        Mockito.when(randomAdapterMock.nextInt(5)).thenReturn(0); // Déclenchement de la trahison
        sondeurRepentant.determinerDecision(tours, joueur1); // Première trahison

        TypeDecision decision = sondeurRepentant.determinerDecision(tours, joueur1); // Réponse après trahison
        assertEquals(TypeDecision.COOPERER, decision);
    }

    @Test
    void testAdversaireCoopere_SuitDerniereDecision() {
        List<TourEntity> tours = createTours(joueur1, joueur2, TypeDecision.COOPERER);

        Mockito.when(randomAdapterMock.nextInt(5)).thenReturn(3); // Pas de trahison aléatoire
        TypeDecision decision = sondeurRepentant.determinerDecision(tours, joueur1);

        assertEquals(TypeDecision.COOPERER, decision);
    }

    // Méthode utilitaire pour créer des tours
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

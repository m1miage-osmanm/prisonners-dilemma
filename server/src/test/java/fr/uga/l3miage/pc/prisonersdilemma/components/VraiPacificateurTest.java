package fr.uga.l3miage.pc.prisonersdilemma.components;

import fr.uga.l3miage.pc.prisonersdilemma.domain.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.PartieEntity;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.TourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.TypeDecision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.strat.RandomAdapter;
import fr.uga.l3miage.pc.prisonersdilemma.domain.strat.VraiPacificateur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VraiPacificateurTest {

    private RandomAdapter randomAdapterMock;
    private VraiPacificateur vraiPacificateur;
    private JoueurEntity joueur1;
    private JoueurEntity joueur2;

    @BeforeEach
    void setUp() {
        randomAdapterMock = Mockito.mock(RandomAdapter.class);
        vraiPacificateur = new VraiPacificateur(randomAdapterMock);

        joueur1 = new JoueurEntity();
        joueur1.setId(1L);

        joueur2 = new JoueurEntity();
        joueur2.setId(2L);
    }

    @Test
    void testMoinsDeDeuxTours_CoopereToujours() {
        List<TourEntity> tours = new ArrayList<>();

        TypeDecision decision = vraiPacificateur.determinerDecision(tours, joueur1);
        assertEquals(TypeDecision.COOPERER, decision);

        tours.add(createTour(TypeDecision.COOPERER, TypeDecision.COOPERER));
        decision = vraiPacificateur.determinerDecision(tours, joueur1);
        assertEquals(TypeDecision.COOPERER, decision);
    }

    @Test
    void testAdversaireTrahitDeuxFois_Trahir() {
        List<TourEntity> tours = new ArrayList<>();
        tours.add(createTour(TypeDecision.COOPERER, TypeDecision.TRAHIR));
        tours.add(createTour(TypeDecision.COOPERER, TypeDecision.TRAHIR));

        Mockito.when(randomAdapterMock.nextInt(10)).thenReturn(5); // Pas de coopération aléatoire

        TypeDecision decision = vraiPacificateur.determinerDecision(tours, joueur1);
        assertEquals(TypeDecision.TRAHIR, decision);
    }

    @Test
    void testAdversaireTrahitDeuxFois_CoopereParHasard() {
        List<TourEntity> tours = new ArrayList<>();
        tours.add(createTour(TypeDecision.COOPERER, TypeDecision.TRAHIR));
        tours.add(createTour(TypeDecision.COOPERER, TypeDecision.TRAHIR));

        Mockito.when(randomAdapterMock.nextInt(10)).thenReturn(1); // Coopération aléatoire (proba 20%)

        TypeDecision decision = vraiPacificateur.determinerDecision(tours, joueur1);
        assertEquals(TypeDecision.COOPERER, decision);
    }

    @Test
    void testAdversaireNeTrahitPasDeuxFois_Coopere() {
        List<TourEntity> tours = new ArrayList<>();
        tours.add(createTour(TypeDecision.COOPERER, TypeDecision.COOPERER));
        tours.add(createTour(TypeDecision.COOPERER, TypeDecision.TRAHIR));

        TypeDecision decision = vraiPacificateur.determinerDecision(tours, joueur1);
        assertEquals(TypeDecision.COOPERER, decision);
    }

    // Méthode utilitaire pour créer un tour
    private TourEntity createTour(TypeDecision decisionJoueur1, TypeDecision decisionJoueur2) {
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


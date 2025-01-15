package fr.uga.l3miage.pc.prisonersdilemma.components;


import fr.uga.l3miage.pc.prisonersdilemma.domain.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.PartieEntity;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.TourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.TypeDecision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.strat.PacificateurNaif;
import fr.uga.l3miage.pc.prisonersdilemma.domain.strat.RandomAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class PacificateurNaifTest {

    private PacificateurNaif pacificateurNaif;
    private RandomAdapter randomGeneratorMock;

    @BeforeEach
    void setUp() {
        randomGeneratorMock = mock(RandomAdapter.class);
        pacificateurNaif = new PacificateurNaif(randomGeneratorMock);
    }

    @Test
    void testPremiereDecision() {
        // Arrange
        JoueurEntity joueur = new JoueurEntity();
        List<TourEntity> tours = new ArrayList<>();

        // Act
        TypeDecision decision = pacificateurNaif.determinerDecision(tours, joueur);

        // Assert
        assertEquals(TypeDecision.COOPERER, decision, "La première décision devrait être COOPERER.");
    }

    @Test
    void testAdversaireCoopereEtPasDeCoupAleatoire() {
        // Arrange
        JoueurEntity joueur1 = new JoueurEntity();
        JoueurEntity joueur2 = new JoueurEntity();
        PartieEntity partie = new PartieEntity();
        partie.setJoueur1(joueur1);
        partie.setJoueur2(joueur2);

        TourEntity dernierTour = new TourEntity();
        dernierTour.setPartie(partie);
        dernierTour.setDecisionJoueur1(TypeDecision.COOPERER);
        dernierTour.setDecisionJoueur2(TypeDecision.COOPERER);

        List<TourEntity> tours = List.of(dernierTour);

        when(randomGeneratorMock.nextInt(5)).thenReturn(1); // Pas de coup aléatoire

        // Act
        TypeDecision decision = pacificateurNaif.determinerDecision(tours, joueur2);

        // Assert
        assertEquals(TypeDecision.COOPERER, decision, "Si l'adversaire coopère, le joueur devrait coopérer.");
    }

    @Test
    void testAdversaireTrahitEtPasDeCoupAleatoire() {
        // Arrange
        JoueurEntity joueur1 = new JoueurEntity();
        JoueurEntity joueur2 = new JoueurEntity();
        PartieEntity partie = new PartieEntity();
        partie.setJoueur1(joueur1);
        partie.setJoueur2(joueur2);

        TourEntity dernierTour = new TourEntity();
        dernierTour.setPartie(partie);
        dernierTour.setDecisionJoueur1(TypeDecision.TRAHIR);
        dernierTour.setDecisionJoueur2(TypeDecision.TRAHIR);

        List<TourEntity> tours = List.of(dernierTour);

        when(randomGeneratorMock.nextInt(5)).thenReturn(1); // Pas de coup aléatoire

        // Act
        TypeDecision decision = pacificateurNaif.determinerDecision(tours, joueur2);

        // Assert
        assertEquals(TypeDecision.TRAHIR, decision, "Si l'adversaire trahit et qu'il n'y a pas de coup aléatoire, le joueur devrait trahir.");
    }

    @Test
    void testAdversaireTrahitEtCoupAleatoire() {
        // Arrange
        JoueurEntity joueur1 = new JoueurEntity();
        JoueurEntity joueur2 = new JoueurEntity();
        PartieEntity partie = new PartieEntity();
        partie.setJoueur1(joueur1);
        partie.setJoueur2(joueur2);

        TourEntity dernierTour = new TourEntity();
        dernierTour.setPartie(partie);
        dernierTour.setDecisionJoueur1(TypeDecision.TRAHIR);
        dernierTour.setDecisionJoueur2(TypeDecision.TRAHIR);

        List<TourEntity> tours = List.of(dernierTour);

        when(randomGeneratorMock.nextInt(5)).thenReturn(0); // Coup aléatoire déclenché

        // Act
        TypeDecision decision = pacificateurNaif.determinerDecision(tours, joueur2);

        // Assert
        assertEquals(TypeDecision.COOPERER, decision, "Si l'adversaire trahit mais qu'un coup aléatoire est déclenché, le joueur devrait coopérer.");
    }
}

package fr.uga.l3miage.pc.prisonersdilemma.services;

import fr.uga.l3miage.pc.prisonersdilemma.adapter.StrategieFactoryAdapter;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.PartieEntity;

import fr.uga.l3miage.pc.prisonersdilemma.domain.models.TourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.TypeDecision;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import static org.mockito.Mockito.*;

class StrategieFactoryAdapterTest {

    private JoueurEntity joueur1;
    private JoueurEntity joueur2;
    private PartieEntity partieMock;
    private TourEntity tourMock1;
    private TourEntity tourMock2;

    @BeforeEach
    void setUp() {
        // Mock PartieEntity and TourEntity
        joueur1 = new JoueurEntity();
        joueur2 = new JoueurEntity();

        partieMock = Mockito.mock(PartieEntity.class);
        tourMock1 = Mockito.mock(TourEntity.class);
        tourMock2 = Mockito.mock(TourEntity.class);

        // Configure PartieEntity
        when(partieMock.getJoueur1()).thenReturn(joueur1);
        when(partieMock.getJoueur2()).thenReturn(joueur2);

        // Configure TourEntity mocks
        when(tourMock1.getPartie()).thenReturn(partieMock);
        when(tourMock1.getDecisionJoueur1()).thenReturn(TypeDecision.COOPERER);
        when(tourMock1.getDecisionJoueur2()).thenReturn(TypeDecision.TRAHIR);

        when(tourMock2.getPartie()).thenReturn(partieMock);
        when(tourMock2.getDecisionJoueur1()).thenReturn(TypeDecision.TRAHIR);
        when(tourMock2.getDecisionJoueur2()).thenReturn(TypeDecision.COOPERER);
    }

    @Test
    void testTourToHistorique_Joueur1() {
        // Arrange
        List<TourEntity> tours = new ArrayList<>();
        tours.add(tourMock1);
        tours.add(tourMock2);

        // Act
        String[] historique = StrategieFactoryAdapter.tourToHistorique(tours, joueur1);

        // Assert
        String[] expectedHistorique = {"t", "c"}; // Decisions by Joueur2 for Joueur1
        assertArrayEquals(expectedHistorique, historique);
    }

    @Test
    void testTourToHistorique_Joueur2() {
        // Arrange
        List<TourEntity> tours = new ArrayList<>();
        tours.add(tourMock1);
        tours.add(tourMock2);

        // Act
        String[] historique = StrategieFactoryAdapter.tourToHistorique(tours, joueur2);

        // Assert
        String[] expectedHistorique = {"c", "t"};
        assertArrayEquals(expectedHistorique, historique);
    }

}

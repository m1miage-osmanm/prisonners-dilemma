package fr.uga.l3miage.pc.prisonersdilemma.services;


import fr.uga.l3miage.pc.prisonersdilemma.adapter.StrategieAdapter;
import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.TourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.TypeDecision;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StrategieAdapterTest {

    private fr.uga.l3miage.pc.stratégies.Strategie strategieExterneMock;
    private StrategieAdapter strategieAdapter;

    @BeforeEach
    void setUp() {

        strategieExterneMock = Mockito.mock(fr.uga.l3miage.pc.stratégies.Strategie.class);


        strategieAdapter = new StrategieAdapter(strategieExterneMock);
    }

    @Test
    void testDeterminerDecision_Cooperer() {
        // Configuration du mock pour retourner "c" (cooperer)
        when(strategieExterneMock.prochainCoup()).thenReturn("c");


        List<TourEntity> tours = new ArrayList<>();
        JoueurEntity joueur = new JoueurEntity();


        TypeDecision decision = strategieAdapter.determinerDecision(tours, joueur);

        assertEquals(TypeDecision.COOPERER, decision);

        verify(strategieExterneMock, times(1)).prochainCoup();
    }

    @Test
    void testDeterminerDecision_Trahir() {

        when(strategieExterneMock.prochainCoup()).thenReturn("t");


        List<TourEntity> tours = new ArrayList<>();
        JoueurEntity joueur = new JoueurEntity();


        TypeDecision decision = strategieAdapter.determinerDecision(tours, joueur);


        assertEquals(TypeDecision.TRAHIR, decision);

        verify(strategieExterneMock, times(1)).prochainCoup();
    }

    @Test
    void testAdapterNameAndDescription() {

        String expectedName = "Adapted " + strategieExterneMock.getClass().getSimpleName();
        String expectedDescription = "Adapted strategy from external system";

        assertEquals(expectedName, strategieAdapter.getName());
        assertEquals(expectedDescription, strategieAdapter.getDescription());
    }
}

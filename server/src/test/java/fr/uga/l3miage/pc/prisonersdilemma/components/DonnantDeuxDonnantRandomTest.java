package fr.uga.l3miage.pc.prisonersdilemma.components;


import fr.uga.l3miage.pc.prisonersdilemma.domain.models.TypeDecision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.strat.DonnantDeuxDonnantRandom;
import fr.uga.l3miage.pc.prisonersdilemma.domain.strat.RandomAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DonnantDeuxDonnantRandomTest {

    private DonnantDeuxDonnantRandom donnantDeuxDonnantRandom;
    private RandomAdapter randomAdapterMock;

    @BeforeEach
    void setUp() {
        // Mock the RandomAdapter
        randomAdapterMock = mock(RandomAdapter.class);

        donnantDeuxDonnantRandom = new DonnantDeuxDonnantRandom(randomAdapterMock);
    }

    @Test
    void testDecisionAleatoire() {
        // Arrange
        when(randomAdapterMock.nextInt(5)).thenReturn(0); // Force random decision
        when(randomAdapterMock.nextBoolean()).thenReturn(true); // Force COOPERER

        // Act
        TypeDecision decision = donnantDeuxDonnantRandom.decisionAvecRéciprocité(TypeDecision.TRAHIR);

        // Assert
        assertEquals(TypeDecision.COOPERER, decision, "Should make a random decision to COOPERER.");
    }

    @Test
    void testDecisionAvecReciprocite() {
        // Arrange
        when(randomAdapterMock.nextInt(5)).thenReturn(1); // No random decision

        // Act
        TypeDecision decision = donnantDeuxDonnantRandom.decisionAvecRéciprocité(TypeDecision.TRAHIR);

        // Assert
        assertEquals(TypeDecision.TRAHIR, decision, "Should reciprocate the adversary's decision.");
    }

    @Test
    void testDecisionAleatoireTrahir() {
        // Arrange
        when(randomAdapterMock.nextInt(5)).thenReturn(0); // Force random decision
        when(randomAdapterMock.nextBoolean()).thenReturn(false); // Force TRAHIR

        // Act
        TypeDecision decision = donnantDeuxDonnantRandom.decisionAvecRéciprocité(TypeDecision.COOPERER);

        // Assert
        assertEquals(TypeDecision.TRAHIR, decision, "Should make a random decision to TRAHIR.");
    }
}


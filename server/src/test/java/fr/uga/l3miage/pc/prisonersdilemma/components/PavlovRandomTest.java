package fr.uga.l3miage.pc.prisonersdilemma.components;

import fr.uga.l3miage.pc.prisonersdilemma.models.TypeDecision;
import fr.uga.l3miage.pc.prisonersdilemma.strat.PavlovRandom;
import fr.uga.l3miage.pc.prisonersdilemma.strat.RandomAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PavlovRandomTest {

    private RandomAdapter randomAdapterMock;
    private PavlovRandom pavlovRandom;

    @BeforeEach
    void setUp() {
        // Crée un mock pour RandomAdapter
        randomAdapterMock = Mockito.mock(RandomAdapter.class);
        // Initialise PavlovRandom avec le mock
        pavlovRandom = new PavlovRandom(randomAdapterMock);
    }

    @Test
    void testDecisionAvecScorePositif_RandomTrigger() {
        // Configure le mock pour simuler une valeur aléatoire de 0 (random.nextInt(5) == 0)
        Mockito.when(randomAdapterMock.nextInt(5)).thenReturn(0);
        Mockito.when(randomAdapterMock.nextBoolean()).thenReturn(true);

        // Appelle la méthode avec une décision précédente
        TypeDecision result = pavlovRandom.decisionAvecScorePositif(TypeDecision.TRAHIR);

        // Vérifie que la décision aléatoire est COOPERER
        assertEquals(TypeDecision.COOPERER, result);
    }

    @Test
    void testDecisionAvecScorePositif_NoRandomTrigger() {
        // Configure le mock pour simuler une valeur aléatoire différente de 0
        Mockito.when(randomAdapterMock.nextInt(5)).thenReturn(3);

        // Appelle la méthode avec une décision précédente
        TypeDecision result = pavlovRandom.decisionAvecScorePositif(TypeDecision.TRAHIR);

        // Vérifie que la décision précédente est répétée
        assertEquals(TypeDecision.TRAHIR, result);
    }

    @Test
    void testDecisionAvecScoreNegatif() {
        // Appelle la méthode pour une décision avec un score négatif
        TypeDecision result = pavlovRandom.decisionAvecScoreNegatif();

        // Vérifie que la décision est toujours COOPERER
        assertEquals(TypeDecision.COOPERER, result);
    }
}

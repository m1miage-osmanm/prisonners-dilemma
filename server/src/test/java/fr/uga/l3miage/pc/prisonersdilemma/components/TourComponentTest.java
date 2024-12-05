package fr.uga.l3miage.pc.prisonersdilemma.components;

import fr.uga.l3miage.pc.prisonersdilemma.models.PartieEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.TourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.TypeDecision;
import fr.uga.l3miage.pc.prisonersdilemma.repositories.PartieRepository;
import fr.uga.l3miage.pc.prisonersdilemma.repositories.TourRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
class TourComponentTest {
    private TourComponent tourComponent;
    private TourRepository tourRepository;

    @BeforeEach
    public void setUp() {
        tourRepository=mock(TourRepository.class);
        tourComponent=new TourComponent(tourRepository);
    }
    @Test
    void creerEtSauvegarderTour_shouldCreateAndSaveTour() {
        // Arrange
        PartieEntity partie = PartieEntity.builder().id(1).nbTours(5).build();
        TourEntity tour = TourEntity.builder().partie(partie).build();
        when(tourRepository.save(any(TourEntity.class))).thenReturn(tour);

        // Act
        TourEntity createdTour = tourComponent.creerEtSauvegarderTour(partie);

        // Assert
        assertNotNull(createdTour, "Le tour créé ne doit pas être nul");
        assertEquals(partie, createdTour.getPartie(), "La partie associée doit être correcte");
        verify(tourRepository, times(1)).save(any(TourEntity.class));
    }

    @Test
    void calculerScores_shouldSetCorrectScoresForTrahirAndCooperer() {
        // Arrange
        TourEntity tour = TourEntity.builder()
                .decisionJoueur1(TypeDecision.TRAHIR)
                .decisionJoueur2(TypeDecision.COOPERER)
                .build();

        // Act
        tourComponent.calculerScores(tour);

        // Assert
        assertEquals(TourComponent.T, tour.getScoreJoueur1(), "Le score du joueur 1 doit être T");
        assertEquals(TourComponent.D, tour.getScoreJoueur2(), "Le score du joueur 2 doit être D");
    }

    @Test
    void calculerScores_shouldSetCorrectScoresForCoopererAndTrahir() {
        // Arrange
        TourEntity tour = TourEntity.builder()
                .decisionJoueur1(TypeDecision.COOPERER)
                .decisionJoueur2(TypeDecision.TRAHIR)
                .build();

        // Act
        tourComponent.calculerScores(tour);

        // Assert
        assertEquals(TourComponent.D, tour.getScoreJoueur1(), "Le score du joueur 1 doit être D");
        assertEquals(TourComponent.T, tour.getScoreJoueur2(), "Le score du joueur 2 doit être T");
    }

    @Test
    void calculerScores_shouldSetCorrectScoresForBothCooperer() {
        // Arrange
        TourEntity tour = TourEntity.builder()
                .decisionJoueur1(TypeDecision.COOPERER)
                .decisionJoueur2(TypeDecision.COOPERER)
                .build();

        // Act
        tourComponent.calculerScores(tour);

        // Assert
        assertEquals(TourComponent.C, tour.getScoreJoueur1(), "Le score du joueur 1 doit être C");
        assertEquals(TourComponent.C, tour.getScoreJoueur2(), "Le score du joueur 2 doit être C");
    }

    @Test
    void calculerScores_shouldSetCorrectScoresForBothTrahir() {
        // Arrange
        TourEntity tour = TourEntity.builder()
                .decisionJoueur1(TypeDecision.TRAHIR)
                .decisionJoueur2(TypeDecision.TRAHIR)
                .build();

        // Act
        tourComponent.calculerScores(tour);

        // Assert
        assertEquals(TourComponent.P, tour.getScoreJoueur1(), "Le score du joueur 1 doit être P");
        assertEquals(TourComponent.P, tour.getScoreJoueur2(), "Le score du joueur 2 doit être P");
    }

    @Test
    void choisirDecisionJ1_shouldSetDecisionForPlayer1() {
        // Arrange
        TourEntity tour = TourEntity.builder().id(1).build();
        TypeDecision decision = TypeDecision.TRAHIR;
        when(tourRepository.save(any(TourEntity.class))).thenReturn(tour);

        // Act
        TourEntity updatedTour = tourComponent.choisirDecisionJ1(tour, decision);

        // Assert
        assertNotNull(updatedTour, "Le tour mis à jour ne doit pas être nul");
        assertEquals(decision, tour.getDecisionJoueur1(), "La décision du joueur 1 doit être correcte");
        verify(tourRepository, times(1)).save(tour);
    }

    @Test
    void choisirDecisionJ2_shouldSetDecisionForPlayer2() {
        // Arrange
        TourEntity tour = TourEntity.builder().id(1).build();
        TypeDecision decision = TypeDecision.COOPERER;
        when(tourRepository.save(any(TourEntity.class))).thenReturn(tour);

        // Act
        TourEntity updatedTour = tourComponent.choisirDecisionJ2(tour, decision);

        // Assert
        assertNotNull(updatedTour, "Le tour mis à jour ne doit pas être nul");
        assertEquals(decision, tour.getDecisionJoueur2(), "La décision du joueur 2 doit être correcte");
        verify(tourRepository, times(1)).save(tour);
    }
}

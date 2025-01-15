package fr.uga.l3miage.pc.prisonersdilemma.components;

import fr.uga.l3miage.pc.prisonersdilemma.domain.components.PartieComponent;
import fr.uga.l3miage.pc.prisonersdilemma.domain.components.TourComponent;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.*;
import fr.uga.l3miage.pc.prisonersdilemma.ports.out.IpartieRepository;
import fr.uga.l3miage.pc.prisonersdilemma.domain.services.StrategieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
class PartieComponentTest {

    private IpartieRepository partieRepository;
    private TourComponent tourComponent;
    private StrategieService strategieService;
    private PartieComponent partieComponent;

    @BeforeEach
    void setUp() {
        partieRepository = mock(IpartieRepository.class);
        tourComponent = mock(TourComponent.class);
        strategieService = mock(StrategieService.class);

        partieComponent = new PartieComponent(partieRepository, tourComponent, strategieService);
    }

    @Test
    void testCreerPartie2JoueursOK() {
        // Given
        JoueurEntity joueur = JoueurEntity.builder().username("Alice").build();
        PartieEntity partie = PartieEntity.builder().nbTours(5).joueur1(joueur).build();
        when(partieRepository.save(any(PartieEntity.class))).thenReturn(partie);

        // When
        PartieEntity result = partieComponent.creerPartie2joueurs(joueur, 5);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getJoueur1()).isEqualTo(joueur);
        assertThat(result.getNbTours()).isEqualTo(5);
        verify(partieRepository, times(1)).save(any(PartieEntity.class));
    }

    @Test
    void creerPartie2joueurKO() {
        // Arrange
        JoueurEntity joueur1 = JoueurEntity.builder().id(1L).build();
        when(partieRepository.save(any(PartieEntity.class))).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () ->
                partieComponent.creerPartie2joueurs(joueur1, 5)
        );

        assertEquals("Database error", exception.getMessage());
        verify(partieRepository, times(1)).save(any(PartieEntity.class));
    }

    @Test
    void testRejoindrePartieSuccess() {
        // Given
        JoueurEntity joueur2 = JoueurEntity.builder().username("Bob").build();
        PartieEntity partie = PartieEntity.builder().id(1).nbTours(5).joueur1(new JoueurEntity()).build();
        when(partieRepository.findPartieEntityById(1)).thenReturn(Optional.of(partie));
        when(partieRepository.save(any(PartieEntity.class))).thenReturn(partie);

        // When
        PartieEntity result = partieComponent.rejoindrePartie(joueur2, 1);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getJoueur2()).isEqualTo(joueur2);
        verify(partieRepository, times(1)).findPartieEntityById(1);
        verify(partieRepository, times(1)).save(partie);
    }

    @Test
    void testRejoindrePartieNotFound() {
        // Given
        when(partieRepository.findPartieEntityById(999)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> partieComponent.rejoindrePartie(new JoueurEntity(), 999));
        verify(partieRepository, times(1)).findPartieEntityById(999);
    }

    @Test
    void rejoindrePartieSaveKO() {
        // Arrange
        JoueurEntity joueur2 = JoueurEntity.builder().id(2L).build();
        PartieEntity partieMock = PartieEntity.builder().id(1).build();

        when(partieRepository.findPartieEntityById(1)).thenReturn(Optional.of(partieMock));
        when(partieRepository.save(any(PartieEntity.class))).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () ->
                partieComponent.rejoindrePartie(joueur2, 1)
        );

        assertEquals("Database error", exception.getMessage());
        verify(partieRepository, times(1)).findPartieEntityById(1);
        verify(partieRepository, times(1)).save(any(PartieEntity.class));
    }

    @Test
    void testJouerUnTour() {
        // Given
        JoueurEntity joueur1 = JoueurEntity.builder().username("Alice").build();
        JoueurEntity joueur2 = JoueurEntity.builder().username("Bob").build();
        PartieEntity partie = PartieEntity.builder().id(1).joueur1(joueur1).joueur2(joueur2).nbTours(5).tours(new ArrayList<>()).estPret(true).build();

        when(partieRepository.findPartieEntityById(1)).thenReturn(Optional.of(partie));
        when(tourComponent.creerEtSauvegarderTour(any(PartieEntity.class))).thenReturn(new TourEntity());
        when(strategieService.getStrategie(anyString())).thenReturn(mock(Strategie.class));

        // When
        Integer[] result = partieComponent.jouerUnTour(1, Optional.of(TypeDecision.COOPERER), Optional.of(TypeDecision.TRAHIR));

        // Then
        assertThat(result).isNotNull();
        verify(partieRepository, times(1)).findPartieEntityById(1);
        verify(tourComponent, times(1)).creerEtSauvegarderTour(any(PartieEntity.class));
    }

    @Test
    void testJouerUnTourPartieNonPrête() {
        // Given
        PartieEntity partie = PartieEntity.builder().id(1).estPret(false).build();
        when(partieRepository.findPartieEntityById(1)).thenReturn(Optional.of(partie));

        // When & Then
        assertThrows(IllegalStateException.class, () -> partieComponent.jouerUnTour(1, Optional.of(TypeDecision.COOPERER), Optional.of(TypeDecision.TRAHIR)));
        verify(partieRepository, times(1)).findPartieEntityById(1);
    }
}

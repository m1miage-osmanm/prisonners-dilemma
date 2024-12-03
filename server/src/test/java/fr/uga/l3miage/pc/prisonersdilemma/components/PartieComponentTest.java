package fr.uga.l3miage.pc.prisonersdilemma.components;

import fr.uga.l3miage.pc.prisonersdilemma.models.*;
import fr.uga.l3miage.pc.prisonersdilemma.repositories.PartieRepository;
import fr.uga.l3miage.pc.prisonersdilemma.services.StrategieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

class PartieComponentTest {

    private PartieRepository partieRepository;
    private TourComponent tourComponent;
    private StrategieService strategieService;
    private PartieComponent partieComponent;

    @BeforeEach
    void setUp() {
        partieRepository = mock(PartieRepository.class);
        tourComponent = mock(TourComponent.class);
        strategieService = mock(StrategieService.class);

        partieComponent = new PartieComponent(partieRepository, tourComponent, strategieService);
    }

    @Test
    void testCreerPartie2Joueurs() {
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
        JoueurEntity joueur1 = JoueurEntity.builder().username("Alice").build();
        JoueurEntity joueur2 = JoueurEntity.builder().username("Bob").build();
        PartieEntity partie = PartieEntity.builder().id(1).joueur1(joueur1).joueur2(joueur2).nbTours(5).tours(new ArrayList<>()).estPret(false).build();

        when(partieRepository.findPartieEntityById(1)).thenReturn(Optional.of(partie));

        // When & Then
        assertThrows(IllegalStateException.class, () -> partieComponent.jouerUnTour(1, Optional.of(TypeDecision.COOPERER), Optional.of(TypeDecision.TRAHIR)));
        verify(partieRepository, times(1)).findPartieEntityById(1);
    }

    @Test
    void testJouerUnTourPartieTerminee() {
        JoueurEntity joueur1 = JoueurEntity.builder().username("Alice").build();
        JoueurEntity joueur2 = JoueurEntity.builder().username("Bob").build();

        TourEntity tourJoue = TourEntity.builder()
                .scoreJoueur1(3)
                .scoreJoueur2(5)
                .decisionJoueur1(TypeDecision.COOPERER)
                .decisionJoueur2(TypeDecision.TRAHIR)
                .build();

        PartieEntity partie = PartieEntity.builder()
                .id(1)
                .joueur1(joueur1)
                .joueur2(joueur2)
                .nbTours(1)
                .tours(new ArrayList<>(List.of(tourJoue))) // Ce tour a déjà été joué
                .estPret(true)
                .build();

        when(partieRepository.findPartieEntityById(1)).thenReturn(Optional.of(partie));

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> partieComponent.jouerUnTour(1, Optional.of(TypeDecision.COOPERER), Optional.of(TypeDecision.TRAHIR))
        );

        // Assertions
        assertThat(exception.getMessage()).isEqualTo("Tous les tours de la partie ont déjà été joués.");
        verify(partieRepository, times(1)).findPartieEntityById(1);
        verifyNoInteractions(tourComponent); // Aucun appel à tourComponent car le tour ne doit pas être créé
    }


}

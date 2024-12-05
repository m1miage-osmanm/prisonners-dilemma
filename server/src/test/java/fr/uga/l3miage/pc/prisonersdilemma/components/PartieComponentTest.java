package fr.uga.l3miage.pc.prisonersdilemma.components;

import fr.uga.l3miage.pc.prisonersdilemma.models.*;
import fr.uga.l3miage.pc.prisonersdilemma.repositories.PartieRepository;
import fr.uga.l3miage.pc.prisonersdilemma.services.StrategieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
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
        JoueurEntity joueur1 = new JoueurEntity(); // Simule un joueur
        joueur1.setId(1L);

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
        JoueurEntity joueur2 = new JoueurEntity();
        joueur2.setId(2L);

        PartieEntity partieMock = new PartieEntity();
        partieMock.setId(1);

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
    @Test
    void obtenirDecision_OK_StrategieUtilisee() {
        // Arrange
        Optional<TypeDecision> decisionOptionnelle = Optional.empty();
        String typeStrategie = "strategieTest";
        List<TourEntity> tours = new ArrayList<>(); // Simule une liste de tours
        JoueurEntity joueur = new JoueurEntity();

        Strategie strategieMock = mock(Strategie.class);
        TypeDecision decisionMock = TypeDecision.COOPERER;
        when(strategieService.getStrategie(typeStrategie)).thenReturn(strategieMock);
        when(strategieMock.determinerDecision(tours, joueur)).thenReturn(decisionMock);

        // Act
        TypeDecision result = partieComponent.obtenirDecision(decisionOptionnelle, typeStrategie, tours, joueur);

        // Assert
        assertNotNull(result);
        assertEquals(decisionMock, result);
        verify(strategieService, times(1)).getStrategie(typeStrategie);
        verify(strategieMock, times(1)).determinerDecision(tours, joueur);
    }
    @Test
    void obtenirDecision_OK_DecisionOptionnellePresente() {
        // Arrange
        TypeDecision decisionMock = TypeDecision.TRAHIR;
        Optional<TypeDecision> decisionOptionnelle = Optional.of(decisionMock);

        // Act
        TypeDecision result = partieComponent.obtenirDecision(decisionOptionnelle, "anyStrategie", new ArrayList<>(), new JoueurEntity());

        // Assert
        assertNotNull(result);
        assertEquals(decisionMock, result);
        verifyNoInteractions(strategieService);
    }

    @Test
    void joueurQuitte_OK_Joueur1Quitte() {
        // Arrange
        PartieEntity partieMock = new PartieEntity();
        JoueurEntity joueur1 = new JoueurEntity();
        joueur1.setId(1L);
        partieMock.setJoueur1(joueur1);

        when(partieRepository.findPartieEntityById(1)).thenReturn(Optional.of(partieMock));
        when(partieRepository.save(partieMock)).thenReturn(partieMock);

        // Act
        PartieEntity result = partieComponent.joueurQuitte(1, 1L, "strategieTest");

        // Assert
        assertNotNull(result);
        assertEquals("strategieTest", partieMock.getTypeStrategieJoueur1());
        verify(partieRepository, times(1)).findPartieEntityById(1);
        verify(partieRepository, times(1)).save(partieMock);
    }
    @Test
    void joueurQuitte_KO_JoueurNonDansLaPartie() {
        // Arrange
        PartieEntity partieMock = new PartieEntity();
        JoueurEntity joueur1 = new JoueurEntity();
        joueur1.setId(1L);
        JoueurEntity joueur2 = new JoueurEntity();
        joueur2.setId(2L);
        partieMock.setJoueur1(joueur1);
        partieMock.setJoueur2(joueur2);

        when(partieRepository.findPartieEntityById(1)).thenReturn(Optional.of(partieMock));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () ->
                partieComponent.joueurQuitte(1, 3L, "strategieTest")
        );
        assertEquals("joueur non dans la partie", exception.getMessage());
        verify(partieRepository, times(1)).findPartieEntityById(1);
        verify(partieRepository, never()).save(any(PartieEntity.class));
    }
    @Test
    void joueurQuitte_KO_PartieNonExistante() {
        // Arrange
        when(partieRepository.findPartieEntityById(1)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                partieComponent.joueurQuitte(1, 1L, "strategieTest")
        );
        assertEquals("Partie with ID 1 does not exist.", exception.getMessage());
        verify(partieRepository, times(1)).findPartieEntityById(1);
    }
    @Test
    void scorePartie_OK_ScoresCalcules() {
        // Arrange
        PartieEntity partieMock = new PartieEntity();
        TourEntity tour1 = new TourEntity();
        tour1.setScoreJoueur1(10);
        tour1.setScoreJoueur2(20);

        TourEntity tour2 = new TourEntity();
        tour2.setScoreJoueur1(15);
        tour2.setScoreJoueur2(25);

        partieMock.setTours(List.of(tour1, tour2));
        when(partieRepository.findPartieEntityById(1)).thenReturn(Optional.of(partieMock));

        // Act
        Integer[] scores = partieComponent.scorePartie(1);

        // Assert
        assertNotNull(scores);
        assertEquals(25, scores[0]); // Score total joueur 1
        assertEquals(45, scores[1]); // Score total joueur 2
        verify(partieRepository, times(1)).findPartieEntityById(1);
    }
    @Test
    void scorePartie_KO_PartieNonExistante() {
        // Arrange
        when(partieRepository.findPartieEntityById(1)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                partieComponent.scorePartie(1)
        );
        assertEquals("Partie with ID 1 does not exist.", exception.getMessage());
        verify(partieRepository, times(1)).findPartieEntityById(1);
    }


}

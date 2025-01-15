package fr.uga.l3miage.pc.prisonersdilemma.services;

import fr.uga.l3miage.pc.prisonersdilemma.domain.components.PartieComponent;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.PartieEntity;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.TypeDecision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.services.PartieService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
class PartieServiceTest {

    @Mock
    private PartieComponent partieComponent;

    @InjectMocks
    private PartieService partieService;

    @Test
    void testCreerPartie2Joueurs() {
        // Given
        JoueurEntity joueur = JoueurEntity.builder().username("Alice").build();
        PartieEntity partie = PartieEntity.builder().id(1).nbTours(5).joueur1(joueur).build();
        when(partieComponent.creerPartie2joueurs(joueur, 5)).thenReturn(partie);

        // When
        PartieEntity result = partieService.creerPartie2joueurs(joueur, 5);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getJoueur1()).isEqualTo(joueur);
        assertThat(result.getNbTours()).isEqualTo(5);
        verify(partieComponent, times(1)).creerPartie2joueurs(joueur, 5);  // Vérifie que le mock a été appelé une fois
    }
    @Test
    void creerPartie2joueurs_KO_Exception() {
        // Arrange
        JoueurEntity joueur1 = new JoueurEntity();
        when(partieComponent.creerPartie2joueurs(joueur1, 5)).thenThrow(new RuntimeException("Erreur de création"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () ->
                partieService.creerPartie2joueurs(joueur1, 5)
        );
        assertEquals("Erreur de création", exception.getMessage());
        verify(partieComponent, times(1)).creerPartie2joueurs(joueur1, 5);
    }
    @Test
    void rejoindrePartie_OK() {
        // Arrange
        JoueurEntity joueur2 = new JoueurEntity();
        joueur2.setId(2L);

        PartieEntity partieMock = new PartieEntity();
        when(partieComponent.rejoindrePartie(joueur2, 1)).thenReturn(partieMock);

        // Act
        PartieEntity result = partieService.rejoindrePartie(joueur2, 1);

        // Assert
        assertNotNull(result);
        verify(partieComponent, times(1)).rejoindrePartie(joueur2, 1);
    }
    @Test
    void rejoindrePartie_KO_PartieNonExistante() {
        // Arrange
        JoueurEntity joueur2 = new JoueurEntity();
        when(partieComponent.rejoindrePartie(joueur2, 1))
                .thenThrow(new IllegalArgumentException("Partie with ID 1 does not exist."));

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                partieService.rejoindrePartie(joueur2, 1)
        );
        assertEquals("Partie with ID 1 does not exist.", exception.getMessage());
        verify(partieComponent, times(1)).rejoindrePartie(joueur2, 1);
    }
    @Test
    void jouerUnTour_OK() {
        // Arrange
        Integer idPartie = 1;
        Optional<TypeDecision> decision1 = Optional.of(TypeDecision.COOPERER);
        Optional<TypeDecision> decision2 = Optional.of(TypeDecision.COOPERER);
        Integer[] expectedScores = {10, 20};

        when(partieComponent.jouerUnTour(idPartie, decision1, decision2)).thenReturn(expectedScores);

        // Act
        Integer[] result = partieService.jouerUnTour(idPartie, decision1, decision2);

        // Assert
        assertNotNull(result);
        assertArrayEquals(expectedScores, result);
        verify(partieComponent, times(1)).jouerUnTour(idPartie, decision1, decision2);
    }

    @Test
    void jouerUnTour_KO_Exception() {
        // Arrange
        Integer idPartie = 1;
        Optional<TypeDecision> decision1 = Optional.empty();
        Optional<TypeDecision> decision2 = Optional.empty();

        when(partieComponent.jouerUnTour(idPartie, decision1, decision2))
                .thenThrow(new RuntimeException("Erreur pendant le tour"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () ->
                partieService.jouerUnTour(idPartie, decision1, decision2)
        );
        assertEquals("Erreur pendant le tour", exception.getMessage());
        verify(partieComponent, times(1)).jouerUnTour(idPartie, decision1, decision2);
    }
    @Test
    void joueurQuitte_OK() {
        // Arrange
        Integer idPartie = 1;
        Long idJoueur = 1L;
        String typeStrategie = "strategieTest";

        PartieEntity partieMock = new PartieEntity();
        when(partieComponent.joueurQuitte(idPartie, idJoueur, typeStrategie)).thenReturn(partieMock);

        // Act
        PartieEntity result = partieService.joueurQuitte(idPartie, idJoueur, typeStrategie);

        // Assert
        assertNotNull(result);
        verify(partieComponent, times(1)).joueurQuitte(idPartie, idJoueur, typeStrategie);
    }
    @Test
    void joueurQuitte_KO_JoueurNonDansPartie() {
        // Arrange
        Integer idPartie = 1;
        Long idJoueur = 3L;
        String typeStrategie = "strategieTest";

        when(partieComponent.joueurQuitte(idPartie, idJoueur, typeStrategie))
                .thenThrow(new RuntimeException("joueur non dans la partie"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () ->
                partieService.joueurQuitte(idPartie, idJoueur, typeStrategie)
        );
        assertEquals("joueur non dans la partie", exception.getMessage());
        verify(partieComponent, times(1)).joueurQuitte(idPartie, idJoueur, typeStrategie);
    }
    @Test
    void getScorePartie_OK() {
        // Arrange
        Integer idPartie = 1;
        Integer[] expectedScores = {50, 60};

        when(partieComponent.scorePartie(idPartie)).thenReturn(expectedScores);

        // Act
        Integer[] result = partieService.getScorePartie(idPartie);

        // Assert
        assertNotNull(result);
        assertArrayEquals(expectedScores, result);
        verify(partieComponent, times(1)).scorePartie(idPartie);
    }
    @Test
    void getScorePartie_KO_PartieNonExistante() {
        // Arrange
        Integer idPartie = 1;

        when(partieComponent.scorePartie(idPartie))
                .thenThrow(new IllegalArgumentException("Partie with ID 1 does not exist."));

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                partieService.getScorePartie(idPartie)
        );
        assertEquals("Partie with ID 1 does not exist.", exception.getMessage());
        verify(partieComponent, times(1)).scorePartie(idPartie);
    }

}

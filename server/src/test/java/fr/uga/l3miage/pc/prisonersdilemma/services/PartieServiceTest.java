package fr.uga.l3miage.pc.prisonersdilemma.services;

import fr.uga.l3miage.pc.prisonersdilemma.components.PartieComponent;
import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.PartieEntity;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)  // Extension pour injecter les mocks automatiquement
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
}

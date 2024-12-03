package fr.uga.l3miage.pc.prisonersdilemma.controllers;
import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.PartieEntity;
import fr.uga.l3miage.pc.prisonersdilemma.services.PartieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
public class PartieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PartieService partieService;

    @Test
    void testCreerPartie2JoueursSuccess() throws Exception {
        JoueurEntity joueur = JoueurEntity.builder().username("Alice").build();
        PartieEntity partie = PartieEntity.builder().id(1).nbTours(5).build();

        when(partieService.creerPartie2joueurs(any(JoueurEntity.class), any(Integer.class))).thenReturn(partie);

        mockMvc.perform(post("/api/parties/create/Alice/5"))
                .andExpect(status().isOk()) // Vérifie que la réponse est OK (status 200)
                .andReturn();

        verify(partieService).creerPartie2joueurs(any(JoueurEntity.class), any(Integer.class));
    }

    @Test
    void testCreerPartie2JoueursBadRequest() throws Exception {
        mockMvc.perform(post("/api/parties/create//0"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}

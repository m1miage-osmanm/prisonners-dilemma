package fr.uga.l3miage.pc.prisonersdilemma.controllers;

import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.PartieEntity;
import fr.uga.l3miage.pc.prisonersdilemma.services.PartieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class PartieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PartieService partieService;

    @Test
    void testCreerPartie2JoueursSuccess() throws Exception {
        // Given
        JoueurEntity joueur = JoueurEntity.builder().username("Alice").build();
        PartieEntity partie = PartieEntity.builder().id(1).nbTours(5).build();

        when(partieService.creerPartie2joueurs(joueur, 5)).thenReturn(partie);

        // When
        MvcResult result = mockMvc.perform(post("/api/parties/create/Alice/5") // Correction de l'URL pour correspondre à celle du contrôleur
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)) // Pas besoin de params ici
                .andExpect(status().isOk()) // Vérifie que la réponse est OK (status 200)
                .andReturn();

        // Then
        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo("1");
    }

    @Test
    void testCreerPartie2JoueursBadRequest() throws Exception {
        // Given & When
        mockMvc.perform(post("/api/parties/create/Alice/5")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .param("nameJ1", "")
                        .param("nbTours", "0"))
                .andExpect(status().isBadRequest());
    }
}

package fr.uga.l3miage.pc.prisonersdilemma.controllers;
import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.PartieEntity;
import fr.uga.l3miage.pc.prisonersdilemma.repositories.JoueurRepository;
import fr.uga.l3miage.pc.prisonersdilemma.repositories.PartieRepository;
import fr.uga.l3miage.pc.prisonersdilemma.services.PartieService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureTestDatabase
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
@ActiveProfiles("test")
public class PartieControllerTest {
    @Autowired
    private PartieRepository partieRepository;

    @Autowired
    private JoueurRepository joueurRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;
    @AfterEach
    void deleteAll() {
        partieRepository.deleteAll();
        joueurRepository.deleteAll();
    }

    @Test
    void creerPartie2JoueursSuccess() {
        // Préparation des données
        String joueur1 = "Player1";
        Integer nbTours = 5;

        // Appel de l'API
        ResponseEntity<Integer> response = testRestTemplate.postForEntity(
                "/api/parties/create/" + joueur1 + "/" + nbTours,
                null,
                Integer.class
        );

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(partieRepository.findById(response.getBody()).isPresent());
    }

    @Test
    void rejoindrePartieSuccess() {
        // Création d'une partie dans la base
        JoueurEntity joueur1 = JoueurEntity.builder().username("Player1").build();
        PartieEntity partie = PartieEntity.builder().joueur1(joueur1).nbTours(5).build();
        partieRepository.save(partie);

        // Préparation des données
        String joueur2 = "Player2";
        Integer idPartie = partie.getId();

        // Appel de l'API
        ResponseEntity<Integer> response = testRestTemplate.postForEntity(
                "/api/parties/join/" + joueur2 + "/" + idPartie,
                null,
                Integer.class
        );

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        PartieEntity updatedPartie = partieRepository.findById(idPartie).orElse(null);
        assertNotNull(updatedPartie);
        assertNotNull(updatedPartie.getJoueur2());
        assertEquals("Player2", updatedPartie.getJoueur2().getUsername());
    }

    @Test
    void jouerUnTourSuccess() {
        // Création d'une partie avec deux joueurs
        JoueurEntity joueur1 = JoueurEntity.builder().username("Player1").build();
        JoueurEntity joueur2 = JoueurEntity.builder().username("Player2").build();

        PartieEntity partie = PartieEntity.builder()
                .joueur1(joueur1)
                .joueur2(joueur2)
                .nbTours(5)
                .estPret(true)
                .tours(new ArrayList<>())
                .build();
        partieRepository.save(partie);

        Integer idPartie = partie.getId();
        String decision1 = "cooperer";
        String decision2 = "trahir";

        // Appel de l'API
        ResponseEntity<Integer[]> response = testRestTemplate.postForEntity(
                "/api/parties/jouerTour/" + idPartie + "?decision1=" + decision1 + "&decision2=" + decision2,
                null,
                Integer[].class
        );

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        System.out.println(Arrays.toString(response.getBody()));
        assertEquals(2, response.getBody().length);
    }
/*
    @Test
    public void joueurQuitteSuccess() {
        // Création d'une partie avec un joueur
        JoueurEntity joueur1 = JoueurEntity.builder().username("Player1").build();
        joueurRepository.save(joueur1);
        PartieEntity partie = PartieEntity.builder()
                .joueur1(joueur1)
                .nbTours(5)
                .build();
        partieRepository.save(partie);

        Long idJoueur = joueur1.getId();
        Integer idPartie = partie.getId();
        String typeStrategie = "defensive";

        // Appel de l'API
        ResponseEntity<Integer> response = testRestTemplate.postForEntity(
                "/api/parties/quitter/" + idPartie + "/" + idJoueur + "?typeStrategie=" + typeStrategie,
                null,
                Integer.class
        );

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(idPartie, response.getBody());
    }

    @Test
    public void getResultatPartieSuccess() {
        // Création d'une partie avec résultats fictifs
        PartieEntity partie = PartieEntity.builder().nbTours(5).build();
        partieRepository.save(partie);

        Integer idPartie = partie.getId();

        // Appel de l'API
        ResponseEntity<Integer[]> response = testRestTemplate.getForEntity(
                "/api/parties/" + idPartie + "/getResultat",
                Integer[].class
        );

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void getestPretSuccess() {
        // Création d'une partie fictive
        PartieEntity partie = PartieEntity.builder().nbTours(5).build();
        partieRepository.save(partie);

        Integer idPartie = partie.getId();

        // Appel de l'API
        ResponseEntity<Boolean> response = testRestTemplate.getForEntity(
                "/api/parties/" + idPartie + "/estPret",
                Boolean.class
        );

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
*/

}

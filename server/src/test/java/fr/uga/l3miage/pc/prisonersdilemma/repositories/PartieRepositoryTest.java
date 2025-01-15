package fr.uga.l3miage.pc.prisonersdilemma.repositories;

import fr.uga.l3miage.pc.prisonersdilemma.domain.models.PartieEntity;
import fr.uga.l3miage.pc.prisonersdilemma.ports.out.IpartieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;



@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
class PartieRepositoryTest {

    @Autowired
    private IpartieRepository partieRepository;

    @Test
    void testFindPartieEntityByIdSuccess() {

        PartieEntity partie = PartieEntity.builder().id(1).nbTours(5).build();
        partieRepository.save(partie);

        // When
        Optional<PartieEntity> foundPartie = partieRepository.findPartieEntityById(1);

        // Then
        assertThat(foundPartie).isPresent();
        assertThat(foundPartie.get().getNbTours()).isEqualTo(5);
    }

    @Test
    void testFindPartieEntityByIdNotFound() {
        Optional<PartieEntity> foundPartie = partieRepository.findPartieEntityById(999);

        assertThat(foundPartie).isEmpty();
    }
}
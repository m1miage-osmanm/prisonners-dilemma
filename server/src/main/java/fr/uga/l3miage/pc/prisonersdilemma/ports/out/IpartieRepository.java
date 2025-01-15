package fr.uga.l3miage.pc.prisonersdilemma.ports.out;

import fr.uga.l3miage.pc.prisonersdilemma.domain.models.PartieEntity;

import java.util.Optional;

public interface IpartieRepository {
    Optional<PartieEntity> findPartieEntityById(Integer id);
    PartieEntity save(PartieEntity partie);
    void deleteAll();
}

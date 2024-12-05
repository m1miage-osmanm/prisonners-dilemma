package fr.uga.l3miage.pc.prisonersdilemma.repositories;

import fr.uga.l3miage.pc.prisonersdilemma.models.PartieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartieRepository extends JpaRepository<PartieEntity,Integer> {
    public Optional<PartieEntity> findPartieEntityById(Integer id);
}

package fr.uga.l3miage.pc.prisonersdilemma.repositories;

import fr.uga.l3miage.pc.prisonersdilemma.models.PartieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartieRepository extends JpaRepository<PartieEntity,Long> {
}

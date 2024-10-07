package fr.uga.l3miage.pc.prisonersdilemma.repositories;

import fr.uga.l3miage.pc.prisonersdilemma.models.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<PlayerEntity, String> {

}

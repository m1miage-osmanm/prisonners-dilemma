package fr.uga.l3miage.pc.prisonersdilemma.repositories;

import fr.uga.l3miage.pc.prisonersdilemma.domain.models.PartieEntity;
import fr.uga.l3miage.pc.prisonersdilemma.ports.out.IpartieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
@RequiredArgsConstructor
public class PartieDatabaseRepo implements IpartieRepository {

    private final PartieRepository repo;
    @Override
    public Optional<PartieEntity> findPartieEntityById(Integer id) {
        return repo.findPartieEntityById(id);
    }
    @Override
    public PartieEntity save(PartieEntity partie) {
        return repo.save(partie);
    }
}

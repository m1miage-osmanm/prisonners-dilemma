package fr.uga.l3miage.pc.prisonersdilemma.repositories;

import fr.uga.l3miage.pc.prisonersdilemma.domain.models.TourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.ports.out.ItourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TourDatabaseRepo implements ItourRepository {
    private final TourRepository repository;
    @Override
    public TourEntity save(TourEntity tour) {
        return repository.save(tour);
    }
}

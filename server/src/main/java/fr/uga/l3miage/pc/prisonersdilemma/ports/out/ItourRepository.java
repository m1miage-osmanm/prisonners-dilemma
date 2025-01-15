package fr.uga.l3miage.pc.prisonersdilemma.ports.out;


import fr.uga.l3miage.pc.prisonersdilemma.domain.models.TourEntity;

public interface ItourRepository {
    TourEntity save(TourEntity tour);

}

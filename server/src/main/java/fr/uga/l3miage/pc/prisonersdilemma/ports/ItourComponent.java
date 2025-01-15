package fr.uga.l3miage.pc.prisonersdilemma.ports;

import fr.uga.l3miage.pc.prisonersdilemma.domain.models.PartieEntity;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.TourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.TypeDecision;

public interface ItourComponent {

    TourEntity creerEtSauvegarderTour(PartieEntity partie);
    void calculerScores(TourEntity tour);
    TourEntity choisirDecisionJ1(TourEntity tour, TypeDecision decision);
    TourEntity choisirDecisionJ2(TourEntity tour, TypeDecision decision);


}

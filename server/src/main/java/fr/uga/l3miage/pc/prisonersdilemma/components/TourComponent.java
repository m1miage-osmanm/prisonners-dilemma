package fr.uga.l3miage.pc.prisonersdilemma.components;

import fr.uga.l3miage.pc.prisonersdilemma.models.PartieEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.TourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.repositories.TourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

;

@Component
@RequiredArgsConstructor
public class TourComponent {
    private static final int T = 5; // Gain si trahison et l'autre coopère
    private static final int D = 0; // Perte si coopère et l'autre trahit
    private static final int C = 3; // Gain si les deux coopèrent
    private static final int P = 1; // Gain si les deux trahissent


    private final TourRepository tourRepository;


    public TourEntity creerEtSauvegarderTour(PartieEntity partie) {
        TourEntity tour= TourEntity.builder().partie(partie).build();
        return tourRepository.save(tour);
    }


    private void calculerScores(TourEntity tour) {
        if ("t".equals(tour.getDecisionJoueur1()) && "c".equals(tour.getDecisionJoueur2())) {
            tour.setScoreJoueur1(T);

            tour.setScoreJoueur2(D);
        } else if ("c".equals(tour.getDecisionJoueur1()) && "t".equals(tour.getDecisionJoueur2())) {
            tour.setScoreJoueur1(D);

            tour.setScoreJoueur2(T);
        } else if ("c".equals(tour.getDecisionJoueur1()) && "c".equals(tour.getDecisionJoueur2())) {

            tour.setScoreJoueur1(C);
            tour.setScoreJoueur2(C);

        } else if ("t".equals(tour.getDecisionJoueur1()) && "t".equals(tour.getDecisionJoueur2())) {

            tour.setScoreJoueur1(P);
            tour.setScoreJoueur1(P);

        }
        tourRepository.save(tour);
    }


}

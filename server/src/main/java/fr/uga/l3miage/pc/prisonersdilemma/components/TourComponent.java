package fr.uga.l3miage.pc.prisonersdilemma.components;

import fr.uga.l3miage.pc.prisonersdilemma.models.PartieEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.TourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.TypeDecision;
import fr.uga.l3miage.pc.prisonersdilemma.repositories.TourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class TourComponent {
    public static final int T = 5; // Gain si trahison et l'autre coopère
    public static final int D = 0; // Perte si coopère et l'autre trahit
    public static final int C = 3; // Gain si les deux coopèrent
    public static final int P = 1; // Gain si les deux trahissent


    private final TourRepository tourRepository;


    public TourEntity creerEtSauvegarderTour(PartieEntity partie) {
        TourEntity tour= TourEntity.builder().partie(partie).build();

        return tourRepository.save(tour);
    }



    public void calculerScores(TourEntity tour) {


        if (tour.getDecisionJoueur1() == TypeDecision.TRAHIR && tour.getDecisionJoueur2() == TypeDecision.COOPERER) {
            tour.setScoreJoueur1(T);
            tour.setScoreJoueur2(D);
        } else if (tour.getDecisionJoueur1() == TypeDecision.COOPERER && tour.getDecisionJoueur2() == TypeDecision.TRAHIR) {
            tour.setScoreJoueur1(D);
            tour.setScoreJoueur2(T);
        } else if (tour.getDecisionJoueur1() == TypeDecision.COOPERER && tour.getDecisionJoueur2() == TypeDecision.COOPERER) {
            tour.setScoreJoueur1(C);
            tour.setScoreJoueur2(C);
        } else if (tour.getDecisionJoueur1() == TypeDecision.TRAHIR && tour.getDecisionJoueur2() == TypeDecision.TRAHIR) {
            tour.setScoreJoueur1(P);
            tour.setScoreJoueur2(P);
        }
    }
    public TourEntity choisirDecisionJ1(TourEntity tour, TypeDecision decision) {
        tour.setDecisionJoueur1(decision);
        return tourRepository.save(tour);
    }
    public TourEntity choisirDecisionJ2(TourEntity tour, TypeDecision decision) {
        tour.setDecisionJoueur2(decision);
        return tourRepository.save(tour);
    }



}

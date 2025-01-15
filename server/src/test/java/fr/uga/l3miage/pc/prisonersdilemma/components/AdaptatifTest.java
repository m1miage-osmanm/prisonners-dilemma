package fr.uga.l3miage.pc.prisonersdilemma.components;



import fr.uga.l3miage.pc.prisonersdilemma.domain.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.PartieEntity;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.TourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.TypeDecision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.strat.Adaptatif;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdaptatifTest {

    private Adaptatif adaptatif;
    private JoueurEntity joueur1;
    private JoueurEntity joueur2;

    @BeforeEach
    void setUp() {
        adaptatif = new Adaptatif();

        joueur1 = new JoueurEntity();
        joueur1.setId(1L);

        joueur2 = new JoueurEntity();
        joueur2.setId(2L);
    }

    @Test
    void testPremiersSixTours_CoopereToujours() {
        List<TourEntity> tours = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            TypeDecision decision = adaptatif.determinerDecision(tours, joueur1);
            assertEquals(TypeDecision.COOPERER, decision);

            // Ajouter un tour simulant une réponse pour les scores
            tours.add(createTour(joueur1, joueur2, 3, 3)); // Simule un tour avec scores égaux
        }
    }

    @Test
    void testToursSuivants_JusquaDix_Trahit() {
        List<TourEntity> tours = createToursForCooperation(joueur1, joueur2, 6, 3);

        for (int i = 0; i < 4; i++) { // Tours 7 à 10
            TypeDecision decision = adaptatif.determinerDecision(tours, joueur1);
            assertEquals(TypeDecision.COOPERER, decision);

            // Ajouter un tour simulant une réponse pour les scores
            tours.add(createTour(joueur1, joueur2, 5, 5)); // Simule un tour avec scores égaux
        }
    }

    @Test
    void testChoixFinal_AvgScoreFavoriseCooperation() {
        List<TourEntity> tours = createToursForCooperation(joueur1, joueur2, 6, 3);
        tours.addAll(createToursForTrahison(joueur1, joueur2, 4, 1)); // Scores faibles pour trahison

        TypeDecision decision = adaptatif.determinerDecision(tours, joueur1);
        assertEquals(TypeDecision.COOPERER, decision); // Favorise la coopération
    }

    @Test
    void testChoixFinal_AvgScoreFavoriseTrahison() {
        List<TourEntity> tours = createToursForCooperation(joueur1, joueur2, 6, 1); // Scores faibles pour coopération
        tours.addAll(createToursForTrahison(joueur1, joueur2, 4, 5));

        TypeDecision decision = adaptatif.determinerDecision(tours, joueur1);
        assertEquals(TypeDecision.COOPERER, decision); // Favorise la trahison
    }

    // Méthodes utilitaires

    private TourEntity createTour(JoueurEntity joueur1, JoueurEntity joueur2, int scoreJoueur1, int scoreJoueur2) {
        PartieEntity partie = new PartieEntity();
        partie.setJoueur1(joueur1);
        partie.setJoueur2(joueur2);

        TourEntity tour = new TourEntity();
        tour.setPartie(partie);
        tour.setScoreJoueur1(scoreJoueur1);
        tour.setScoreJoueur2(scoreJoueur2);

        return tour;
    }

    private List<TourEntity> createToursForCooperation(JoueurEntity joueur1, JoueurEntity joueur2, int nbTours, int score) {
        List<TourEntity> tours = new ArrayList<>();
        for (int i = 0; i < nbTours; i++) {
            tours.add(createTour(joueur1, joueur2, score, score));
        }
        return tours;
    }

    private List<TourEntity> createToursForTrahison(JoueurEntity joueur1, JoueurEntity joueur2, int nbTours, int score) {
        List<TourEntity> tours = new ArrayList<>();
        for (int i = 0; i < nbTours; i++) {
            tours.add(createTour(joueur1, joueur2, score, score));
        }
        return tours;
    }
}

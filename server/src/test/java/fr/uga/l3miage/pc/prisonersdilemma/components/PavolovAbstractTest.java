package fr.uga.l3miage.pc.prisonersdilemma.components;



import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.PartieEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.TourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.TypeDecision;
import fr.uga.l3miage.pc.prisonersdilemma.strat.PavolovAbstract;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PavolovAbstractTest {

    private PavolovAbstract pavlovStrategy;
    private JoueurEntity joueur1;
    private JoueurEntity joueur2;

    @BeforeEach
    void setUp() {
        // Implémentation concrète minimale pour tester PavlovAbstract
        pavlovStrategy = new PavolovAbstract("TestPavlov", "Test Description") {
            @Override
            protected TypeDecision decisionAvecScorePositif(TypeDecision decisionPrecedente) {
                return decisionPrecedente; // Imiter la décision précédente
            }

            @Override
            protected TypeDecision decisionAvecScoreNegatif() {
                return TypeDecision.TRAHIR; // Toujours trahir après un score négatif
            }
        };

        joueur1 = new JoueurEntity();
        joueur1.setId(1L);

        joueur2 = new JoueurEntity();
        joueur2.setId(2L);
    }

    @Test
    void testPremierTour() {
        List<TourEntity> tours = new ArrayList<>();
        TypeDecision decision = pavlovStrategy.determinerDecision(tours, joueur1);
        assertEquals(TypeDecision.COOPERER, decision);
    }

    @Test
    void testScorePositif() {
        List<TourEntity> tours = new ArrayList<>();
        tours.add(createTour(joueur1, joueur2, TypeDecision.COOPERER, TypeDecision.TRAHIR, 5, 0));

        TypeDecision decision = pavlovStrategy.determinerDecision(tours, joueur1);
        assertEquals(TypeDecision.COOPERER, decision); // Score positif, imite la décision précédente
    }

    @Test
    void testScoreNegatif() {
        List<TourEntity> tours = new ArrayList<>();
        tours.add(createTour(joueur1, joueur2, TypeDecision.COOPERER, TypeDecision.COOPERER, 1, 3));

        TypeDecision decision = pavlovStrategy.determinerDecision(tours, joueur1);
        assertEquals(TypeDecision.TRAHIR, decision); // Score négatif, trahit
    }

    @Test
    void testDecisionAdversaireScorePositif() {
        List<TourEntity> tours = new ArrayList<>();
        tours.add(createTour(joueur1, joueur2, TypeDecision.TRAHIR, TypeDecision.COOPERER, 3, 5));

        TypeDecision decision = pavlovStrategy.determinerDecision(tours, joueur2);
        assertEquals(TypeDecision.COOPERER, decision); // Score positif, imite la décision précédente
    }

    @Test
    void testDecisionAdversaireScoreNegatif() {
        List<TourEntity> tours = new ArrayList<>();
        tours.add(createTour(joueur1, joueur2, TypeDecision.TRAHIR, TypeDecision.COOPERER, 0, 1));

        TypeDecision decision = pavlovStrategy.determinerDecision(tours, joueur2);
        assertEquals(TypeDecision.TRAHIR, decision); // Score négatif, trahit
    }

    // Méthode utilitaire pour créer un tour simulé
    private TourEntity createTour(JoueurEntity joueur1, JoueurEntity joueur2,
                                  TypeDecision decisionJoueur1, TypeDecision decisionJoueur2,
                                  int scoreJoueur1, int scoreJoueur2) {
        PartieEntity partie = new PartieEntity();
        partie.setJoueur1(joueur1);
        partie.setJoueur2(joueur2);

        TourEntity tour = new TourEntity();
        tour.setPartie(partie);
        tour.setDecisionJoueur1(decisionJoueur1);
        tour.setDecisionJoueur2(decisionJoueur2);
        tour.setScoreJoueur1(scoreJoueur1);
        tour.setScoreJoueur2(scoreJoueur2);

        return tour;
    }
}

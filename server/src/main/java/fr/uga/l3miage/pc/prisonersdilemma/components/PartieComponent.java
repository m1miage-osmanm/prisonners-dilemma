package fr.uga.l3miage.pc.prisonersdilemma.components;

import fr.uga.l3miage.pc.prisonersdilemma.models.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.PartieEntity;
import fr.uga.l3miage.pc.prisonersdilemma.repositories.PartieRepository;
import fr.uga.l3miage.pc.prisonersdilemma.services.StrategieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PartieComponent {

    private final PartieRepository partieRepository;
    private final TourComponent tourComponent;
    private final StrategieService strategieService;
    @Transactional
    public PartieEntity creerPartie2joueurs(JoueurEntity joueur1, Integer nbTours) {
        PartieEntity partie = PartieEntity.builder()

                .joueur1(joueur1)
                .nbTours(nbTours)
                .tours(new ArrayList<>())
                .estPret(false)
                .build();
        return partieRepository.save(partie);

    }

    @Transactional
    public PartieEntity rejoindrePartie(JoueurEntity joueur2, Integer idPartie) {
        PartieEntity partie = partieRepository.findPartieEntityById(idPartie).orElse(null);
        if (partie == null) {
            throw new IllegalArgumentException("Partie with ID " + idPartie + " does not exist.");
        }
        partie.setJoueur2(joueur2);
        partie.setEstPret(true);
        return partieRepository.save(partie);

    }





    @Transactional
    public TourEntity jouerUnTour(Integer idPartie, Optional<TypeDecision> decision1, Optional<TypeDecision> decision2) {
        // Récupérer la partie
        PartieEntity partie = partieRepository.findPartieEntityById(idPartie)
                .orElseThrow(() -> new IllegalArgumentException("Partie with ID " + idPartie + " does not exist."));

        // Vérifier si la partie est prête
        if (!partie.isEstPret()) {
            throw new IllegalStateException("La partie n'est pas prête. Les deux joueurs doivent être présents.");
        }

        // Vérifier si tous les tours ont été joués
        if (partie.getTours().size() >= partie.getNbTours()) {
            throw new IllegalStateException("Tous les tours de la partie ont déjà été joués.");
        }

        // Créer un nouveau tour et sauvegarder
        TourEntity tour = tourComponent.creerEtSauvegarderTour(partie);
        TypeDecision decisionJ1=obtenirDecision(decision1,partie.getTypeStrategieJoueur1(),partie.getTours(),partie.getJoueur1());
        TypeDecision decisionJ2=obtenirDecision(decision1,partie.getTypeStrategieJoueur1(),partie.getTours(),partie.getJoueur1());

        tourComponent.choisirDecisionJ1(tour, decisionJ1);
        tourComponent.choisirDecisionJ2(tour, decisionJ2);
        tourComponent.calculerScores(tour);

        // Ajouter le tour à la partie
        partie.getTours().add(tour);
        partieRepository.save(partie);

        System.out.println("Tour joué : Joueur 1 a choisi " + decision1 + ", Joueur 2 a choisi " + decision2);

        return tour;
    }



    private TypeDecision obtenirDecision(Optional<TypeDecision> decisionOptionnelle, String typeStrategie, List<TourEntity> tours, JoueurEntity joueur) {
        if (decisionOptionnelle.isEmpty()) {

            Strategie strategie = strategieService.getStrategie(typeStrategie);
            return strategie.determinerDecision(tours, joueur);
        } else {

            return decisionOptionnelle.get();
        }
    }

    public PartieEntity joueurQuitte(Integer idPartie,Long idJoueur,String typeStrategie) {
        PartieEntity partie = partieRepository.findPartieEntityById(idPartie)
                .orElseThrow(() -> new IllegalArgumentException("Partie with ID " + idPartie + " does not exist."));
        if (partie.getJoueur1().getId().equals(idJoueur)) {

            partie.setTypeStrategieJoueur1(typeStrategie);}
        else if (partie.getJoueur2().getId().equals(idJoueur)) {

            partie.setTypeStrategieJoueur2(typeStrategie);}
        else{
            throw new RuntimeException("joueur non dans la partie");
        }

        return partieRepository.save(partie);

    }
}

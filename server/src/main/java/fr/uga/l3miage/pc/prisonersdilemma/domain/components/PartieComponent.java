package fr.uga.l3miage.pc.prisonersdilemma.domain.components;

import fr.uga.l3miage.pc.prisonersdilemma.domain.models.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.uga.l3miage.pc.prisonersdilemma.ports.out.IpartieRepository;
import fr.uga.l3miage.pc.prisonersdilemma.domain.services.StrategieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PartieComponent {

    private final IpartieRepository ipartieRepository;
    private final TourComponent tourComponent;
    private final StrategieService strategieService;
    private String noExist= " does not exist.";
    private String id="Partie with ID ";
    @Transactional
    public PartieEntity creerPartie2joueurs(JoueurEntity joueur1, Integer nbTours) {
        PartieEntity partie = PartieEntity.builder()

                .joueur1(joueur1)
                .nbTours(nbTours)
                .tours(new ArrayList<>())
                .estPret(false)
                .build();
        return ipartieRepository.save(partie);

    }


    @Transactional
    public PartieEntity rejoindrePartie(JoueurEntity joueur2, Integer idPartie) {
        PartieEntity partie = ipartieRepository.findPartieEntityById(idPartie).orElse(null);
        if (partie == null) {
            throw new IllegalArgumentException(id + idPartie + noExist);
        }
        partie.setJoueur2(joueur2);
        partie.setEstPret(true);
        return ipartieRepository.save(partie);

    }





    @Transactional
    public Integer[] jouerUnTour(Integer idPartie, Optional<TypeDecision> decision1, Optional<TypeDecision> decision2) {
        // Récupérer la partie
        PartieEntity partie = ipartieRepository.findPartieEntityById(idPartie)
                .orElseThrow(() -> new IllegalArgumentException(id + idPartie + noExist));

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
        ipartieRepository.save(partie);


        return new Integer[]{tour.getScoreJoueur1(), tour.getScoreJoueur2()};
    }



    protected TypeDecision obtenirDecision(Optional<TypeDecision> decisionOptionnelle, String typeStrategie, List<TourEntity> tours, JoueurEntity joueur) {
        if (decisionOptionnelle.isEmpty()) {

            Strategie strategie = strategieService.getStrategie(typeStrategie);
            return strategie.determinerDecision(tours, joueur);
        } else {

            return decisionOptionnelle.get();
        }
    }

    public PartieEntity joueurQuitte(Integer idPartie,Long idJoueur,String typeStrategie) {

        PartieEntity partie = ipartieRepository.findPartieEntityById(idPartie)
                .orElseThrow(() -> new IllegalArgumentException(id + idPartie + noExist));
        if (partie.getJoueur1().getId().equals(idJoueur)) {

            partie.setTypeStrategieJoueur1(typeStrategie);}
        else if (partie.getJoueur2().getId().equals(idJoueur)) {

            partie.setTypeStrategieJoueur2(typeStrategie);}
        else{
            throw new RuntimeException("joueur non dans la partie");
        }

        return ipartieRepository.save(partie);

    }
    public Integer[] scorePartie(Integer idPartie) {
        PartieEntity partie = ipartieRepository.findPartieEntityById(idPartie)
                .orElseThrow(() -> new IllegalArgumentException(id + idPartie + noExist));
        Integer[] scores = new Integer[2];
        int scoreTotJ1=0;
        int scoreTotJ2=0;
        for (TourEntity tour : partie.getTours()) {
            scoreTotJ1 = scoreTotJ1+tour.getScoreJoueur1();
            scoreTotJ2 = scoreTotJ2+tour.getScoreJoueur2();
        }
        scores[0]=scoreTotJ1;
        scores[1]=scoreTotJ2;
        return scores;
    }

}

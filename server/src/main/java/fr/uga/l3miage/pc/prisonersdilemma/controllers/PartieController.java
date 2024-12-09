package fr.uga.l3miage.pc.prisonersdilemma.controllers;


import fr.uga.l3miage.pc.prisonersdilemma.endpoints.PartieEndpoint;
import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.PartieEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.TourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.TypeDecision;
import fr.uga.l3miage.pc.prisonersdilemma.repositories.PartieRepository;
import fr.uga.l3miage.pc.prisonersdilemma.services.PartieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class PartieController implements PartieEndpoint {

    @Autowired
    private PartieService partieService;
    @Autowired
    private PartieRepository partieRepository;



    @Override
    public ResponseEntity<Integer> creerPartie2joueurs(String nameJ1, Integer nbTours) {
        JoueurEntity joueur=JoueurEntity.builder().username(nameJ1).build();
        PartieEntity partie=partieService.creerPartie2joueurs(joueur,nbTours);
        return new ResponseEntity<>(partie.getId(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Integer> rejoindrePartie(String nameJ2, Integer idPartie) {
        JoueurEntity joueur=JoueurEntity.builder().username(nameJ2).build();
        PartieEntity partie=partieService.rejoindrePartie(joueur,idPartie);
        return new ResponseEntity<>(partie.getId(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Integer[]> jouerUnTour(Integer idPartie, Optional<String> decision1, Optional<String> decision2) {
        try {

            Optional<TypeDecision> decision1Converted = decision1.map(dec -> TypeDecision.valueOf(dec.toUpperCase()));
            Optional<TypeDecision> decision2Converted = decision2.map(dec -> TypeDecision.valueOf(dec.toUpperCase()));


            Integer[] h=partieService.jouerUnTour(idPartie, decision1Converted, decision2Converted);


            return new ResponseEntity<>(h,HttpStatus.OK);
        } catch (IllegalArgumentException e) {

            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Integer> joueurQuitte(Integer idPartie, Long idJoueur, String typeStrategie) {
        PartieEntity partie=partieService.joueurQuitte(idPartie,idJoueur,typeStrategie);
        return new ResponseEntity<>(partie.getId(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Integer[]> getResultatPartie(Integer idPartie) {
        return new ResponseEntity<>(partieService.getScorePartie(idPartie),HttpStatus.OK);
    }
    @Override
    public ResponseEntity<Boolean> getestPret( Integer idPartie) {
        Optional<PartieEntity> partie = partieRepository.findPartieEntityById(idPartie);

        if (partie.isPresent()) {
            System.out.println(partie.get().isEstPret());
            return new ResponseEntity<>(partie.get().isEstPret(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Override
    public ResponseEntity<Boolean> checkDecisions( Integer idPartie) {
        PartieEntity partie = partieRepository.findPartieEntityById(idPartie)
                .orElseThrow(() -> new IllegalArgumentException("Partie with ID " + idPartie + " does not exist."));

        List<TourEntity> tours = partie.getTours();
        if (!tours.isEmpty()) {
            TourEntity dernierTour = tours.get(tours.size() - 1);
            boolean bothDecisionsPresent = dernierTour.getDecisionJoueur1() != null &&
                    dernierTour.getDecisionJoueur2() != null;
            return ResponseEntity.ok(bothDecisionsPresent);
        }

        return ResponseEntity.ok(false);
    }
}

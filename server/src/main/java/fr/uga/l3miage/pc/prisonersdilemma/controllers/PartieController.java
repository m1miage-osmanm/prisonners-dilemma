package fr.uga.l3miage.pc.prisonersdilemma.controllers;


import fr.uga.l3miage.pc.prisonersdilemma.endpoints.PartieEndpoint;
import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.PartieEntity;
import fr.uga.l3miage.pc.prisonersdilemma.services.PartieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PartieController implements PartieEndpoint {

    @Autowired
    private PartieService partieService;



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


}

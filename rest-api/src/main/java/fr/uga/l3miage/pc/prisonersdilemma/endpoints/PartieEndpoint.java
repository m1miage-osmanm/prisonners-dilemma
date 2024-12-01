package fr.uga.l3miage.pc.prisonersdilemma.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/parties")
public interface PartieEndpoint {
    @PostMapping("/create/{nameJ1}/{nbTours}")
    public ResponseEntity<Integer> creerPartie2joueurs(@PathVariable String nameJ1, @PathVariable Integer nbTours);

    @PostMapping("/join/{nameJ2}/{idPartie}")
    public ResponseEntity<Integer> rejoindrePartie(@PathVariable String nameJ2, @PathVariable Integer idPartie);


    @PostMapping("/jouerTour/{idPartie}")
    public ResponseEntity<String> jouerUnTour(
            @PathVariable Integer idPartie,
            @RequestParam Optional<String> decision1,
            @RequestParam Optional<String> decision2
    );

    @PostMapping("/quitter/{idPartie}/{idJoueur}")
    public ResponseEntity<Integer> joueurQuitte(
            @PathVariable Integer idPartie,
            @PathVariable Long idJoueur,
            @RequestParam String typeStrategie
    );

}

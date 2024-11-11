package fr.uga.l3miage.pc.prisonersdilemma.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/parties")
public interface PartieEndpoint {
    @PostMapping("/create/{nameJ1}/{nbTours}")
    public ResponseEntity<Integer> creerPartie2joueurs(@PathVariable String nameJ1, @PathVariable Integer nbTours);

    @PostMapping("/join/{nameJ2}/{idPartie}")
    public ResponseEntity<Integer> rejoindrePartie(@PathVariable String nameJ2, @PathVariable Integer idPartie);
}

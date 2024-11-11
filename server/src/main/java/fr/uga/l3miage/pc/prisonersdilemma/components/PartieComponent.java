package fr.uga.l3miage.pc.prisonersdilemma.components;

import fr.uga.l3miage.pc.prisonersdilemma.models.*;
import fr.uga.l3miage.pc.prisonersdilemma.repositories.*;

import javax.transaction.Transactional;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.PartieEntity;
import fr.uga.l3miage.pc.prisonersdilemma.repositories.PartieRepository;
import fr.uga.l3miage.pc.prisonersdilemma.repositories.TourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PartieComponent {

    private final PartieRepository partieRepository;
    private final TourRepository tourRepository;

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

    /*public void ajouterTour(TourEntity tour) {
        partieEntity.getTours().add(tour);
    }*/




}

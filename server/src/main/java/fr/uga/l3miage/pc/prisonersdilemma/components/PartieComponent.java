package fr.uga.l3miage.pc.prisonersdilemma.components;

import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.PartieEntity;
import fr.uga.l3miage.pc.prisonersdilemma.repositories.PartieRepository;
import fr.uga.l3miage.pc.prisonersdilemma.repositories.TourRepository;

import javax.transaction.Transactional;
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
    public PartieEntity creerPartie(JoueurEntity joueur1, JoueurEntity joueur2, Integer nbTours) {
        PartieEntity partie = PartieEntity.builder()
                .joueur1(joueur1)
                .joueur2(joueur2)
                .nbTours(nbTours)
                .tours(new ArrayList<>())
                .build();
        return partieRepository.save(partie);

    }





}

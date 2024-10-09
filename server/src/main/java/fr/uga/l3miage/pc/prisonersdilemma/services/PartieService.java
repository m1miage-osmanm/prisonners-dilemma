package fr.uga.l3miage.pc.prisonersdilemma.services;


import fr.uga.l3miage.pc.prisonersdilemma.components.PartieComponent;
import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.PartieEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PartieService {
private final PartieComponent partieComponent;

public PartieEntity creerPartie(JoueurEntity joueur1, JoueurEntity joueur2, Integer nbTours) {
    return partieComponent.creerPartie(joueur1, joueur2, nbTours);
}





}

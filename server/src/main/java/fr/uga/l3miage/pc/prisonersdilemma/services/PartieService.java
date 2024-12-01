package fr.uga.l3miage.pc.prisonersdilemma.services;


import fr.uga.l3miage.pc.prisonersdilemma.components.PartieComponent;
import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.PartieEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.TourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.TypeDecision;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartieService {
        private final PartieComponent partieComponent;

        public PartieEntity creerPartie2joueurs(JoueurEntity joueur1,
                                        Integer nbTours) {

            return partieComponent.creerPartie2joueurs(joueur1,nbTours);
        }
        public PartieEntity rejoindrePartie(JoueurEntity joueur2, Integer idPartie){
            return partieComponent.rejoindrePartie(joueur2,idPartie);
        }


    public void jouerUnTour(Integer idPartie, Optional<TypeDecision> decision1Converted, Optional<TypeDecision> decision2Converted) {
    }

    public PartieEntity joueurQuitte(Integer idPartie, Long idJoueur, String typeStrategie) {
            return null;
    }
}

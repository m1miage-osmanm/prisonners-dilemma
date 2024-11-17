package fr.uga.l3miage.pc.prisonersdilemma.services;


import fr.uga.l3miage.pc.prisonersdilemma.components.PartieComponent;
import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.PartieEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.TourEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

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


        /*
        public void ajouterTours (TourEntity tour) {
            PartieEntity partie = tour.getPartie();
            if (partie.getTours().size() == partie.getNbTours()) {
                throw new IllegalStateException("nombre de tours max atteint"); //avant de personnaliser les excep
            }

            partieComponent.ajouterTour(tour);
        }*/




}

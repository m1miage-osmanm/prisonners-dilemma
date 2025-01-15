package fr.uga.l3miage.pc.prisonersdilemma.domain.services;


import fr.uga.l3miage.pc.prisonersdilemma.domain.components.PartieComponent;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.PartieEntity;

import fr.uga.l3miage.pc.prisonersdilemma.domain.models.TypeDecision;
import fr.uga.l3miage.pc.prisonersdilemma.ports.in.IpartieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartieService implements IpartieService {
        private final PartieComponent partieComponent;

        public PartieEntity creerPartie2joueurs(JoueurEntity joueur1,
                                        Integer nbTours) {

            return partieComponent.creerPartie2joueurs(joueur1,nbTours);
        }
        public PartieEntity rejoindrePartie(JoueurEntity joueur2, Integer idPartie){
            return partieComponent.rejoindrePartie(joueur2,idPartie);
        }


    public Integer[] jouerUnTour(Integer idPartie, Optional<TypeDecision> decision1Converted, Optional<TypeDecision> decision2Converted) {
            return partieComponent.jouerUnTour(idPartie,decision1Converted,decision2Converted);
    }

    public PartieEntity joueurQuitte(Integer idPartie, Long idJoueur, String typeStrategie) {
            return partieComponent.joueurQuitte(idPartie,idJoueur,typeStrategie);
    }
    public Integer[] getScorePartie(Integer idPartie){
            return partieComponent.scorePartie(idPartie);
        }
}

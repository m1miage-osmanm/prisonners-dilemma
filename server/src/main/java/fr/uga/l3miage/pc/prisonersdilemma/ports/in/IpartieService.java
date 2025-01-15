package fr.uga.l3miage.pc.prisonersdilemma.ports.in;

import fr.uga.l3miage.pc.prisonersdilemma.domain.models.JoueurEntity;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.PartieEntity;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.TypeDecision;

import java.util.Optional;

public interface IpartieService {
    PartieEntity creerPartie2joueurs(JoueurEntity joueur1,
                                     Integer nbTours);

    PartieEntity rejoindrePartie(JoueurEntity joueur2, Integer idPartie);

    Integer[] jouerUnTour(Integer idPartie, Optional<TypeDecision> decision1Converted, Optional<TypeDecision> decision2Converted);

    PartieEntity joueurQuitte(Integer idPartie, Long idJoueur, String typeStrategie);

    Integer[] getScorePartie(Integer idPartie);
}

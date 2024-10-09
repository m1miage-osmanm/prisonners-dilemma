package fr.uga.l3miage.pc.prisonersdilemma.models;

import java.util.List;
import java.util.Random;

public class DonnantDonnantRandom extends StrategieEntity {

    private final Random random = new Random();

    public DonnantDonnantRandom() {
    super("DonnantDonnantRandom", "Jouer comme le dernier coup de l'adversaire, mais jouer parfois un coup au\n" +
            "hasard");
  }

  @Override
  public String determinerDecision(List<TourEntity> tours,
                                   JoueurEntity joueur,
                                   JoueurEntity adversaire) {
        TourEntity dernierTour = tours.get(tours.size()-1);

      if (tours.isEmpty()) {
          return "c";   // Premier tour : coopérer
      }

      boolean coupAleatoire = random.nextInt(2) == 0;

      if (coupAleatoire) {
          return random.nextBoolean()? "c" : "t";
      }
      if (joueur.equals(dernierTour.getPartie().getJoueur1())) {
          return dernierTour.getDecisionJoueur2(); // Adversaire est joueur2
      } else {
          return dernierTour.getDecisionJoueur1(); // Adversaire est joueur1
      }
  }


}

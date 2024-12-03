package fr.uga.l3miage.pc.prisonersdilemma.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

    //@Entity
    @Getter
    @Setter
    public abstract class Strategie{
        //@Id
        private int numeroStrat;
        protected String name;

        protected String description;

        public Strategie(String name, String description) {
            this.name = name;
            this.description = description;
        }

       /* public StrategieEntity() {

        }*/

        /**
          @param tours Liste des tours précédents.
          @param joueur Le joueur actuel.

          @return 'c' pour coopérer, 't' pour trahir.
         */
        public abstract TypeDecision determinerDecision(List<TourEntity> tours,
                                                  JoueurEntity joueur
                                                  );
    }










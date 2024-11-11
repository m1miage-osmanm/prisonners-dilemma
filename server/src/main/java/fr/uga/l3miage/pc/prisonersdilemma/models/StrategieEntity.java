package fr.uga.l3miage.pc.prisonersdilemma.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

    //@Entity
    @Getter
    @Setter
    public abstract class StrategieEntity{
        //@Id
        private int numeroStrat;
        protected String name;
        protected String description;

        public StrategieEntity(String name, String description) {
            this.name = name;
            this.description = description;
        }

       /* public StrategieEntity() {

        }*/

        /**
          @param tours Liste des tours précédents.
          @param joueur Le joueur actuel.
          @param adversaire L'adversaire contre lequel il joue.
          @return 'c' pour coopérer, 't' pour trahir.
         */
        public abstract String determinerDecision(List<TourEntity> tours,
                                                  JoueurEntity joueur,
                                                  JoueurEntity adversaire);
    }










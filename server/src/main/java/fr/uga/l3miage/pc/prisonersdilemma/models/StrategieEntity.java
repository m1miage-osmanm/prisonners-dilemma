package fr.uga.l3miage.pc.prisonersdilemma.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


    @Getter
    @Setter
    public abstract class StrategieEntity{

        protected String name;
        protected String description;

        public StrategieEntity(String name, String description) {
            this.name = name;
            this.description = description;
        }

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










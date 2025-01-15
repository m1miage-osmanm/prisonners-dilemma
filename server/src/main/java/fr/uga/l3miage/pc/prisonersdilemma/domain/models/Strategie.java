package fr.uga.l3miage.pc.prisonersdilemma.domain.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

    //@Entity
    @Getter
    @Setter
    public abstract class Strategie {

        private int numeroStrat;
        protected String name;

        protected String description;

        protected Strategie(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public abstract TypeDecision determinerDecision(List<TourEntity> tours, JoueurEntity joueur);
    }









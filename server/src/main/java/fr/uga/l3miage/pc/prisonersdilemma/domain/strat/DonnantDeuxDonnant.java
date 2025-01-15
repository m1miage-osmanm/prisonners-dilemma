package fr.uga.l3miage.pc.prisonersdilemma.domain.strat;

import org.springframework.stereotype.Component;

@Component
public class DonnantDeuxDonnant extends DonnantDeuxDonnantAbstract {

    public DonnantDeuxDonnant() {
        super(
                "DonnantDeuxDonnant",
                "Comme donnant-donnant sauf que l'adversaire doit faire le même choix deux fois de suite avant la réciprocité."
        );
    }
}

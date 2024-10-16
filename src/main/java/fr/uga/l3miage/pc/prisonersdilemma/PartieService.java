package fr.uga.l3miage.pc.prisonersdilemma;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PartieService {

    private Partie partie;

    public void initPartie(String nomJoueur1,  String nomJoueur2,Strategie strategie1, Strategie strategie2, Integer nb) {
        Joueur joueur1 = new Joueur(nomJoueur1);
        Joueur joueur2 = new Joueur(nomJoueur2);
        this.partie = new Partie(joueur1, joueur2,nb,strategie1,strategie2);
        System.out.println("Partie initialisée entre " + nomJoueur1 + " et " + nomJoueur2);
    }


    public void jouerUnTour() {
        if (partie == null) {
            throw new IllegalStateException("La partie n'a pas été initialisée.");
        }




        String decisionJoueur1 = partie.getStrategieJoueur1().determinerDecision(partie.getTours(), partie.getJoueur1(), partie.getJoueur2());
        String decisionJoueur2 = partie.getStrategieJoueur2().determinerDecision(partie.getTours(), partie.getJoueur2(), partie.getJoueur1());
        //String decisionJoueur2 = "t";

       Tours tours=new Tours(partie,decisionJoueur1,decisionJoueur2);


        partie.ajouterTour(tours);

        System.out.println("Tour joué : Joueur 1 a choisi " + decisionJoueur1 + ", Joueur 2 a choisi " + decisionJoueur2);
    }


    public void jouerTousLesTours() {
        for (int i = 0; i < partie.getNombreTours(); i++) {
            jouerUnTour();
        }

        System.out.println("La partie est terminée !");
    }


   /* public List<Tours> getTours() {
        if (partie == null) {
            throw new IllegalStateException("La partie n'est pas initialisée");
        }
        return partie.getTours();
    }*/

    public void afficherResultats() {
        if (partie == null) {
            throw new IllegalStateException("La partie n'est pas initialisée");
        }
        System.out.println("Résultats des tours :");
        for (Tours tour : partie.getTours()) {
            System.out.println("Tour: Joueur 1 - " + tour.getDecisionJoueur1() + ", Joueur 2 - " + tour.getDecisionJoueur2());
        }
    }
}


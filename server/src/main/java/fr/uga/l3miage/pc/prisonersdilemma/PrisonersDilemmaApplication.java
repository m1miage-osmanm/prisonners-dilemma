package fr.uga.l3miage.pc.prisonersdilemma;

import fr.uga.l3miage.pc.prisonersdilemma.services.PartieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PrisonersDilemmaApplication implements CommandLineRunner {

	@Autowired
	private PartieService partieService;

	public static void main(String[] args) {
		SpringApplication.run(PrisonersDilemmaApplication.class, args);
		System.out.println("Hello world!");

	}

	@Override
	public void run(String... args) throws Exception {

		/*
		partieService.initPartie("Alice", "Bob", new SondeurRepentant(),new Pavlov(), 10);

		// Jouer tous les tours
		partieService.jouerTousLesTours();

		// Afficher les résultats de la partie
		partieService.afficherResultats();*/
	}
}

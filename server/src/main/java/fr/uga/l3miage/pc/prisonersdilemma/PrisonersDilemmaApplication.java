package fr.uga.l3miage.pc.prisonersdilemma;

import fr.uga.l3miage.pc.prisonersdilemma.services.PartieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PrisonersDilemmaApplication  {

	public static void main(String[] args) {
		SpringApplication.run(PrisonersDilemmaApplication.class, args);
		System.out.println("Hello world!");

	}


}

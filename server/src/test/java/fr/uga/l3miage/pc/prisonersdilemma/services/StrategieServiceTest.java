package fr.uga.l3miage.pc.prisonersdilemma.services;

import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.models.TypeDecision;
import fr.uga.l3miage.pc.prisonersdilemma.strat.DonnantDonnant;
import fr.uga.l3miage.pc.prisonersdilemma.strat.DonnantDeuxDonnant;
import fr.uga.l3miage.pc.prisonersdilemma.strat.DonnantDeuxDonnantRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
class StrategieServiceTest {

    @Autowired
    private StrategieService strategieService;

    @Test
    void getStrategie_shouldReturnCorrectStrategie() {
        // Vérifier que la stratégie "DonnantDonnant" est bien retournée
        Strategie donnantDonnant = strategieService.getStrategie("donnantDonnant");
        assertNotNull(donnantDonnant, "La stratégie DonnantDonnant ne doit pas être nulle");
        assertTrue(donnantDonnant instanceof DonnantDonnant, "La stratégie doit être de type DonnantDonnant");

        // Vérifier que la stratégie "DonnantDeuxDonnant" est bien retournée
        Strategie donnantDeuxDonnant = strategieService.getStrategie("donnantDeuxDonnant");
        assertNotNull(donnantDeuxDonnant, "La stratégie DonnantDeuxDonnant ne doit pas être nulle");
        assertTrue(donnantDeuxDonnant instanceof DonnantDeuxDonnant, "La stratégie doit être de type DonnantDeuxDonnant");

        // Vérifier que la stratégie "DonnantDeuxDonnantRandom" est bien retournée
        Strategie donnantDeuxDonnantRandom = strategieService.getStrategie("donnantDeuxDonnantRandom");
        assertNotNull(donnantDeuxDonnantRandom, "La stratégie DonnantDeuxDonnantRandom ne doit pas être nulle");
        assertTrue(donnantDeuxDonnantRandom instanceof DonnantDeuxDonnantRandom, "La stratégie doit être de type DonnantDeuxDonnantRandom");
    }

    @Test
    void getStrategie_shouldThrowExceptionForInvalidName() {
        // Vérifier qu'une exception est levée si le nom de la stratégie n'existe pas
        Exception exception = assertThrows(Exception.class, () -> {
            strategieService.getStrategie("invalidStrategie");
        });

        String expectedMessage = "No bean named 'invalidStrategie' available";
        assertTrue(exception.getMessage().contains(expectedMessage), "Le message d'exception doit indiquer qu'aucun bean n'existe avec ce nom");
    }

    @Test
    public void testToutesLesStrategiesDepuisService() {
        List<String> nomsStrategies = List.of("donnantDeuxDonnant", "pavlov", "pavlovRandom","adaptatif","aleatoire","donnantDeuxDonnantRandom","donnantDeuxDonnant"
                ,"donnantDonnant","donnantDonnantRandom","donnantDonnant","donnantDonnantRandom","graduel","pacificateurNaif"
                ,"rancunier","sondeurNaif","sondeurRepentant","toujoursCooperer","toujoursTrahir","vraiPacificateur");

        for (String nom : nomsStrategies) {
            Strategie strategie = strategieService.getStrategie(nom);
            assertNotNull(strategie);

            TypeDecision decision = strategie.determinerDecision(Collections.emptyList(), null);
            assertNotNull(decision);

        }
    }

}

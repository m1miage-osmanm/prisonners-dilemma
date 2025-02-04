package fr.uga.l3miage.pc.prisonersdilemma.services;

import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class StrategieService {

    @Autowired
    private ApplicationContext applicationContext;

    public Strategie getStrategie(String nomStrategie) {
        return (Strategie) applicationContext.getBean(nomStrategie);
    }
}


package fr.uga.l3miage.pc.prisonersdilemma.strat;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomAdapter implements RandomGenerator {
    private final Random random;

    public RandomAdapter() {
        this.random = new Random();
    }

    @Override
    public int nextInt(int bound) {
        return random.nextInt(bound);
    }


    public boolean nextBoolean() {
        return random.nextBoolean();
    }

    public double nextDouble() {
        return random.nextDouble();
    }
}


package org.happyhour.scoreboard.algo;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.function.Function;

@Component
public class ScoreAlgorithm implements Function<int[], Integer> {
    @Override
    public Integer apply(int[] ints) {
        return Arrays.stream(ints).sum();
    }
}

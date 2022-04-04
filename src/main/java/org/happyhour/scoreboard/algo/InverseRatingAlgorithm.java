package org.happyhour.scoreboard.algo;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class InverseRatingAlgorithm implements Function<int[], Integer> {
    final static int K = 5;

    public static void main(String[] args) {
        InverseRatingAlgorithm algo = new InverseRatingAlgorithm();
        System.out.println(algo.apply(new int[]{2, 1, -1, 1, -1, -1}));
        System.out.println(algo.apply(new int[]{-1, -1, -1, -1, -1, -1}));
        System.out.println(algo.apply(new int[]{-1, -1, -1, 2, 2, 2}));
        System.out.println(algo.apply(new int[]{2, 1, 1, 1, 1, 1}));
        System.out.println(algo.apply(new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2, 2}));
    }

    @Override
    public Integer apply(int[] ints) {
        double rating = 0;
        int index = 0;
        for (int x : ints) {
            for (int j = 0; j < x; j++) {
                index++;
                if (index <= K) {
                    rating += 2d / (1 + K);
                } else {
                    rating += 1d / index;
                }
            }
            for (int j = 0; j > x; j--) {
                index++;
            }
        }
        return (int)Math.round(rating * 1e3);
    }
}

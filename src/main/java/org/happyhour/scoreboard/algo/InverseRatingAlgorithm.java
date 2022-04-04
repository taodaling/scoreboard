package org.happyhour.scoreboard.algo;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class InverseRatingAlgorithm implements Function<int[], Integer> {
    final static int K = 5;

    public static void main(String[] args) {
        InverseRatingAlgorithm algo = new InverseRatingAlgorithm();
        int[] data = new int[10000];
        for (int i = 0; i < data.length; i++) {
            data[i] = i % 2 < 1 ? 1 : -1;
        }
        System.out.println(algo.apply(data));
    }

    @Override
    public Integer apply(int[] ints) {
        if (ints.length == 0) {
            return 0;
        }
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
                rating -= 1d / index;
            }
        }
        return (int) Math.round(rating * 1e3) + 1200;
    }
}

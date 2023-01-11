package org.happyhour.scoreboard.algo;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class InverseRatingAlgorithm implements Function<int[], Integer> {
    final static int K = 5;

    public static void main(String[] args) {
        InverseRatingAlgorithm algo = new InverseRatingAlgorithm();
        int[] data = new int[100];
        for (int i = 0; i < data.length; i++) {
            data[i] = i < 5 || i % 5 != 0 ? 1 : -1;
        }
        System.out.println(algo.apply(data));
    }

    @Override
    public Integer apply(int[] ints) {
        if (ints.length == 0) {
            return 0;
        }
        int offset = 5;
        double rating = 0;
        int index = 0;
        for (int x : ints) {
            for (int j = 0; j < x; j++) {
                index++;
                rating += 1d / (offset + index);
            }
            for (int j = 0; j > x; j--) {
                index++;
                rating -= 1d / (offset + index);
            }
        }
        return (int) Math.round(rating * 2e3) + 1200;
    }
}

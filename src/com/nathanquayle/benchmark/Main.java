package com.nathanquayle.benchmark;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // setup
        int arraySize = 100_000;

        List<Integer> Arr = new ArrayList<>();

        for (int i = 0; i < arraySize; i++) {
            Arr.add(i);
        }

        for (int i = 10; i <= arraySize; i *= 10) {
            List<Integer> testArr = Arr.subList(0, i - 1);

            System.out.println("Testing size: " + i);

            lastIndexTest(testArr);
            reverseTest(testArr);
            shuffleTest(testArr);
            sortTest(testArr);
        }

    }

    private static void lastIndexTest(List<Integer> arr) {
        Collections.shuffle(arr);

        long start = System.nanoTime();

        arr.get(arr.size() - 1);

        long finish = System.nanoTime();
        long totalTime = finish - start;

        System.out.println("Last index test took: " + totalTime + "ns | " +  format((double) totalTime / 1000000)+ "ms");
    }


    private static void reverseTest(List<Integer> arr) {
        Collections.shuffle(arr);

        long start = System.nanoTime();

        Collections.reverse(arr);

        long finish = System.nanoTime();
        long totalTime = finish - start;

        System.out.println("Reverse test took: " + totalTime + "ns | " +  format((double) totalTime / 1000000) + "ms");
    }

    private static void shuffleTest(List<Integer> arr) {
        long start = System.nanoTime();

        Collections.shuffle(arr);

        long finish = System.nanoTime();
        long totalTime = finish - start;

        System.out.println("Shuffle test took: " + totalTime + "ns | " +  format((double) totalTime / 1000000) + "ms");
    }

    private static void sortTest(List<Integer> arr) {
        long start = System.nanoTime();

        Collections.sort(arr);

        long finish = System.nanoTime();
        long totalTime = finish - start;

        System.out.println("Sort test took: " + totalTime + "ns | " + format((double) totalTime / 1000000) + "ms");
    }

    private static String format(double value) {
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(8);
        df.setMinimumIntegerDigits(1);
        return df.format(value);
    }
}

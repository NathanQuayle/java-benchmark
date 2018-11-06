package com.nathanquayle.benchmark;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // setup
        int arraySize = 100_000;

        List<Integer> testArr = new ArrayList<>();

        // Populate with random numbers
        for (int i = 0; i < arraySize; i++) {
            testArr.add((int) (Math.random() * 100) );
        }

        for (int i = 10; i <= arraySize; i *= 10) {
            List<Integer> t = testArr.subList(0, i);
            Collections.shuffle(t);

            System.out.println("Running " + i + " array size tests");

            // Add tests here
            test( "last index", () -> t.get(t.size() - 1));
            test( "reverse", () -> Collections.reverse(t));
            test( "shuffle", () -> Collections.shuffle(t));
            test( "sort", () -> Collections.sort(t));
        }

    }

    private static void test(String testName, Runnable method) {
        long start = System.nanoTime();

        method.run();

        long finish = System.nanoTime();
        long totalTime = finish - start;

        System.out.println(testName + " test took: " + totalTime + "ns | " +  format((double) totalTime / 1000000)+ "ms");
    }

    private static String format(double value) {
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(8);
        df.setMinimumIntegerDigits(1);
        return df.format(value);
    }
}

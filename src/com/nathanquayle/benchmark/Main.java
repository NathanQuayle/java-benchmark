package com.nathanquayle.benchmark;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class Main {
    private static HashMap<String, ArrayList<String>> results = new HashMap<>();
    private static HashMap<String, ArrayList<Integer>> testTimes = new HashMap<>();
    private static boolean isWarmedUp = false;
    private static int ARRAY_SIZE;
    private static int NUM_OF_LOOPS;
    private static int STEP_SIZE;

    public static void main(String[] args) {
        List<Integer> testArr = new ArrayList<>();
        if (!isWarmedUp) {
            // Warmup setup
            ARRAY_SIZE = 100;
            STEP_SIZE = 50;
            NUM_OF_LOOPS = 1;
            testArr = setupTestArr();
            // 15,000 looks like the 'sweet spot' first test time isn't skewed.
            for (int i = 1; i <= 15_000; i++) {
                System.out.println("Warming up.. " + i + "/10000");
                runTests(testArr);
            }
            isWarmedUp = true;

            // Test setup
            ARRAY_SIZE = 10_000;
            STEP_SIZE = 5_000;
            NUM_OF_LOOPS = 100;
            testArr = setupTestArr();
        }

        Instant startTime = Instant.now();

        runTests(testArr);

        displayTimeTaken(startTime);
        displayResults();
    }

    private static List<Integer> setupTestArr() {
        List<Integer> testArr = new ArrayList<>();

        // Populate with random numbers
        for (int i = 0; i < ARRAY_SIZE; i++) {
            testArr.add((int) (Math.random() * 100) );
        }
        return testArr;
    }

    private static void runTests(List<Integer> testArr) {
        for (int i = STEP_SIZE; i <= testArr.size(); i += STEP_SIZE) {
            List<Integer> testArrSub = testArr.subList(0, i);

            if(isWarmedUp) {
                addTestTime(testArrSub.size());
                System.out.println("Running " + i + " array size tests");
            }

            // Add tests here
//            test(testArrSub,"Last Index", () -> testArrSub.get(testArrSub.size() - 1));
//            test(testArrSub,"Reverse", () -> Collections.reverse(testArrSub));
//            test(testArrSub,"Shuffle", () -> Collections.shuffle(testArrSub));
            test(testArrSub,"Sort", () -> Collections.sort(testArrSub));
            test(testArrSub,"My Sort", () -> Utils.quickSort(testArrSub));
//            test(testArrSub,"My Shuffle", () -> Utils.shuffle(testArrSub));
//            test(testArrSub,"My Reverse", () -> Utils.reverse(testArrSub));
        }
    }

    private static void test(List<Integer> testArr, String testName, Runnable method) {
        List<Long> times = new ArrayList<>();

        for (int i = 0; i < NUM_OF_LOOPS ; i++) {
            Collections.shuffle(testArr);
            long start = System.nanoTime();
            method.run();

            long finish = System.nanoTime();
            long totalTime = finish - start;
            times.add(totalTime);
        }

        if(isWarmedUp) {
            addResults(testName, calculateAverage(times));
       }
    }

    private static String calculateAverage(List<Long> arr) {
        long sum = 0;
        for (long time : arr) {
            sum += time;
        }
        return format((double) (sum / arr.size()) / 1000000);
    }

    private static void addResults(String key, String value) {
        results.putIfAbsent(key, new ArrayList<>());
        // Add value as ms.
        results.get(key).add(value);
    }

    private static void addTestTime(int value) {
        String key = "Array Size";
        testTimes.putIfAbsent(key, new ArrayList<>());
        // Add value as ms.
        testTimes.get(key).add(value);
    }

    private static void displayResults() {
        testTimes.forEach((k, v) -> {
            System.out.print(k + ",");
            v.forEach((res) -> System.out.print(res + ","));
            System.out.println();
        });

        results.forEach((k, v) -> {
            System.out.print(k + ",");
            v.forEach((res) -> System.out.print(res + ","));
            System.out.println();
        });
    }

    private static void displayTimeTaken(Instant start) {
        Duration d =  Duration.between(start, Instant.now());
        System.out.println();
        System.out.println("Tests ran in: " + d.toMinutesPart() + " minutes and " + d.toSecondsPart() + " seconds.");
    }

    private static String format(double value) {
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(2);
        df.setMinimumIntegerDigits(1);
        return df.format(value);
    }

}

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
        // setup
        ARRAY_SIZE = 10_000;
        STEP_SIZE = 10_000;
        NUM_OF_LOOPS = 100;

        if (!isWarmedUp) {
            for (int i = 1; i <= 10_000; i++) {
                System.out.println("Warming up.. " + i + "/10000");
                runTests(Arrays.asList(1,2,3,4,5));
            }
            isWarmedUp = true;
        }
        setupTests();
        displayResults();
    }

    private static void setupTests() {
        List<Integer> testArr = new ArrayList<>();

        // Populate with random numbers
        for (int i = 0; i < ARRAY_SIZE; i++) {
            testArr.add((int) (Math.random() * 100) );
        }
        Instant startTime = Instant.now();

        runTests(testArr);

        displayTimeTaken(startTime);
    }

    private static void runTests(List<Integer> testArr) {
        for (int i = STEP_SIZE; i <= testArr.size(); i += STEP_SIZE) {
            List<Integer> testArrSub = testArr.subList(0, i);

            addTestTime("array size", testArrSub.size());
            System.out.println("Running " + i + " array size tests");

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

    private static void addTestTime(String key, int value) {
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

    public static void displayTimeTaken(Instant start) {
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

package com.nathanquayle.benchmark;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {
    private static List<Integer> testArr;

    @BeforeEach
    void setup() {
        testArr = Arrays.asList(1, 2, 3, 4, 5, 6);
    }

    @Test
    void shuffle() {
        Utils.shuffle(testArr);
        assertNotEquals(Arrays.asList(1, 2, 3, 4, 5, 6), testArr);
    }

    @Test
    void reverse() {
        Utils.reverse(testArr);
        assertEquals(Arrays.asList(6, 5, 4, 3, 2, 1), testArr);
    }

    @Test
    void quickSort() {
        Collections.shuffle(testArr);
        testArr = Utils.quickSort(testArr);
        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6), testArr);
    }

}
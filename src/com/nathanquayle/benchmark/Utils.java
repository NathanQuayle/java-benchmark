package com.nathanquayle.benchmark;

import java.util.ArrayList;
import java.util.List;

class Utils {

    static void shuffle(List<Integer> arr) {
        for(int i = 0; i < arr.size(); i++) {
            int randomInt = (int) (Math.random() * arr.size());

            swap(arr, i, randomInt);
        }
    }

    static void reverse(List<Integer> arr) {
        for (int i = 0; i < arr.size() / 2 ; i++) {
            swap(arr, i, arr.size() - 1 - i);
        }
    }

    static List<Integer> quickSort(List<Integer> arr) {
        if(arr.size() <= 1) return arr;
        int pivot = arr.get(0);
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        for (int i = 1; i < arr.size(); i++) {
            int val = arr.get(i);
            if(val > pivot) {
                right.add(val);
            } else {
                left.add(val);
            }
        }

        List<Integer> result = new ArrayList<>(quickSort(left));
        result.add(pivot);
        result.addAll(quickSort(right));
        return result;
    }

    private static void swap(List<Integer> arr, int currentIndex, int newIndex) {
        int temp = arr.get(currentIndex);
        arr.set(currentIndex, arr.get(newIndex));
        arr.set(newIndex, temp);
    }

}

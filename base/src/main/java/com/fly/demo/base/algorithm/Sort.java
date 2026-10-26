package com.fly.demo.base.algorithm;

public class Sort {

    public void bubbleSort(int[] arr) {
        for (int i = 1; i < arr.length - 1; i++) {
            boolean finished = true;
            for (int j = 0; j < arr.length - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    exchange(arr, j, j + 1);
                    finished = false;
                }
            }
            if (finished) {
                break;
            }
        }
    }

    private void exchange(int[] arr, int a, int b) {
        arr[a] = arr[a] ^ arr[b];
        arr[b] = arr[a] ^ arr[b];
        arr[a] = arr[a] ^ arr[b];
    }
}

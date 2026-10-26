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

    // 选择排序
    public void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            exchange(arr, i, minIndex);
        }
    }

    private void exchange(int[] arr, int a, int b) {
        if (a == b) {
            return;
        }
        arr[a] = arr[a] ^ arr[b];
        arr[b] = arr[a] ^ arr[b];
        arr[a] = arr[a] ^ arr[b];
    }
}

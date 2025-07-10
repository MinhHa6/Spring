package com.Devmaster.DevmasterLesson2;

import java.util.Arrays;

public class BlueSort {
    public void sort(int arr[]) {
        System.out.println("Sap xep giai thua Bluesort:");
        int n = arr.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
    }

    public static class TightCouplingService {
        private BlueSort blueSort = new BlueSort();

        public TightCouplingService() {}

        public TightCouplingService(BlueSort blueSort) {
            this.blueSort = blueSort;
        }

        public void complexBussinessSortn(int arr[]) {
            blueSort.sort(arr);
            Arrays.stream(arr).forEach(System.out::println);
        }

        public static void main(String[] args) {
            TightCouplingService tightCouplingService = new TightCouplingService();
            tightCouplingService.complexBussinessSortn(new int[]{11, 21, 13, 42, 15});
        }
    }
}

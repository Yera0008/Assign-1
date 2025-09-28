import java.util.Arrays;

public class Mergesort {
    private static final int CUTOFF = 10;

    public static void insertionSort(int[] a, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= left) {
                Metric.incComparisons();
                if (a[j] > key) {
                    a[j + 1] = a[j];
                    j--;
                } else {
                    break;
                }
            }
            a[j + 1] = key;
        }
    }

    public static void msort(int[] a, int[] aux, int left, int right) {
        Metric.enterRecursion();

        if (right - left + 1 <= CUTOFF) {
            insertionSort(a, left, right);
            Metric.exitRecursion();
            return;
        }

        int mid = (left + right) / 2;
        msort(a, aux, left, mid);
        msort(a, aux, mid + 1, right);

        Metric.incComparisons();
        if (a[mid] <= a[mid + 1]) {
            Metric.exitRecursion();
            return;
        }

        merge(a, aux, left, mid, right);
        Metric.exitRecursion();
    }

    public static void merge(int[] a, int[] aux, int left, int mid, int right) {
        for (int k = left; k <= right; k++) {
            aux[k] = a[k];
            Metric.incAllocations();
        }

        int i = left;
        int j = mid + 1;

        for (int k = left; k <= right; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > right) {
                a[k] = aux[i++];
            } else {
                Metric.incComparisons();
                if (aux[i] <= aux[j]) {
                    a[k] = aux[i++];
                } else {
                    a[k] = aux[j++];
                }
            }
        }
    }

    public static void sort(int[] a) {
        Metric.reset();
        Metric.startTimer();

        int[] aux = new int[a.length];
        Metric.incAllocations(a.length);

        msort(a, aux, 0, a.length - 1);

        Metric.stopTimer();
    }

    public static void main(String[] args) {
        int[] arr = {64, 34, 25, 12, 22, 11, 90, 5, 77, 30, 15, 42, 8, 19, 50};

        System.out.println("Original array: " + Arrays.toString(arr));

        sort(arr);

        System.out.println("Sorted array: " + Arrays.toString(arr));
        System.out.println("MergeSort Metrics:");
        System.out.println("Time: " + Metric.getDuration() / 1000 + " microseconds");
        System.out.println("Max Recursion Depth: " + Metric.getMaxRecursionDepth());
        System.out.println("Comparisons: " + Metric.getComparisons());
        System.out.println("Allocations: " + Metric.getAllocations());
    }
}
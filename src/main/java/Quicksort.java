import java.util.Arrays;
import java.util.Random;

public class Quicksort {
    private static final Random RAND = new Random();

    public static void sort(int[] arr) {
        Metric.reset();
        Metric.startTimer();
        quickSort(arr, 0, arr.length - 1);
        Metric.stopTimer();
    }

    public static void quickSort(int[] arr, int low, int high) {
        Metric.enterRecursion();

        while (low < high) {
            int randomIndex = low + RAND.nextInt(high - low + 1);
            swap(arr, randomIndex, high);

            int pivotIndex = partition(arr, low, high);

            if (pivotIndex - low < high - pivotIndex) {
                quickSort(arr, low, pivotIndex - 1);
                low = pivotIndex + 1;
            } else {
                quickSort(arr, pivotIndex + 1, high);
                high = pivotIndex - 1;
            }
        }

        Metric.exitRecursion();
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            Metric.incComparisons();
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        Metric.incAllocations(3);
    }

    public static void main(String[] args) {
        int[] array = {64, 34, 25, 12, 22, 11, 90, 5, 77, 30, 15, 42, 8, 19, 50};

        System.out.println("Original array:");
        System.out.println(Arrays.toString(array));

        sort(array);

        System.out.println("Sorted array:");
        System.out.println(Arrays.toString(array));

        System.out.println("QuickSort Metrics:");
        System.out.println("Time: " + Metric.getDuration() / 1000 + " microseconds");
        System.out.println("Max Recursion Depth: " + Metric.getMaxRecursionDepth());
        System.out.println("Comparisons: " + Metric.getComparisons());
        System.out.println("Allocations: " + Metric.getAllocations());
    }
}
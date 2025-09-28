import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Deterministicselect {
    private ArrayList<Integer> medians = new ArrayList<>();

    public int select(int[] arr, int k) {
        if (arr == null || arr.length == 0) return Integer.MIN_VALUE;
        if (k > arr.length || k < 1) throw new IllegalArgumentException("Invalid k");

        Metric.reset();
        Metric.startTimer();
        int result = select(arr, k, 0, arr.length - 1);
        Metric.stopTimer();

        return result;
    }

    private int select(int[] arr, int k, int start, int end) {
        Metric.enterRecursion();

        if (start == end) {
            Metric.exitRecursion();
            return arr[start];
        }

        int pivot = findPivot(arr, start, end);

        for (int i = start; i <= end; i++) {
            Metric.incComparisons();
            if (arr[i] == pivot) {
                swap(arr, i, end);
                break;
            }
        }

        int pivotIndex = partition(arr, start, end);
        int order = pivotIndex - start + 1;

        int result;
        if (k == order) {
            result = arr[pivotIndex];
        } else if (k < order) {
            result = select(arr, k, start, pivotIndex - 1);
        } else {
            result = select(arr, k - order, pivotIndex + 1, end);
        }

        Metric.exitRecursion();
        return result;
    }

    private int findPivot(int[] arr, int start, int end) {
        ArrayList<Integer> medians = new ArrayList<>();
        int length = end - start + 1;

        if (length <= 5) {
            Metric.incAllocations(length);
            return findMedian(Arrays.copyOfRange(arr, start, end + 1), length);
        }

        int i;
        for (i = start; end + 1 - i > 5; i += 5) {
            Metric.incAllocations(5);
            int median = findMedian(Arrays.copyOfRange(arr, i, i + 5), 5);
            medians.add(median);
        }

        if (i <= end) {
            int remaining = end + 1 - i;
            Metric.incAllocations(remaining);
            medians.add(findMedian(Arrays.copyOfRange(arr, i, end + 1), remaining));
        }

        int[] medianArr = toArray(medians);
        Metric.incAllocations(medianArr.length);
        return findPivot(medianArr, 0, medianArr.length - 1);
    }

    private int[] toArray(ArrayList<Integer> list) {
        int[] arr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) arr[i] = list.get(i);
        return arr;
    }

    private int findMedian(int[] arr, int length) {
        Arrays.sort(arr);
        Metric.incComparisons(length * (int)Math.log(length));
        return arr[length / 2];
    }

    private int partition(int[] arr, int start, int end) {
        int b = start - 1;
        for (int i = start; i <= end; i++) {
            Metric.incComparisons();
            if (arr[i] <= arr[end]) {
                b++;
                swap(arr, i, b);
            }
        }
        return b;
    }

    private void swap(int[] arr, int index1, int index2) {
        int inter = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = inter;
        Metric.incAllocations(3);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Deterministicselect selector = new Deterministicselect();

        System.out.print("Number of elements: ");
        int n = scanner.nextInt();

        int[] arr = new int[n];
        System.out.println("Enter " + n + " elements:");
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }

        System.out.print("Enter k: ");
        int k = scanner.nextInt();

        try {
            int result = selector.select(arr, k);
            System.out.println(k + "-th smallest element: " + result);

            System.out.println("Select Metrics:");
            System.out.println("Time: " + Metric.getDuration() / 1000 + " microseconds");
            System.out.println("Max Recursion Depth: " + Metric.getMaxRecursionDepth());
            System.out.println("Comparisons: " + Metric.getComparisons());
            System.out.println("Allocations: " + Metric.getAllocations());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
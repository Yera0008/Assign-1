import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.Random;

class AlgorithmTest {

    @Test
    void testQuickSortDepth() {
        int[] array = generateRandomArray(10000);
        Quicksort.sort(array);

        int depth = Metric.getMaxRecursionDepth();
        int expectedMaxDepth = 2 * (int)(Math.log(array.length) / Math.log(2)) + 10;

        assertTrue(depth <= expectedMaxDepth,
                "QuickSort depth should be O(log n). Actual: " + depth + ", Expected max: " + expectedMaxDepth);
    }

    @Test
    void testDeterministicSelectVsSort() {
        Random rand = new Random();
        for (int trial = 0; trial < 100; trial++) {
            int[] array = generateRandomArray(1000);
            int k = rand.nextInt(array.length) + 1;

            int[] copy = array.clone();
            int expected = Arrays.stream(copy).sorted().toArray()[k-1];
            int actual = new Deterministicselect().select(array, k);

            assertEquals(expected, actual, "Select result should match Arrays.sort");
        }
    }

    @Test
    void testClosestPairValidation() {
        // Генерируем небольшие наборы точек для проверки против brute-force
        for (int n = 10; n <= 100; n += 10) {
            Point[] points = generateRandomPoints(n);

            double fastResult = Closestpairofpoints.closest(points, points.length);
            double bruteForceResult = bruteForceClosest(points);

            assertEquals(bruteForceResult, fastResult, 1e-10,
                    "Closest pair should match brute-force for n=" + n);
        }
    }

    private int[] generateRandomArray(int size) {
        Random rand = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(10000);
        }
        return array;
    }

    private Point[] generateRandomPoints(int n) {
        Random rand = new Random();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(rand.nextInt(1000), rand.nextInt(1000));
        }
        return points;
    }

    private double bruteForceClosest(Point[] points) {
        double min = Double.MAX_VALUE;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double dist = distance(points[i], points[j]);
                if (dist < min) min = dist;
            }
        }
        return min;
    }

    private double distance(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }
}

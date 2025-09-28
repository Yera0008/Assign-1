import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

public class Closestpairofpoints {

    public static double closest(Point[] P, int n) {
        Metric.reset();
        Metric.startTimer();

        Point[] Px = Arrays.copyOf(P, n);
        Metric.incAllocations(n);
        Arrays.sort(Px, (p1, p2) -> {
            Metric.incComparisons();
            return p1.x - p2.x;
        });

        Point[] Py = Arrays.copyOf(P, n);
        Metric.incAllocations(n);
        Arrays.sort(Py, (p1, p2) -> {
            Metric.incComparisons();
            return p1.y - p2.y;
        });

        double result = closestUtil(Px, Py, n);
        Metric.stopTimer();
        return result;
    }

    private static double closestUtil(Point[] Px, Point[] Py, int n) {
        Metric.enterRecursion();

        if (n <= 3) {
            double result = bruteForce(Px, n);
            Metric.exitRecursion();
            return result;
        }

        int mid = n / 2;
        Point midPoint = Px[mid];

        List<Point> PylList = new ArrayList<>();
        List<Point> PyrList = new ArrayList<>();
        for (Point p : Py) {
            if (p.x <= midPoint.x) {
                PylList.add(p);
            } else {
                PyrList.add(p);
            }
        }

        Point[] Pyl = PylList.toArray(new Point[0]);
        Metric.incAllocations(Pyl.length);
        Point[] Pyr = PyrList.toArray(new Point[0]);
        Metric.incAllocations(Pyr.length);

        double dl = closestUtil(Arrays.copyOfRange(Px, 0, mid), Pyl, mid);
        double dr = closestUtil(Arrays.copyOfRange(Px, mid, n), Pyr, n - mid);

        double d = Math.min(dl, dr);

        List<Point> strip = new ArrayList<>();
        for (Point p : Py) {
            if (Math.abs(p.x - midPoint.x) < d) {
                strip.add(p);
            }
        }

        double result = stripClosest(strip.toArray(new Point[0]), strip.size(), d);
        Metric.exitRecursion();
        return result;
    }


    private static double bruteForce(Point[] P, int n) {
        double min = Double.MAX_VALUE;
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                Metric.incComparisons();
                double dist = distance(P[i], P[j]);
                if (dist < min) {
                    min = dist;
                }
            }
        }
        return min;
    }

    private static double stripClosest(Point[] strip, int size, double d) {
        double min = d;

        for (int i = 0; i < size; ++i) {
            for (int j = i + 1; j < size && j <= i + 7; ++j) {
                if (Math.abs(strip[j].y - strip[i].y) >= min) break;

                Metric.incComparisons();
                double dist = distance(strip[i], strip[j]);
                if (dist < min) {
                    min = dist;
                }
            }
        }
        return min;
    }

    private static double distance(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    public static void main(String[] args) {
        Point[] P = {
                new Point(2, 3), new Point(12, 30), new Point(40, 50),
                new Point(5, 1), new Point(12, 10), new Point(3, 4)
        };
        int n = P.length;

        double distance = closest(P, n);

        System.out.println("Smallest distance is " + distance);
        System.out.println("Closest Pair Metrics:");
        System.out.println("Time: " + Metric.getDuration() / 1000 + " microseconds");
        System.out.println("Max Recursion Depth: " + Metric.getMaxRecursionDepth());
        System.out.println("Comparisons: " + Metric.getComparisons());
        System.out.println("Allocations: " + Metric.getAllocations());
    }
}

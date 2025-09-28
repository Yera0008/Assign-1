public class Metric {
    private static final ThreadLocal<Metric> INSTANCE = ThreadLocal.withInitial(Metric::new);

    private int recursionDepth = 0;
    private int maxRecursionDepth = 0;
    private int comparisons = 0;
    private int allocations = 0;
    private long startTime;
    private long duration;

    public static Metric get() {
        return INSTANCE.get();
    }

    public static void reset() {
        Metric metric = get();
        metric.recursionDepth = 0;
        metric.maxRecursionDepth = 0;
        metric.comparisons = 0;
        metric.allocations = 0;
        metric.duration = 0;
    }

    public static void startTimer() {
        get().startTime = System.nanoTime();
    }

    public static void stopTimer() {
        get().duration = System.nanoTime() - get().startTime;
    }

    public static void enterRecursion() {
        Metric metric = get();
        metric.recursionDepth++;
        if (metric.recursionDepth > metric.maxRecursionDepth) {
            metric.maxRecursionDepth = metric.recursionDepth;
        }
    }

    public static void exitRecursion() {
        get().recursionDepth--;
    }

    public static void incComparisons() {
        get().comparisons++;
    }

    public static void incComparisons(int count) {
        get().comparisons += count;
    }

    public static void incAllocations() {
        get().allocations++;
    }

    public static void incAllocations(int count) {
        get().allocations += count;
    }

    public static int getMaxRecursionDepth() { return get().maxRecursionDepth; }
    public static int getComparisons() { return get().comparisons; }
    public static int getAllocations() { return get().allocations; }
    public static long getDuration() { return get().duration; }
}
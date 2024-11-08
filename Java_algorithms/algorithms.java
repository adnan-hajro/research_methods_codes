import java.util.Arrays;
import java.util.Random;

public class SortingComparison {

    // Bubble Sort Implementation
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    // Quick Sort Implementation
    public static int[] quickSort(int[] arr) {
        if (arr.length <= 1) return arr;

        int pivot = arr[arr.length / 2];
        int[] left = Arrays.stream(arr).filter(x -> x < pivot).toArray();
        int[] middle = Arrays.stream(arr).filter(x -> x == pivot).toArray();
        int[] right = Arrays.stream(arr).filter(x -> x > pivot).toArray();

        int[] result = new int[left.length + middle.length + right.length];
        System.arraycopy(quickSort(left), 0, result, 0, left.length);
        System.arraycopy(middle, 0, result, left.length, middle.length);
        System.arraycopy(quickSort(right), 0, result, left.length + middle.length, right.length);

        return result;
    }

    // Measure execution time and memory usage
    public static void measureSortingAlgorithm(String algorithm, int[] data) {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc(); // Request garbage collection for accurate memory measurement

        long startTime = System.nanoTime();
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();

        if (algorithm.equals("bubble")) {
            bubbleSort(data.clone());
        } else if (algorithm.equals("quick")) {
            quickSort(data.clone());
        }

        long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
        long endTime = System.nanoTime();

        double timeTaken = (endTime - startTime) / 1e6; // Convert to milliseconds
        double memoryUsed = (memoryAfter - memoryBefore) / 1024.0; // Convert to KB

        System.out.printf("Execution time: %.3f ms\n\n", timeTaken);
        //System.out.printf("Memory used: %.2f KB\n", memoryUsed);
    }

    // Test different scenarios and sizes
    public static void testScenarios() {
        int[] sizes = {100, 1000, 10000, 50000, 100000, 1000000};
        String[] conditions = {"sorted", "random", "reverse"};
        Random rand = new Random();

        for (int size : sizes) {
            for (String condition : conditions) {
                int[] data;
                switch (condition) {
                    case "sorted":
                        data = new int[size];
                        for (int i = 0; i < size; i++) data[i] = i;
                        break;
                    case "random":
                        data = rand.ints(size, 0, size * 10).toArray();
                        break;
                    case "reverse":
                        data = new int[size];
                        for (int i = 0; i < size; i++) data[i] = size - i;
                        break;
                    default:
                        data = new int[0];
                }

                System.out.printf("Testing Bubble Sort with %s data of size %d\n", condition, size);
                measureSortingAlgorithm("bubble", data);

                System.out.printf("Testing Quick Sort with %s data of size %d\n", condition, size);
                measureSortingAlgorithm("quick", data);
            }
        }
    }

    public static void main(String[] args) {
        testScenarios();
    }
}

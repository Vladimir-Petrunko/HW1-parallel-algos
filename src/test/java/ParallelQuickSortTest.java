import org.example.tasks.ParallelQuickSortTask;
import org.example.utils.IntegerArrayGenerator;
import org.example.utils.Quicksort;
import org.example.utils.StringArrayGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class ParallelQuickSortTest {
    private ForkJoinPool pool;

    @BeforeEach
    public void init() {
        pool = new ForkJoinPool();
    }

    @Test
    public void testEmpty() {
        Assertions.assertDoesNotThrow(() -> parallelQuickSort(new Integer[0]));
        Assertions.assertDoesNotThrow(() -> parallelQuickSort(new String[0]));

        Assertions.assertDoesNotThrow(() -> sequentialQuickSort(new Integer[0]));
        Assertions.assertDoesNotThrow(() -> sequentialQuickSort(new String[0]));
    }

    @Test
    public void testIntegerArray() {
        Integer[] array = IntegerArrayGenerator.generateArray(100);
        Assertions.assertArrayEquals(
                parallelQuickSort(array),
                sequentialQuickSort(array)
        );
    }

    @Test
    public void testStringArray() {
        String[] array = StringArrayGenerator.generateArray(50, 10);
        Assertions.assertArrayEquals(
                parallelQuickSort(array),
                sequentialQuickSort(array)
        );
    }

    @Test
    public void stressTest() {
        Random r = new Random(67);
        Integer[] rising = new Integer[1000000];
        Integer[] falling = new Integer[1000000];
        Integer[] equal = new Integer[1000000];
        Integer[] random = new Integer[1000000];
        for (int i = 0; i < 1000000; i++) {
            rising[i] = i;
            falling[i] = 1000000 - i;
            equal[i] = 67_67_67_67;
            random[i] = r.nextInt(1000000);
        }
        double risingT = measure(() -> parallelQuickSort(rising));
        double fallingT = measure(() -> parallelQuickSort(falling));
        double equalT = measure(() -> parallelQuickSort(equal));
        double randomT = measure(() -> parallelQuickSort(random));
        List<Double> times = new ArrayList<>(List.of(risingT, fallingT, equalT, randomT));
        System.out.println("Rising: " + risingT);
        System.out.println("Falling: " + fallingT);
        System.out.println("Equal: " + fallingT);
        System.out.println("Random: " + randomT);
        Collections.sort(times);
        Assertions.assertTrue(times.get(3) < 500);
    }

    private <T extends Comparable<T>> T[] parallelQuickSort(T[] array) {
        T[] clone = Arrays.copyOf(array, array.length);
        ParallelQuickSortTask<T> task = new ParallelQuickSortTask<>(clone, 0, clone.length - 1);
        pool.invoke(task);
        return clone;
    }

    private <T extends Comparable<T>> T[] sequentialQuickSort(T[] array) {
        T[] clone = Arrays.copyOf(array, array.length);
        Quicksort.quickSort(clone, 0, clone.length - 1);
        return clone;
    }

    private double measure(Runnable r) {
        double start = System.currentTimeMillis();
        r.run();
        return System.currentTimeMillis() - start;
    }
}

import org.example.tasks.ParallelQuickSortTask;
import org.example.utils.IntegerArrayGenerator;
import org.example.utils.Quicksort;
import org.example.utils.StringArrayGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
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
}

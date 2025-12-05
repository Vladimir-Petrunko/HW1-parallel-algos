package org.example;

import org.example.tasks.ParallelQuickSortTask;
import org.example.utils.Benchmarker;
import org.example.utils.IntegerArrayGenerator;
import org.example.utils.Quicksort;

import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;

public class Main {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        Benchmarker<Integer> benchmarker = new Benchmarker<>();
        try (ForkJoinPool pool = new ForkJoinPool(4)) {
            benchmarker.benchmark(
                    new String[]{"Parallel quicksort", "Sequential quicksort"},
                    () -> IntegerArrayGenerator.generateArray(100_000_000),
                    new Consumer[]{
                            (arr) -> pool.invoke(new ParallelQuickSortTask<>((Integer[]) arr, 0, ((Integer[]) arr).length - 1)),
                            (arr) -> Quicksort.quickSort((Integer[]) arr, 0, ((Integer[]) arr).length - 1)
                    },
                    2,
                    5,
                    0.95,
                    1
            );
        }
    }
}
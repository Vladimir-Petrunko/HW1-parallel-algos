package org.example.tasks;

import org.example.utils.Constants;
import org.example.utils.Quicksort;

import java.util.concurrent.RecursiveAction;

public class ParallelQuickSortTask<T extends Comparable<T>> extends RecursiveAction {
    private final T[] array;
    private final int low, high;

    public ParallelQuickSortTask(T[] array, int low, int high) {
        this.array = array;
        this.low = low;
        this.high = high;
    }

    @Override
    protected void compute() {
        if (high - low <= Constants.THRESHOLD) {
            Quicksort.quickSort(array, low, high);
        } else {
            int[] pivots = Quicksort.partition(array, low, high);
            ParallelQuickSortTask<T> leftTask = new ParallelQuickSortTask<>(array, low, pivots[0] - 1);
            ParallelQuickSortTask<T> rightTask = new ParallelQuickSortTask<>(array, pivots[1] + 1, high);
            invokeAll(leftTask, rightTask);
        }
    }
}

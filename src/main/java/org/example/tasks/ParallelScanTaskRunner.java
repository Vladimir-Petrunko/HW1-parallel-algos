package org.example.tasks;

import org.example.utils.ScanResult;

import java.util.concurrent.ForkJoinPool;
import java.util.function.BiFunction;

public class ParallelScanTaskRunner<T> {
    public void run(int n, BiFunction<T, T, T> f) {
        ParallelScanTask<T> upTask = new ParallelScanTask<>(ParallelScanTask.Phase.UP, 0, n, 0, f, null);
        ParallelScanTask<T> downTask = new ParallelScanTask<>(ParallelScanTask.Phase.DOWN, 0, n, 0, f, null);

        ForkJoinPool pool = new ForkJoinPool(10);
        pool.invoke(upTask);
        ScanResult.tree[0] = null;
        pool.invoke(downTask);
    }
}

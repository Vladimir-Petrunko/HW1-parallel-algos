package org.example.tasks;

import org.example.utils.Constants;
import org.example.utils.ScanResult;

import java.util.concurrent.RecursiveTask;
import java.util.function.BiFunction;

@SuppressWarnings("unchecked")
public class ParallelScanTask<T>  extends RecursiveTask<T> {
    public enum Phase {
        UP,
        DOWN
    }

    public static Object[] array;
    private final Phase phase;
    private final int l, r, index;
    private final BiFunction<T, T, T> f;

    public ParallelScanTask(Phase phase, int l, int r, int index, BiFunction<T, T, T> f, T res) {
        this.l = l;
        this.r = r;
        this.f = f;
        this.index = index;
        this.phase = phase;
        ScanResult.tree[index] = res;
    }

    @Override
    protected T compute() {
        if (phase == Phase.UP) {
            return up();
        } else {
            return down();
        }
    }

    private T up() {
        if (r - l <= Constants.THRESHOLD) {
            T curr = null;
            for (int i = l; i < r; i++) {
                curr = f.apply(curr, (T) array[i]);
            }
            ScanResult.tree[index] = curr;
            return curr;
        }

        int m = (l + r) / 2;
        int indexL = index * 2 + 1;
        int indexR = index * 2 + 2;

        ParallelScanTask<T> leftTask = new ParallelScanTask<>(Phase.UP, l, m, indexL, f, null);
        ParallelScanTask<T> rightTask = new ParallelScanTask<>(Phase.UP, m, r, indexR, f, null);

        leftTask.fork();
        T right = rightTask.compute();
        T left = leftTask.join();

        ScanResult.tree[index] = f.apply(left, right);
        return (T) ScanResult.tree[index];
    }

    private T down() {
        if (r - l <= Constants.THRESHOLD) {
            T curr = (T) ScanResult.tree[index];
            for (int i = l; i < r; i++) {
                ScanResult.result[i] = curr;
                curr = f.apply(curr, (T) array[i]);
            }
            return curr;
        }

        int m = (l + r) / 2;
        int indexL = index * 2 + 1;
        int indexR = index * 2 + 2;

        T res = (T) ScanResult.tree[index];
        T rest = (T) ScanResult.tree[indexL];

        ParallelScanTask<T> leftTask = new ParallelScanTask<>(Phase.DOWN, l, m, indexL, f, res);
        ParallelScanTask<T> rightTask = new ParallelScanTask<>(Phase.DOWN, m, r, indexR, f, f.apply(res, rest));

        invokeAll(leftTask, rightTask);
        return null;
    }
}

package org.example.utils;

import java.text.DecimalFormat;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.apache.commons.math3.distribution.TDistribution;

public class Benchmarker<T extends Comparable<T>> {
    public record BenchmarkResult(double mean, double delta) {}

    public BenchmarkResult benchmark(
            String label,
            Supplier<T[]> generator,
            Consumer<T[]> consumer,
            int warmupTrials,
            int benchmarkTrials,
            double confidence
    ) {
        for (int i = 0; i < warmupTrials; i++) {
            T[] array = generator.get();
            consumer.accept(array);
        }

        long[] results = new long[benchmarkTrials];
        double mean = 0.0;
        for (int i = 0; i < benchmarkTrials; i++) {
            T[] array = generator.get();
            long start = System.currentTimeMillis();
            consumer.accept(array);
            long end = System.currentTimeMillis();
            results[i] = end - start;
            mean += results[i];
        }
        mean /= benchmarkTrials;

        double variance = 0.0;
        for (int i = 0; i < benchmarkTrials; i++) {
            variance += Math.pow(results[i] - mean, 2);
        }
        double stdDev = Math.sqrt(variance / (benchmarkTrials - 1));
        double t = t(benchmarkTrials - 1, confidence);
        double error = t * stdDev / Math.sqrt(benchmarkTrials);

        System.out.printf("""
                Benchmark results for %s:
                Trial cnt: warmup = %d, measure = %d
                Measured time: %s +/- %s ms.
                """, label, warmupTrials, benchmarkTrials, format(mean), format(error));

        return new BenchmarkResult(mean, error);
    }

    public void benchmark(
            String[] labels,
            Supplier<T[]> generator,
            Consumer<T[]>[] consumers,
            int warmupTrials,
            int benchmarkTrials,
            double confidence,
            int baseline
    ) {
        double[] results = new double[labels.length];
        double[] speedup = new double[labels.length];

        for (int i = 0; i < labels.length; i++) {
            BenchmarkResult result = benchmark(
                    labels[i],
                    generator,
                    consumers[i],
                    warmupTrials,
                    benchmarkTrials,
                    confidence
            );
            System.out.println();
            results[i] = result.mean;
        }

        double baselineMean = results[baseline];
        for (int i = 0; i < labels.length; i++) {
            speedup[i] = results[i] / baselineMean;
        }

        System.out.println("===== RESULTS =====");
        for (int i = 0; i < labels.length; i++) {
            String verdict = i == baseline
                    ? format(results[i]) + "ms. (baseline)"
                    : format(results[i]) + "ms. (speedup " + format(1.0 / speedup[i]) + "x)";
            System.out.printf("%s: %s\n", labels[i], verdict);
        }
    }

    private double t(int trials, double confidence) {
        TDistribution distribution = new TDistribution(trials);
        return distribution.inverseCumulativeProbability(1 - (1 - confidence) / 2);
    }

    private String format(double x) {
        DecimalFormat format = new DecimalFormat("#.###");
        return format.format(x);
    }
}

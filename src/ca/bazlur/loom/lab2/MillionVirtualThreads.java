package ca.bazlur.loom.lab2;

import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

public class MillionVirtualThreads {
    static LongAdder longAdder = new LongAdder();

    public static void main(String[] args) {

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (; ; ) {
                executor.submit(() -> {
                    longAdder.increment();
                    return sleep();
                });
            }
        }
    }

    private static Object sleep() {
        try {
            if (longAdder.longValue() % 100_000 == 0) {
                System.out.printf(
                        Locale.US, "Current Thread count: %,d\tthreads%n",
                        longAdder.longValue()
                );
            }
            TimeUnit.HOURS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}

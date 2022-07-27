package ca.bazlur.loom.lab1;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

public class CountThreads {
    public static void main(String[] args) {
        var counter = new AtomicInteger();
        while (true) {
            var thread = new Thread(() -> {
                int threadsCount = counter.incrementAndGet();
                System.out.printf(
                        Locale.US, "started %,d\tthreads %,d%n",
                        threadsCount, Thread.currentThread().threadId()
                );
                LockSupport.park();
            });

            thread.start();
        }
    }
}

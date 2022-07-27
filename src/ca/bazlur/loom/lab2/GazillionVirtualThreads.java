package ca.bazlur.loom.lab2;

import java.time.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;


//https://www.javaspecialists.eu/archive/Issue301-Gazillion-Virtual-Threads.html
public class GazillionVirtualThreads {
    public static void main(String... args)
            throws InterruptedException {
        LongAdder threads = new LongAdder();
        long started = 0;
        while (true) {
            Thread.startVirtualThread(() -> {
                threads.increment();
                LockSupport.park();
            });
            started++;
            if (started % 1_000_000 == 0) {
                long threadsCount = threads.longValue();
                System.out.printf(
                        Locale.US, "started %,d\tthreads %,d%n",
                        started, threadsCount
                );
                if (started - threadsCount > 10_000_000) {
                    System.out.print("Waiting for virtual thread " +
                            "scheduling to catch up");
                    while (started - threads.longValue() > 1_000_000) {
                        Thread.sleep(Duration.ofSeconds(1));
                        System.out.print(".");
                    }
                    System.out.println();
                }
            }
        }
    }
}
package ca.bazlur.loom.lab3;

import java.util.List;
import java.util.stream.IntStream;

public class ClassicalSynchronization {
    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();

        List<Thread> threads = IntStream.range(0, 10)
                .mapToObj(index ->
                        Thread.ofVirtual().unstarted(() -> {
                            if (index == 0) System.out.println(Thread.currentThread());
                            synchronized (lock){
                                sleep();
                            }
                            if (index == 0) System.out.println(Thread.currentThread());
                        })).toList();

        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

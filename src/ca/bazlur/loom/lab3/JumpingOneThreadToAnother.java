package ca.bazlur.loom.lab3;

import java.util.List;
import java.util.stream.IntStream;

public class JumpingOneThreadToAnother {
    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = IntStream.range(0, 10)
                .mapToObj(index ->
                        Thread.ofVirtual().unstarted(() -> {
                            printThread(index);
                        })).toList();

        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }
    }

    private static void printThread(int index) {
        if (index == 0) System.out.println(Thread.currentThread());
        sleep();
        if (index == 0) System.out.println(Thread.currentThread());
    }

    private static void sleep() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

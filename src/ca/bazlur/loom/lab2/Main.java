package ca.bazlur.loom.lab2;

import jdk.incubator.concurrent.StructuredTaskScope;

import java.util.Random;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

//        var thread = new Thread(() -> {
//            System.out.println("Hello world!");
//            try {
//                Thread.sleep(1_000_000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        });
//        thread.setName("MyThread");
//        thread.start();
//        thread.join();


//        var thread = new Thread(() -> {
//            System.out.println("Running inside a new thread!");
//        });
//        thread.start();
//        thread.join();
//
//
//        int fooBaz = foo();
//        System.out.println("fooBaz = " + fooBaz);
//        System.out.println("hello world");
//
        var threadPool = Executors.newVirtualThreadPerTaskExecutor();
        Future<Long> fifthFibonacciNumber = threadPool.submit(() -> fib(100, threadPool));

        System.out.println("fifthFibonacciNumber = " + fifthFibonacciNumber.get());

        threadPool.shutdown();
    }

    private static void downlaodImages(int id) {
    }


    public static long fib(int n, ExecutorService threadPool)
            throws ExecutionException, InterruptedException {
        if (n < 2) {
            return n;
        } else {
            var prev = threadPool.submit(() -> fib(n - 1, threadPool));
            var prevPrev = threadPool.submit(() -> fib(n - 2, threadPool));
            return prev.get() + prevPrev.get();
        }
    }

    private static int foo() {
        try (var taskScope = new StructuredTaskScope.ShutdownOnFailure()) {
            Future<Integer> f1 = taskScope.fork(Main::baz);
            Future<Integer> f2 = taskScope.fork(Main::baz);

            taskScope.join();
            return f1.resultNow() + f2.resultNow();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static int baz() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new Random().nextInt();
    }
}
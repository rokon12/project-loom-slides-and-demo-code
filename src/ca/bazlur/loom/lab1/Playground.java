package ca.bazlur.loom.lab1;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class Playground {
    public static void main(String[] args) throws InterruptedException {
        Thread vThread1 = Thread.startVirtualThread(() -> {
            System.out.println("Current Thread: " + Thread.currentThread());
            sleep();


        });
        vThread1.join();


        Thread thread = Thread.ofVirtual().unstarted(() -> {
            System.out.println("Current Thread: " + Thread.currentThread());
        });
        thread.start();

        thread.join();

        Thread platformThread = Thread.ofPlatform().unstarted(() -> {
            System.out.println("Current Thread: " + Thread.currentThread());
        });
        platformThread.start();
        platformThread.join();

        List<Thread> threads = IntStream.range(0, 1_000)
                .mapToObj(index -> Thread.ofVirtual().unstarted(() -> {
                    System.out.println("Current Thread: " + Thread.currentThread());
                    sleep();
                })).toList();
        for (var t : threads) {
            t.start();
        }

    }

    private static void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


//    Response handle() throws IOException {
//        String theUser = findUser();
//        int theOrder = fetchOrder();
//        return new Response(theUser, theOrder);
//    }

    private String findUser() {
        return null;
    }

    private int fetchOrder() {
        return 0;
    }

//    Response handle() throws ExecutionException, InterruptedException {
//        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
//            Future<String> user = scope.fork(() -> findUser());
//            Future<Integer> order = scope.fork(() -> fetchOrder());
//
//            scope.join();          // Join both forks
//            scope.throwIfFailed(); // ... and propagate errors
//
//            // Here, both forks have succeeded, so compose their results
//            return new Response(user.resultNow(), order.resultNow());
//        }
//    }

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    Response handle() throws ExecutionException, InterruptedException {
        Future<String> user = executorService.submit(() -> findUser());
        Future<Integer> order = executorService.submit(() -> fetchOrder());
        String theUser = user.get();   // Join findUser
        int theOrder = order.get();  // Join fetchOrder
        return new Response(theUser, theOrder);
    }
}

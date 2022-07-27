package ca.bazlur.loom.lab2;

import jdk.internal.vm.*;

//--add-exports java.base/jdk.internal.vm=ALL-UNNAMED

public class ContinuationExample {
    public static void main(String[] args) {
        var scope = new ContinuationScope("Continuation Demo");
        var continuation = new Continuation(scope, () -> {
            System.out.println("C1: " + Thread.currentThread());

            Continuation.yield(scope);

            System.out.println("C2: " + Thread.currentThread());
        });

        System.out.println("start");
        continuation.run();
        System.out.println("come back");

        continuation.run();

        System.out.println("done!");

//        Thread.sleep(10);
    }
}

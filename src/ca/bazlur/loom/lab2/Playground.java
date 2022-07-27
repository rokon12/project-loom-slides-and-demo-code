package ca.bazlur.loom.lab2;

import jdk.incubator.concurrent.StructuredTaskScope;

import java.text.NumberFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.LockSupport;

public class Playground {
    public static void main(String[] args) throws InterruptedException {

        Thread vThread = Thread.ofVirtual()
                .unstarted(() -> System.out.println("Running on top of virtual threads: " + Thread.currentThread()));
        vThread.start();

        Thread v2 = Thread.startVirtualThread(() -> System.out.println("Running on top of virtual threads: " + Thread.currentThread()));

        v2.join();

        Thread pThread = Thread.ofPlatform().unstarted(() -> {
            System.out.println("Running on top of classical threads: " + Thread.currentThread());
        });

        pThread.start();
        pThread.join();
        vThread.join();

        // ForkJoinPool

        //
        ExecutorService threadPool = Executors.newFixedThreadPool(10);

       // threadPool.submit()


    }


   static class A {
        void a() {
            new B().b();
        }
    }

    static  class B {
        void b() {
            new C().c();
        }
    }

    static  class C {
        void c() {
            new D().d();
        }
    }

    static class D {
        void d() {
            throw new RuntimeException("Exception");
        }
    }
}

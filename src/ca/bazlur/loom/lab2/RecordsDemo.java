package ca.bazlur.loom.lab2;

public class RecordsDemo {
    public static void main(String[] args) {

        Pair<A> p1 = new Pair<>(new A(), new B());
        Pair<I> p2 = new Pair<>(new C(), new D());


//        switch (p2) {
//            case Pair<I>(I i, C c) -> System.out.println( "Hello");
//        }


        Double PI = 3.1416;


    }

    public void print(Object o) {
        if (o instanceof Double d) {
            System.out.println("d = " + d);
        }
    }


    static void printSum(Object o) {
        if (o instanceof Point p) {
            int x = p.x();
            int y = p.y();
            System.out.println(x+y);
        }
    }
}

class A {
}

class B extends A {
}

sealed interface I permits C, D {
}

final class C implements I {
}

final class D implements I {
}

record Pair<T>(T x, T y) {
}
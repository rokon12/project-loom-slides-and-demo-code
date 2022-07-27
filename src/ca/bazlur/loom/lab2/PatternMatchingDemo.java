package ca.bazlur.loom.lab2;

public class PatternMatchingDemo {
    public static void main(String[] args) {
        //printSum(new Point(4, 5));
    }

//    static void printSum(Object o) {
//        if (o instanceof Point( int x, int y)){
//            System.out.println(x + y);
//        }
//    }
//
//    public void printSum(Object o) {
//        if (o instanceof Point ( int x int y)){
//            System.out.println(x + y);
//        }
//    }
//
//    public void printUpperLeftColoredPoint(Square s) {
//        if (s instanceof Square(ColoredPoint(Point(var x, var y), var color), var leftRight))){
//        }
//    }
}


record Square(ColoredPoint upperLeft, ColoredPoint lowerRight) {
}

enum Color {RED, GREEN, BLUE}

record ColoredPoint(Point p, Color color) {
}

record Point(int x, int y) {
}


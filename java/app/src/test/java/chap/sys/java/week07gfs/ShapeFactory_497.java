package chap.sys.java.week07gfs;

import org.junit.Test;

/*-
Factory is a design pattern in common usage. Implement a ShapeFactory that can generate correct shape.

You can assume that we have only three different shapes: Triangle, Square and Rectangle.
*/
public class ShapeFactory_497 {
    @Test public void test() {
        ShapeFactory sf = new ShapeFactory();
        sf.getShape("Triangle").draw();
        sf.getShape("Square").draw();
    }
    interface Shape {
        void draw();
    }

    class Rectangle implements Shape {

        @Override
        public void draw() {
            System.out.println(" ---- ");
            System.out.println("|    |");
            System.out.println(" ---- ");
        }
        // Write your code here
    }

    class Square implements Shape {

        @Override
        public void draw() {
            System.out.println(" ---- ");
            System.out.println("|    |");
            System.out.println("|    |");
            System.out.println(" ---- ");
        }
        // Write your code here
    }

    class Triangle implements Shape {

        @Override
        public void draw() {
            System.out.println("  /\\");
            System.out.println(" /  \\");
            System.out.println("/____\\");

        }
        // Write your code here
    }

    public class ShapeFactory {
        /**
         * @param shapeType a string
         * @return Get object of type Shape
         */
        public Shape getShape(String shapeType) {
            // Write your code here
            if (shapeType == null)
                return null;
            switch(shapeType) {
                case "Rectangle":
                    return new Rectangle();
                case "Square":
                    return new Square();
                case "Triangle":
                    return new Triangle();
                default:
                    return null;
            }
        }
    }
}

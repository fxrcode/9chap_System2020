package chap.sys.java.week07gfs;

import org.junit.Test;

/*-
Factory is a design pattern in common usage. Please implement a ToyFactory which can generate proper toy based on the given type.
*/
public class ToyFactory_496 {
    @Test
    public void test1() {
        ToyFactory tf = new ToyFactory();
        tf.getToy("Dog").talk();
        tf.getToy("Cat").talk();
        tf.getToy("ren");
    }
    /**
     * Your object will be instantiated and called as such: ToyFactory tf = new
     * ToyFactory(); Toy toy = tf.getToy(type); toy.talk();
     */
    interface Toy {
        void talk();
    }

    class Dog implements Toy {
        @Override
        public void talk() {
            System.out.println("Wow");
        }
    }

    class Cat implements Toy {
        @Override
        public void talk() {
            System.out.println("Meow");
        }
    }

    public class ToyFactory {
        /**
         * @param type a string
         * @return Get object of the type
         */
        public Toy getToy(String type) {
            switch(type) {
                case "Dog":
                    return new Dog();
                case "Cat":
                    return new Cat();
                default:
                    throw new RuntimeException("Not valid type: " + type);
            }
        }
    }
}

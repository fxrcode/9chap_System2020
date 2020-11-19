package chap.sys.java.j11;

import org.junit.Test;
import java.util.*;

public class Play_NavigatableTree {

    /**
     * To find least greater, or most less, use Red-black tree: TreeSet/Map.
     * For 520: Consistent Hashing II
     */
    @Test
    public void test_treeset() {
        NavigableSet<Integer> set = new TreeSet<>();
        set.add(10);
        set.add(350);
        set.add(45);
        set.add(180);

        int a = 60;
        System.out.println( set.ceiling(a) );
        System.out.println( set.floor(a) ) ;

        int b = 359;
        System.out.println( set.ceiling(b));
        System.out.println( set.floor(b));

        int c = 45;
        System.out.println( set.ceiling(c));
        System.out.println( set.floor(c));
    }
}

package chap.sys.java.j11;

import org.junit.Test;
import java.util.*;
import java.util.Map.Entry;

/**
 * Todo: get familiar of all navigable set/map methods, time complexity, comparator.
 */
public class Play_NavigatableTree {

    /**
     * To find least greater, or most less, use Red-black tree: TreeSet/Map.
     * For 520: Consistent Hashing II
     */
    @Test
    public void test_basic_treeset() {
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

    /**
     * Try to use in HeartBeat GFS, to find all slaves that last ping is older than currentEpoch - 2k sec.
     * But actually, Treeset/map are sorted by key, and there's no Range Query.
     */
    @Test
    public void test_treeset_comparator() {
        NavigableSet< Entry<String, Integer> > iplastpings = new TreeSet<>( (a,b) -> {
            return a.getValue() - b.getValue();
        });
        iplastpings.add(Map.entry("0.0.0.1", 40) );
        iplastpings.add(Map.entry("0.0.0.2", 80) );
        iplastpings.add(Map.entry("0.0.0.3", 60) );
        iplastpings.add(Map.entry("0.0.0.4", 100) );
        iplastpings.add(Map.entry("0.0.0.5", 90) );

        // find all time older than 85
        System.out.println( iplastpings );
        List<String> ans = new ArrayList<>();
        iplastpings.forEach( il -> {
            if (il.getValue() < 85) {
                ans.add(il.getKey());
            }
        });
        System.out.println( ans );
    }
}

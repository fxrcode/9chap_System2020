package chap.sys.java.week04dbconsisthashing;

import org.junit.Test;
import java.util.*;

/**
 *
 * -- Question Implement a web logger, which provide two methods:
 * 
 * hit(timestamp), record a hit at given timestamp.
 * get_hit_count_in_last_5_minutes(timestamp), get hit count in last 5 minutes.
 * the two methods will be called with non-descending timestamp (in sec).
 * 
 * The unit of timestamp is seconds.
 * 
 * -- comment The call to the function is in chronological order, that is to
 * say, timestamp is in ascending order.
 */
public class WebLogger_505_a {
    @Test
    public void test() {
        WebLogger wl = new WebLogger();
        wl.hit(1);
        wl.hit(2);
        wl.get_hit_count_in_last_5_minutes(3);
        wl.hit(300);
        wl.get_hit_count_in_last_5_minutes(300);
        wl.get_hit_count_in_last_5_minutes(301);
    }

    @Test public void test2() {
        WebLogger wl = new WebLogger();
        wl.hit(1);
        wl.hit(1);
        wl.hit(1);
        wl.hit(2);
        wl.get_hit_count_in_last_5_minutes(3);
        wl.hit(300);
        wl.get_hit_count_in_last_5_minutes(300);
        wl.get_hit_count_in_last_5_minutes(301);
        wl.get_hit_count_in_last_5_minutes(302);
        wl.get_hit_count_in_last_5_minutes(900);
        wl.get_hit_count_in_last_5_minutes(900);
    }

    public class WebLogger {
        LinkedList<Integer> ll = new LinkedList<>();

        public WebLogger() {
            // do intialization if necessary
        }

        /*
         * @param timestamp: An integer
         *
         * @return: nothing
         */
        public void hit(int timestamp) {
            // write your code here
            ll.add(timestamp);
        }

        /*
         * @param timestamp: An integer
         *
         * @return: An integer
         */
        public int get_hit_count_in_last_5_minutes(int timestamp) {
            // write your code here
            while (!ll.isEmpty() && ll.getFirst() + 300 <= timestamp) {
                ll.removeFirst();
            }
            int rt = ll.size();
            System.out.println( rt );
            return rt;
        }

        // private int binary(int target, int l, int h) {
        //     while (l + 1 < h) {
        //         int mid = (l + h) / 2;
        //         if (ll.get(mid) < target) {
        //             l = mid;
        //         } else if (ll.get(mid) == target) {
        //             h = mid;
        //         } else {
        //             h = mid;
        //         }
        //     }
        //     return h;
        // }
    }
}

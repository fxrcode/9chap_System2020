package chap.sys.java.week05bigtable;

import org.junit.Test;
import java.util.*;

/*-
Given k sorted integer arrays, merge them into one sorted array.
*/
public class Merge_K_Sorted_arrays_486_a {
    @Test public void test() {
        int[][] arrays = {
            {1, 3, 5, 7},
            {2, 4, 6},
            {0, 8, 9, 10, 11}
        };
        Solution s = new Solution();
        s.mergekSortedArrays(arrays);
    }

    public class Solution {
        PriorityQueue<Element> minpq = new PriorityQueue<>((a,b) -> {
            return a.val - b.val;
        });
        /**
         * @param arrays: k sorted integer arrays
         * @return: a sorted array
         */
        public int[] mergekSortedArrays(int[][] arrays) {
            // write your code here
            if (arrays == null)
                return new int[0];

            int total_size = 0;
            for (int i = 0; i < arrays.length; i++) {
                if (arrays[i].length > 0) {
                    minpq.add(new Element(i, 0, arrays[i][0]));
                    total_size += arrays[i].length;
                }
            }

            int[] ret = new int[total_size];
            int idx = 0;
            while (!minpq.isEmpty()) {
                Element e = minpq.poll();
                ret[idx++] = e.val;
                if (e.col + 1 < arrays[e.row].length) {
                    minpq.add(new Element(e.row, e.col + 1, arrays[e.row][e.col + 1]));
                }
            }
            for (int i = 0; i < ret.length; i++) {
                System.out.println(ret[i]);
            }
            return ret;
        }
        class Element {
            int row, col, val;
            public Element(int r, int c, int v) {
                this.row = r;
                this.col = c;
                this.val = v;
            }
        }
    }
}

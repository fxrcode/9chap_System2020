package chap.sys.java.week04dbconsisthashing;

import java.util.*;

import org.junit.Test;

public class ConsistHashing_519_b {
    @Test
    public void test() {
        Solution sln = new Solution();
        for (int i = 1; i <= 6; i++) {
            System.out.println(sln.consistentHashing(i));
        }
    }

    /**
     * Java inner class is defined inside the body of another class. Java inner
     * class can be declared private, public, protected, or with default access
     * whereas an outer class can have only public or default access. Java Nested
     * classes are divided into two types.
     */
    public class Solution {
        /*
         * @param n: a positive integer
         * 
         * @return: n x 3 matrix
         */
        public List<List<Integer>> consistentHashing(int n) {
            Queue<List<Integer>> pq = new PriorityQueue<>((a, b) -> {
                int range_A = a.get(1) - a.get(0) + 1;
                int range_B = b.get(1) - b.get(0) + 1;
                int idx_A = a.get(2);
                int idx_B = b.get(2);
                if (range_A == range_B) {
                    return idx_A - idx_B;
                } else {
                    return range_B - range_A;
                }
            });

            List<List<Integer>> res = new ArrayList<>();
            if (n <= 0) {
                return res;
            }

            // initialize only 1 server
            List<Integer> init = Arrays.asList(0, 359, 1);

            pq.offer(init);

            for (int i = 1; i < n; i++) {
                List<Integer> cur = pq.poll();  // poll the largest server

                // int mid = cur.get(0) + (cur.get(1) - cur.get(0)) / 2;
                int mid = (cur.get(0) + cur.get(1)) / 2;

                List<Integer> newServer = Arrays.asList(mid+1, cur.get(1), i+1);
                cur.set(1, mid);
                pq.offer(cur);
                pq.offer(newServer);
            }

            res.addAll(pq);

            return res;

        }
    }
}

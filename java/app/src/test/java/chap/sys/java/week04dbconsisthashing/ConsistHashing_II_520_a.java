package chap.sys.java.week04dbconsisthashing;

import org.junit.Test;
import java.util.*;

/**
 *
 * - Question: In Consistent Hashing I we introduced a relatively simple
 * consistency hashing algorithm. This simple version has two defects：
 *
 * After adding a machine, the data comes from one of the machines. The read
 * load of this machine is too large, which will affect the normal service. When
 * adding to 3 machines, the load of each server is not balanced, it is 1:1:2 In
 * order to solve this problem, the concept of micro-shards was introduced, and
 * a better algorithm is like this：
 *
 * From 0~359 to a range of 0 ~ n-1, the interval is connected end to end and
 * connected into a circle. When joining a new machine, randomly choose to
 * sprinkle k points in the circle, representing the k micro-shards of the
 * machine. Each data also corresponds to a point on the circumference, which is
 * calculated by a hash function. Which machine belongs to which data is to be
 * managed is determined by the machine to which the first micro-shard point
 * that is clockwise touched on the circle is corresponding to the point on the
 * circumference of the data. n and k are typically 2^64 and 1000 in a real
 * NoSQL database.
 *
 * Implement these methods of introducing consistent hashing of micro-shard.
 * 
 * create(int n, int k) addMachine(int machine_id) // add a new machine, return
 * a list of shard ids. getMachineIdByHashCode(int hashcode) // return machine
 * id
 *
 * - Comment: When n is 2^64, there is almost no repetition in the interval
 * within this interval. However, in order to test the correctness of your
 * program, n may be small in the data, so you must ensure that the k random
 * numbers you generate will not be duplicated. LintCode does not judge the
 * correctness of your returnMachine's return (because it is a random number),
 * it will only judge the correctness of your getMachineIdByHashCode result
 * based on the result of the addMachine you return.
 */
public class ConsistHashing_II_520_a {
    @Test
    public void test() {
        Solution s = Solution.create(100, 3);
        System.out.println(s.addMachine(1));
        System.out.println(s.getMachineIdByHashCode(4));
        System.out.println(s.addMachine(2));
        System.out.println(s.getMachineIdByHashCode(61));
        System.out.println(s.getMachineIdByHashCode(91));
    }

    /**
     * hash data, and server => hash_data, hash_server then clockwise find the least
     * hash_server >= hash_data.
     */
    public static class Solution {
        Map<Integer, String> serverhashsById;
        NavigableSet<Integer> set;
        Random rand;
        int n, k;

        /*
         * @param n: a positive integer
         *
         * @param k: a positive integer
         *
         * @return: a Solution object
         */
        public static Solution create(int n, int k) {
            // Write your code here
            Solution s = new Solution();
            s.n = n;
            s.k = k;
            s.rand = new Random();
            s.set = new TreeSet<>();
            s.serverhashsById = new HashMap<>();
            return s;
        }

        /*
         * @param machine_id: An integer
         *
         * @return: a list of shard ids
         */
        public List<Integer> addMachine(int machine_id) {
            // write your code here
            List<Integer> rt = new ArrayList<>();
            for (int i = 0; i < k; i++) {
                int hash = rand.nextInt(n);
                while (set.contains(hash)) {
                    hash = rand.nextInt(n);
                }
                set.add(hash);
                serverhashsById.put(hash, machine_id + "-" + i);
                rt.add(hash);
            }
            // System.out.println(set);
            System.out.println( serverhashsById );
            // Collections.sort(rt);
            return rt;
        }

        /*
         * @param hashcode: An integer
         *
         * @return: A machine id
         */
        public int getMachineIdByHashCode(int hashcode) {
            // write your code here
            if (set.ceiling(hashcode) != null) {
                String server_id = serverhashsById.get(set.ceiling(hashcode));
                String[] split = server_id.split("-");
                System.out.println( hashcode + ": " + server_id );
                return Integer.parseInt(split[0]);
            } else {
                String server_id = serverhashsById.get(set.ceiling(0));
                String[] split = server_id.split("-");
                System.out.println( hashcode + ": " + server_id );
                return Integer.parseInt(split[0]);
            }
        }
    }
}

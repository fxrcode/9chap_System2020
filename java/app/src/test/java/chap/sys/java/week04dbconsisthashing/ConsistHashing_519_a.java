package chap.sys.java.week04dbconsisthashing;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*-
A general database method for performing a horizontal shard is to take the id against the total number of database servers n and then to find out which machine it is on. The downside of this approach is that as the data continues to increase, we need to increase the database server. When n is changed to n+1, almost all of the data has to be moved, which is not consistent. In order to reduce the defects caused by this naive's hash method (%n), a new hash algorithm emerges: Consistent Hashing, Consistent Hashing. There are many ways to implement this algorithm. Here we implement a simple Consistent Hashing.

Take id to 360. If there are 3 machines at the beginning, then let 3 machines be responsible for the three parts of 0~119, 120~239, 240~359. Then, how much is the model, check which zone you are in, and which machine to go to.
When the machine changes from n to n+1, we find the largest one from the n intervals, then divide it into two and give half to the n+1th machine.
For example, when changing from 3 to 4, we find the third interval 0~119 is the current largest interval, then we divide 0~119 into 0~59 and 60~119. 0~59 is still given to the first machine, 60~119 to the fourth machine.
Then change from 4 to 5, we find the largest interval is the third interval 120~239, after splitting into two, it becomes 120~179, 180~239.
Suppose all the data is on one machine at the beginning. When adding to the nth machine, what is the distribution of the interval and the corresponding machine number?

You can assume n <= 360. At the same time, we agree that when there are multiple occurrences in the maximum interval, we split the machine with the smaller number.
For example, the size of 0~119, 120~239 is 120, but the number of the previous machine is 1, and the number of the next machine is 2, so we split the range of 0~119.
*/
public class ConsistHashing_519_a {
    @Test
    public void test1() {
        Solution sln = new Solution();
        for (int i = 1; i <= 6; i++) {
            System.out.println(sln.consistentHashing(i));
        }
        // System.out.println( sln.consistentHashing(4) );
    }

    /**
     * learned from lintcode solution
     */
    class Solution {
        public Solution() {
        }

        /*
         * @param n: a positive integer
         * 
         * @return: n x 3 matrix
         */
        public List<List<Integer>> consistentHashing(int n) {
            // write your code here
            List<List<Integer>> result = new ArrayList<>();
            List<Integer> machine = Arrays.asList(0, 359, 1);
            result.add(machine);

            for (int i = 1; i < n; i++) { // i is for simulation
                int index = 0;
                for (int j = 1; j < i; j++) { // real cut in each simulation
                    if (result.get(j).get(1) - result.get(j).get(0) + 1 > result.get(index).get(1)
                            - result.get(index).get(0) + 1) {
                        index = j;
                    }
                }
                int x = result.get(index).get(0);
                int y = result.get(index).get(1);
                result.get(index).set(1, (x + y) / 2);
                List<Integer> new_machine = Arrays.asList((x + y) / 2 + 1, y, i + 1);
                result.add(new_machine);
            }
            return result;
        }
    }

    /**
     * So this is a basic CONSISTENT HASHING, because it's not like INCONSISTENT
     * HASHING that ALL data need to remove to new nodes.
     */
    class Solution_Bad {
        public Solution_Bad() {
        }

        /*
         * @param n: a positive integer
         * 
         * @return: n x 3 matrix
         */
        public List<List<Integer>> consistentHashing(int n) {
            // write your code here
            if (n == 1) {
                List<List<Integer>> rt = new ArrayList<>();
                List<Integer> r = Arrays.asList(0, 359, 1);
                rt.add(r);
                return rt;
            }
            List<List<Integer>> n_1 = consistentHashing(n - 1);
            int mx_size = 0;
            int mx_idx = 0;
            int mx_left = 0;
            for (int i = 0; i < n_1.size(); i++) {
                int i_sz = n_1.get(i).get(1) - n_1.get(i).get(0);
                if (i_sz > mx_size) {
                    mx_left = n_1.get(i).get(0);
                    mx_size = i_sz;
                    mx_idx = i;
                }
            }
            // break this mx into 2 item, and update rest's sublist's 3rd item by +1
            List<Integer> break_0 = Arrays.asList(mx_left, mx_left + mx_size / 2, n_1.get(mx_idx).get(2));
            List<Integer> break_1 = Arrays.asList(mx_left + mx_size / 2 + 1, mx_left + mx_size, n);

            n_1.set(mx_idx, break_0);
            n_1.add(mx_idx + 1, break_1);
            return n_1;
        }
    }
}

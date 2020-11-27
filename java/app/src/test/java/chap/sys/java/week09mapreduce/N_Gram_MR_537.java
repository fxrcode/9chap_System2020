package chap.sys.java.week09mapreduce;

import java.util.*;

/*-
Give a number of strings and the number N. Use the Map Reduce method to count all N-Grams and their occurrences. The letter is granular.
*/
public class N_Gram_MR_537 {
    // 您的提交打败了 100.00% 的提交!
    public static class NGram {

        public static class Map {
            public void map(String s, int n, String str, OutputCollector<String, Integer> output) {
                // Write your code here
                // Output the results into output buffer.
                // Ps. output.collect(String key, Integer value);
                for (int j = 0; j+n <= str.length(); j++) {
                    String outputKey = str.substring(j, j+n);
                    output.collect(outputKey, 1);
                }
            }
        }

        public static class Reduce {
            public void reduce(String key, Iterator<Integer> values, OutputCollector<String, Integer> output) {
                // Write your code here
                // Output the results into output buffer.
                // Ps. output.collect(String key, int value);
                int sum = 0;
                while (values.hasNext()) {
                    sum += values.next();
                }
                output.collect(key, sum);
            }
        }
    }
}

/*****************************************************************
 * All given class defined and ready to use *
 *****************************************************************/

// Definition of OutputCollector:
class OutputCollector<K, V> {
    public void collect(K key, V value) {
    }
    // Adds a key/value pair to the output buffer
}
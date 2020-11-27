package chap.sys.java.week09mapreduce;

import java.util.*;

// Use map reduce to build inverted index for given documents.
// Note the Step4+Step5 will sort key, then value.
// 您的提交打败了 86.20% 的提交!
public class InvertedIndex_MR_504 {
    public static class InvertedIndex {

        public static class Map {
            /**
             *
             * @param _unused_key: not used
             * @param value
             * @param output
             */
            public void map(String _unused_key, Document value, OutputCollector<String, Integer> output) {
                // Write your code here
                // Output the results into output buffer.
                // Ps. output.collect(String key, int value);
                StringTokenizer tokenizer = new StringTokenizer(value.content);
                while (tokenizer.hasMoreTokens()) {
                    String outputKey = tokenizer.nextToken();
                    output.collect(outputKey, value.id);
                }
            }
        }

        public static class Reduce {
            /**
             * Notice: between Map <-> Reduce, the framework will do Step4: PartitionSort, Step 5: Fetch + MergeSort,
             *  therefore it's sorted by key, then index. So Iterator<Integer> values is sorted as well.
             * @param key
             * @param values
             * @param output
             */
            public void reduce(String key, Iterator<Integer> values, OutputCollector<String, List<Integer>> output) {
                // Write your code here
                // Output the results into output buffer.
                // Ps. output.collect(String key, List<Integer> value);
                List<Integer> results = new ArrayList<>();
                int previous = -1;
                while (values.hasNext()) {
                    int n = values.next();
                    if (previous != n) {
                        results.add(n);
                        previous = n;
                    }
                }
                output.collect(key, results);
            }
        }
    }

    /*****************************************************************
     * All given class defined and ready to use *
     *****************************************************************/

    // Definition of OutputCollector:
    class OutputCollector<K, V> {
        public void collect(K key, V value);
        // Adds a key/value pair to the output buffer
    }

    class Document {
        public int id;
        public String content;
    }
}

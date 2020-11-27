package chap.sys.java.week09mapreduce;

public class SortIntegers_MR_554 {
    public class SortIntegers {

        public static class Map {
            public void map(int _, List<Integer> value, OutputCollector<String, List<Integer>> output) {
                // Write your code here
                // Output the results into output buffer.
                // Ps. output.collect(String key, List<Integer> value);
            }
        }

        public static class Reduce {
            public void reduce(String key, List<List<Integer>> values, OutputCollector<String, List<Integer>> output) {
                // Write your code here
                // Output the results into output buffer.
                // Ps. output.collect(String key, List<Integer> value);
            }
        }
    }

    /*****************************************************************
     *          All given class defined and ready to use             *
     *****************************************************************/

    // Definition of OutputCollector:
    class OutputCollector<K, V> {
        public void collect(K key, V value);
        // Adds a key/value pair to the output buffer
    }
}

package chap.sys.java.week09mapreduce;

import java.util.*;

// Lec 13.3 example coding question
public class Anagram_MR_503 {
    public static class Anagram {

        public static class Map {
            public void map(String key, String value, OutputCollector<String, String> output) {
                // Write your code here
                // Output the results into output buffer.
                // Ps. output.collect(String key, String value);
                StringTokenizer tokenizer = new StringTokenizer(value);
                while (tokenizer.hasMoreTokens()) {
                    String word = tokenizer.nextToken();
                    char[] sc = word.toCharArray();
                    Arrays.sort(sc);
                    output.collect(new String(sc), word);
                }
            }
        }

        public static class Reduce {
            public void reduce(String key, Iterator<String> values, OutputCollector<String, List<String>> output) {
                // Write your code here
                // Output the results into output buffer.
                // Ps. output.collect(String key, List<String> value);
                List<String> results = new ArrayList<>();
                while (values.hasNext()) {
                    String word = values.next();
                    results.add(word);
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
        public void collect(K key, V value){}
        // Adds a key/value pair to the output buffer
    }

}

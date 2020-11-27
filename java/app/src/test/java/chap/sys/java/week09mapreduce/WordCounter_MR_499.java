package chap.sys.java.week09mapreduce;

import java.util.Iterator;
import java.util.StringTokenizer;

/*-
Using map reduce to count word frequency.

https://hadoop.apache.org/docs/r1.2.1/mapred_tutorial.html#Example%3A+WordCount+v1.0
*/
public class WordCounter_MR_499 {

    /**
     * Demo of MapReduce in lec Chap 13.3
     * 您的提交打败了 35.80% 的提交!
     */
    public static class WordCount {

        public static class Map {
            /**
             * @param key: doc location
             * @param value: doc content
             * @return output: k:v of word:count
             */
            public void map(String key, String value, OutputCollector<String, Integer> output) {
                // Write your code here
                // Output the results into output buffer.
                // Ps. output.collect(String key, int value);
                StringTokenizer tokenizer = new StringTokenizer(value);
                while (tokenizer.hasMoreTokens()) {
                    String outputKey = tokenizer.nextToken();
                    output.collect(outputKey, 1);
                }
            }
        }

        public static class Reduce {
            /**
             *
             * @param key: output key of map: word
             * @param values: output value of map: count in array, so the Iterator is given
             * @param output: sum of word's count in kv: specific word:sum(count)
             */
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
    /*****************************************************************
     *          All given class defined and ready to use             *
     *****************************************************************/
    // Definition of OutputCollector:
    class OutputCollector<K, V> {
        public void collect(K key, V value){}
        // Adds a key/value pair to the output buffer
    }

}

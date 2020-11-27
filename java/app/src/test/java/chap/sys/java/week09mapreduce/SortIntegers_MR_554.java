package chap.sys.java.week09mapreduce;

import java.util.*;

/*-
Sort integers by Map Reduce framework.
In the mapper, key is the document id which can be ignored, value is the integers.

In the reducer, your can specify what the key / value is (this depends on how you implement your mapper class). For the output of the reducer class, the key can be anything you want, the value should be ordered. (the order is depending on when you output it)
*/
public class SortIntegers_MR_554 {
    // 您的提交打败了 14.80% 的提交!
    public static class SortIntegers {

        public static class Map {
            /**
             *
             * @param _unused_key
             * @param value
             * @param output
             */
            public void map(int _unused_key, List<Integer> value, OutputCollector<String, List<Integer>> output) {
                // Write your code here
                // Output the results into output buffer.
                // Ps. output.collect(String key, List<Integer> value);
                Collections.sort(value);
                output.collect("unused_key", value);
            }
        }

        public static class Reduce {
            // K-way merge
            public void reduce(String key, List<List<Integer>> values, OutputCollector<String, List<Integer>> output) {
                // Write your code here
                // Output the results into output buffer.
                // Ps. output.collect(String key, List<Integer> value);
                List<Integer> result = new ArrayList<>();
                Comparator<Element> cmp = (a, b) -> {
                    return a.val - b.val;
                };
                if (values.size() == 0) {
                    output.collect(key, result);
                    return;
                }
                PriorityQueue<Element> q = new PriorityQueue<>(values.size(), cmp);

                // init the Q
                for (int i = 0; i < values.size(); i++) {
                    if (values.get(i).size() != 0) {
                        q.add(new Element(i, 0, values.get(i).get(0)));
                    }
                }

                // poll and add next item from the according row
                while (!q.isEmpty()) {
                    Element cur = q.poll();
                    result.add(cur.val);
                    if (cur.col + 1 < values.get(cur.row).size()) {
                        cur.col += 1;
                        cur.val = values.get(cur.row).get(cur.col);
                        q.add(cur);
                    }
                }
                output.collect(key, result);
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

}

class Element {
    int row, col, val;

    public Element(int r, int c, int v) {
        row = r;
        col = c;
        val = v;
    }
}
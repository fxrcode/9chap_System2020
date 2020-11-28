package chap.sys.java.week09mapreduce;

import java.util.*;

/*-
Find top k frequent words with map reduce framework.

The mapper's key is the document id, value is the content of the document, words in a document are split by spaces.

For reducer, the output should be at most k key-value pairs, which are the top k words and their frequencies in this reducer. The judge will take care about how to merge different reducers' results to get the global top k frequent words, so you don't need to care about that part.

The k is given in the constructor of TopK class.
*/
public class TopKFrequentWords_MR_549 {

    // 您的提交打败了 12.60% 的提交!
    public static class TopKFrequentWords {

        public static class Map {
            public void map(String _unused_key, Document value, OutputCollector<String, Integer> output) {
                // Write your code here
                // Output the results into output buffer.
                // Ps. output.collect(String key, int value);
                StringTokenizer tokenizer = new StringTokenizer(value.content);
                while (tokenizer.hasMoreTokens()) {
                    String outputKey = tokenizer.nextToken();
                    output.collect(outputKey, 1);
                }
            }
        }

        public static class Reduce {
            private int k = 0;
            private PriorityQueue<Pair> q;

            // note: it's same lambda to create comparator, but it's better to write it to a
            // member, so we can reuse it in other places
            private Comparator<Pair> cmp = (a, b) -> {
                if (a.cnt != b.cnt) {
                    return a.cnt - b.cnt;
                } else {
                    return b.word.compareTo(a.word);
                }
            };

            public void setup(int k) {
                // initialize your data structure here
                this.k = k;
                q = new PriorityQueue<>(k, cmp);
            }

            public void reduce(String key, Iterator<Integer> values) {
                // Write your code here
                int sum = 0;
                while (values.hasNext()) {
                    sum += values.next();
                }
                Pair cur = new Pair(key, sum);
                if (q.size() < k) {
                    q.add(cur);
                } else {
                    Pair min = q.peek();
                    if (cmp.compare(cur, min) > 0) {
                        q.poll();
                        q.add(cur);
                    }
                }
            }

            public void cleanup(OutputCollector<String, Integer> output) {
                // Output the top k pairs <word, times> into output buffer.
                // Ps. output.collect(String key, Integer value);
                List<Pair> tmp = new ArrayList<>();
                // Careful, this doesn't preserve queue's order when using arr.addAll(queue);
                // tmp.addAll(q);
                while (!q.isEmpty())
                    tmp.add(q.poll());
                for (int i = tmp.size() - 1; i >= 0; i--) {
                    Pair cur = tmp.get(i);
                    output.collect(cur.word, cur.cnt);
                }
            }
        }
    }

}

class Pair {
    String word;
    int cnt;

    public Pair(String w, int v) {
        word = w;
        cnt = v;
    }
}

/*****************************************************************
 * All given class defined and ready to use *
 *****************************************************************/

// Definition of OutputCollector:
// class OutputCollector<K, V> {
//     public void collect(K key, V value) {
//     }
//     // Adds a key/value pair to the output buffer
// }

class Document {
    public int id;
    public String content;
}
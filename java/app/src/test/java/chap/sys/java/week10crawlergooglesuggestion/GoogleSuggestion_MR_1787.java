package chap.sys.java.week10crawlergooglesuggestion;

import java.util.*;

import org.junit.Test;

public class GoogleSuggestion_MR_1787 {
    @Test
    public void test() {
        // [("apple ",1200), ("app",1200), ("app store",1200), ("appp",10000),
        // ("aaaa",5), ("abc",1200), ("ac",11100), ("aaaaaa",1), ("aw",5), ("awe",45),
        // ("aqw",12)]
    }

    // 您的提交打败了 17.09% 的提交!
    public static class GoogleSuggestion {

        public static class Map {
            public void map(Document value, OutputCollector<String, Pair> output) {
                // Write your code here
                // Output the results into output buffer.
                // Ps. output.collect(String key, Pair value);
                String content = value.content;
                Pair pair = new Pair(content, value.count);
                String prefix = "";
                for (int i = 0; i < content.length(); i++) {
                    prefix += content.charAt(i);
                    output.collect(prefix, pair);
                }
            }
        }

        public static class Reduce {
            private Comparator<Pair> cmp = (a, b) -> {
                if (a.getCount() != b.getCount()) {
                    return a.getCount() - b.getCount();
                }
                return b.getContent().compareTo(a.getContent());
            };

            public void setup() {
                // initialize your data structure here
            }

            public void reduce(String key, Iterator<Pair> values, OutputCollector<String, Pair> output) {
                // Write your code here
                // Output the results into output buffer.
                // Ps. output.collect(String key, Pair value);
                PriorityQueue<Pair> q = new PriorityQueue<>(cmp);
                List<Pair> list = new ArrayList<>();
                while (values.hasNext()) {
                    q.add(values.next());
                    if (q.size() > 10) {
                        q.poll();
                    }
                }
                while (!q.isEmpty()) {
                    list.add(q.poll());
                }
                int n = list.size();
                for (int i = n - 1; i >= 0; i--) {
                    output.collect(key, list.get(i));
                }
            }
        }

        static class Pair {
            private String content;
            private int count;

            Pair(String key, int value) {
                this.content = key;
                this.count = value;
            }

            public String getContent() {
                return this.content;
            }
    
            public int getCount() {
                return this.count;
            }
        }
    }

    class OutputCollector<K, V> {
        public void collect(K key, V value) {
        }
        // Adds a key/value pair to the output buffer
    }

    // Definition of Document:
    class Document {
        public int count;
        public String content;
    }
}

package chap.sys.java.week09mapreduce;

import java.util.*;

// Create an inverted index with given documents.
public class InvertedIndex_500 {
    // 您的提交打败了 26.40% 的提交!
    public class Solution {
        /**
         * @param docs a list of documents
         * @return an inverted index
         */
        public Map<String, List<Integer>> invertedIndex(List<Document> docs) {
            // Write your code here
            Map<String, List<Integer>> _m = new HashMap<>();
            for (Document doc : docs) {
                StringTokenizer tok = new StringTokenizer(doc.content);
                while (tok.hasMoreTokens()) {
                    String word = tok.nextToken();
                    _m.putIfAbsent(word, new ArrayList<>());
                    List<Integer> ids = _m.get(word);
                    if (ids.size() == 0 || ids.get(ids.size()-1) != doc.id) {
                        ids.add(doc.id);
                    }
                }
            }
            return _m;
        }
    }
}

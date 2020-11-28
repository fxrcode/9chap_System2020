package chap.sys.java.week10crawlergooglesuggestion;

import java.util.*;

// Build tries from a list of <word, freq> pairs. Save top 10 for each node.
public class TrieService_559 {
    public class TrieService {

        private TrieNode root = null;

        public TrieService() {
            root = new TrieNode();
        }

        public TrieNode getRoot() {
            // Return root of trie root, and
            // lintcode will print the tree struct.
            return root;
        }

        /**
         * No much speed up.
         * @param word
         * @param frequency
         */
        public void insert(String word, int frequency) {
            // Write your cod here
            TrieNode cur = getRoot();
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (cur.children.get(c) == null) {
                    cur.children.put(c, new TrieNode());
                }
                cur = cur.children.get(c);
                List<Integer> sortedList = cur.top10;
                sortedList.add(frequency);
                Collections.sort(sortedList, Collections.reverseOrder());
                if (sortedList.size() > 10) {
                    sortedList.remove(10);
                }
                cur.top10 = sortedList;
            }
        }

        /**
         * My 1st implement, a bit slow.
         * @param word
         * @param frequency
         */
        public void insert_a(String word, int frequency) {
            // Write your cod here
            TrieNode cur = getRoot();
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (cur.children.get(c) == null) {
                    cur.children.put(c, new TrieNode());
                }
                cur = cur.children.get(c);
                addFrequency(cur, frequency);
            }
        }

        // 您的提交打败了 13.80% 的提交!
        private void addFrequency(TrieNode cur, int frequency) {
            List<Integer> top10 = cur.top10;
            top10.add(frequency);
            Collections.sort(top10, (a,b) -> {
                return b-a;
            });
            if (top10.size() > 10) {
                cur.top10 = top10.subList(0, 10);
            }
        }
    }

    // Definition of TrieNode:
    public class TrieNode {
        public NavigableMap<Character, TrieNode> children;
        public List<Integer> top10;

        public TrieNode() {
            children = new TreeMap<Character, TrieNode>();
            top10 = new ArrayList<Integer>();
        }
    }
}

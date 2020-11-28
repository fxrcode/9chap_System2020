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

        // @param word a string
        // @param frequency an integer
        public void insert(String word, int frequency) {
            // Write your cod here
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

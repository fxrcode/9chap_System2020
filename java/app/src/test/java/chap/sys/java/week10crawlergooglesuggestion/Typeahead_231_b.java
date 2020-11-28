package chap.sys.java.week10crawlergooglesuggestion;

import java.util.*;

public class Typeahead_231_b {

    public class Typeahead {
        /*
        * @param dict: A dictionary of words dict
        */
        public Typeahead(Set<String> dict) {
            // do intialization if necessary
        }
    
        /*
         * @param str: a string
         * @return: a list of words
         */
        public List<String> search(String str) {
            // write your code here
        }
    }

    class TrieNode {
        private NavigableMap<Character, TrieNode> sons;
        private boolean isWord;
        public TrieNode() {
            sons = new TreeMap<>();
            isWord = false;
        }
    }
}

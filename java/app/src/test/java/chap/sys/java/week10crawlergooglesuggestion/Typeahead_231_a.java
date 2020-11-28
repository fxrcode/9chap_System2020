package chap.sys.java.week10crawlergooglesuggestion;

import java.util.*;

import org.junit.Test;

/*-
Implement typeahead. Given a string and a dictionary, return all words that contains the string as a substring. The dictionary will give at the initialize method and wont be changed. The method to find all words with given substring would be called multiple times.
*/
public class Typeahead_231_a {
    @Test public void test() {
        Set<String> dict = new HashSet<>();
        dict.add("abd");
        dict.add("abcd");
        dict.add("abce");
        dict.add("ac");
        Typeahead ta = new Typeahead(dict);
        System.out.println( ta.search("ac") );
        System.out.println( ta.search("ab") );
    }
    public class Typeahead {
        private TrieNode root;
        /*
        * @param dict: A dictionary of words dict
        */
        public Typeahead(Set<String> dict) {
            // do intialization if necessary
            root = new TrieNode();
            for(String w : dict) {
                insert(w);
            }
        }

        /*
         * @param str: a string
         * 
         * @return: a list of words
         */
        public List<String> search(String str) {
            // write your code here
            List<String> ans = new ArrayList<>();
            if (str == null || str.length() == 0) {
                return ans;
            }
            TrieNode cur = root;
            for (int i = 0; i < str.length(); i++) {
                char ch = str.charAt(i);
                if (!cur.sons.containsKey(ch)) {
                    return ans;
                }
                cur = cur.sons.get(ch);
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            dfs(cur, ans, sb);

            return ans;
        }

        private void dfs(TrieNode subroot, List<String> ans, StringBuilder prefix) {
            if (subroot.isWord) {
                ans.add(prefix.toString());
            }
            for (Map.Entry<Character, TrieNode> kv : subroot.sons.entrySet()) {
                char c = kv.getKey();
                prefix.append(c);
                if (subroot.isWord) {
                    ans.add(prefix.toString());
                }
                dfs(subroot.sons.get(c), ans, prefix);
                int sz = prefix.length();
                prefix.delete(sz-1, sz);
            }
        }

        private void insert(String w) {
            if (w == null || w.length() == 0)
                return;
            TrieNode cur = root;
            for (int i = 0; i < w.length(); i++) {
                char ch = w.charAt(i);
                cur.sons.putIfAbsent(ch, new TrieNode());
                cur = cur.sons.get(ch);
            }
            cur.isWord = true;
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

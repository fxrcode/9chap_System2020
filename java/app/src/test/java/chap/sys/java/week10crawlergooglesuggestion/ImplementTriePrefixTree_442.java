package chap.sys.java.week10crawlergooglesuggestion;

// Implement a Trie with insert, search, and startsWith methods.
public class ImplementTriePrefixTree_442 {

    /**
     * 9chap adv algs Lec 2 Union-Find & Trie (33 loc)
     * 您的提交打败了 5.00% 的提交!
     */
    public class Trie {
        private Node root;

        public Trie() {
            // do intialization if necessary
            root = new Node();
        }

        /*
         * @param word: a word
         * @return: nothing
         */
        public void insert(String word) {
            // write your code here
            Node cur = root;
            for (int i = 0; i < word.length(); i++) {
                int pos = word.charAt(i) - 'a';
                if (cur.sons[pos] == null) {
                    cur.sons[pos] = new Node();
                }
                cur = cur.sons[pos];
            }
            cur.isWord = true;
        }

        /*
         * @param word: A string
         * @return: if the word is in the trie.
         */
        public boolean search(String word) {
            // write your code here
            Node cur = root;
            for (int i = 0; i < word.length(); i++) {
                int pos = word.charAt(i) - 'a';
                if (cur.sons[pos] == null) {
                    return false;
                }
                cur = cur.sons[pos];
            }
            return cur.isWord == true;
        }

        /*
         * @param prefix: A string
         * @return: if there is any word in the trie that starts with the given prefix.
         */
        public boolean startsWith(String prefix) {
            // write your code here
            Node cur = root;
            for (int i = 0; i < prefix.length(); i++) {
                int pos = prefix.charAt(i) - 'a';
                if (cur.sons[pos] == null) {
                    return false;
                }
                cur = cur.sons[pos];
            }
            return true;
        }
    }

    class Node {
        Node[] sons;
        boolean isWord;

        public Node() {
            sons = new Node[26];
            isWord = false;
        }

        public void insert(Node p, String word) {

        }
    }
}

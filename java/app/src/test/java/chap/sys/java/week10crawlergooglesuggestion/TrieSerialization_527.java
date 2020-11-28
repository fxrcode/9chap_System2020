package chap.sys.java.week10crawlergooglesuggestion;

import java.util.NavigableMap;
import java.util.Stack;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.junit.Test;

/*-
Serialize and deserialize a trie (prefix tree, search on internet for more details).

You can specify your own serialization algorithm, the online judge only cares about whether you can successfully deserialize the output from your own serialize function.

You only need to implement these two functions serialize and deserialize. We will run the following code snippet
*/
public class TrieSerialization_527 {
    @Test public void test1() {
        Solution sln = new Solution();
        TrieNode root = sln.getRoot();
        sln.insert("abe");
        sln.insert("ac");
        sln.insert("adf");
        System.out.println( sln.serialize(root) );
    }

    @Test public void test2() {
        Solution sln = new Solution();
        TrieNode root = sln.deserialize("<a<b<e<>>c<>d<f<>>>>");
        System.out.println( sln.serialize(root));
    }

    // Same as Tree: DFS/BFS to serialize, Stack to deserialize
    // 您的提交打败了 21.40% 的提交!
    class Solution {
        private TrieNode root = new TrieNode();
        public void insert(String word) {
            TrieNode cur = root;
            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                if (cur.children.get(ch) == null) {
                    cur.children.put(ch, new TrieNode() );
                }
                cur = cur.children.get(ch);
            }
        }

        public TrieNode getRoot() {
            return root;
        }

        /**
         * This method will be invoked first, you should design your own algorithm
         * to serialize a trie which denote by a root node to a string which
         * can be easily deserialized by your own "deserialize" method later.
         */
        public String serialize(TrieNode root) {
            // Write your code here
            if (root == null)
                return "";

            // StringBuffer & StringBuilder have same API, but latter one is not thread-safe, so it's faster and recommended in singlethread.
            StringBuilder sb = new StringBuilder();
            sb.append("<");
            for (Entry<Character, TrieNode> entry : root.children.entrySet()) {
                Character ch = entry.getKey();
                TrieNode son = entry.getValue();
                if ( son != null) {
                    sb.append(ch);
                    sb.append(serialize(son));
                }
            }
            sb.append(">");
            return sb.toString();
        }

        /**
         * This method will be invoked second, the argument data is what exactly
         * you serialized at method "serialize", that means the data is not given by
         * system, it's given by your own serialize method. So the format of data is
         * designed by yourself, and deserialize it here as you serialize it in 
         * "serialize" method.
         */
        public TrieNode deserialize(String data) {
            // Write your code here
            if (data == null || data.length() == 0) {
                return null;
            }
            TrieNode root = new TrieNode();
            TrieNode cur =  root;
            Stack<TrieNode> path = new Stack<>();
            for (Character ch : data.toCharArray()) {
                switch (ch) {
                    case '<':
                        path.push(cur);
                        break;
                    case '>':
                        path.pop();
                        break;
                    default:
                        cur = new TrieNode();
                        path.peek().children.put(ch, cur);
                }
            }
            return root;
        }
    }

    class TrieNode {
        public NavigableMap<Character, TrieNode> children;
        public TrieNode() {
            children = new TreeMap<>();
        }
    }
}

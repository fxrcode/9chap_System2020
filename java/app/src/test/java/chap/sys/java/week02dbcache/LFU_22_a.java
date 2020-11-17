package chap.sys.java.week02dbcache;

import org.junit.Test;
import java.util.*;

/*-
Design and implement a data structure for Least Frequently Used (LFU) cache.

Implement the LFUCache class:

* LFUCache(int capacity) Initializes the object with the capacity of the data structure.
* int get(int key) Gets the value of the key if the key exists in the cache. Otherwise, returns -1.
* void put(int key, int value) Sets or inserts the value if the key is not already present. When the cache reaches its capacity, it should invalidate the least frequently used item before inserting a new item. For this problem, when there is a tie (i.e., two or more keys with the same frequency), the least recently used key would be evicted.

Notice that the number of times an item is **used** is the number of calls to the **get** and **put** functions for that item since it was inserted. This number is set to zero when the item is removed.
*/
public class LFU_22_a {
    @Test public void test1() {
        LFUCache lfu = new LFUCache(3);
        lfu.set(1,10);
        lfu.set(2,20);
        lfu.set(3,30);
        System.out.println( lfu.get(1) );
        lfu.set(4,40);
        System.out.println( lfu.get(4) );
        System.out.println( lfu.get(3) );
        System.out.println( lfu.get(2) );
        System.out.println( lfu.get(1) );
        lfu.set(5,50);
        System.out.println( lfu.get(1) );
        System.out.println( lfu.get(2) );
        System.out.println( lfu.get(3) );
        System.out.println( lfu.get(4) );
        System.out.println( lfu.get(5) );
        // expecting: [10,40,30,-1,10,10,-1,30,-1,50]
    }

    /**
     * 您的提交打败了 1.00% 的提交!
     * TreeSet + HashMap. O(Log(capacity))
     */
    class LFUCache {
        int stamp;
        TreeSet<Node> cache_;
        Map<Integer, Node> m_;
        int cap;
        /*
        * @param capacity: An integer
        */
        public LFUCache(int capacity) {
            // do intialization if necessary
            this.cap = capacity;
            this.m_ = new HashMap<>();
            // comparator means less (<) in C++, it's easier to remember the order. Learned from Huahua.
            this.cache_ = new TreeSet<>((a,b) -> {
                if (a.t == b.t) {
                    return a.e - b.e;
                } else {
                    return a.t - b.t;
                }
            });
            this.stamp = 0;
        }

        /*
         * @param key: An integer
         * @return: An integer
         */
        public int get(int key) {
            // write your code here
            if (!m_.containsKey(key)) {
                return -1;
            }
            Node n = m_.get(key);
            touch(n);
            return n.v;
        }

        /*
         * @param key: An integer
         * @param value: An integer
         * @return: nothing
         */
        public void set(int key, int value) {
            // write your code here
            if (cap == 0) {
                return;
            }
            if (m_.containsKey(key)) {
                Node n = m_.get(key);
                n.v = value;
                touch(n);
                return;
            }
            if (m_.size() == cap) {
                Node n = cache_.pollFirst();
                m_.remove(n.k);
            }
            Node newNode = new Node(key, value, 1, ++stamp);
            m_.put(key, newNode);
            cache_.add(newNode);
        }

        /**
         * increment times, and update epoch to wall clock, then rebalance BST.
         * Need to action on TreeSet to trigger rebalance!
         * Since everything is reference in Java, so the node will also update in HashMap (m_)
         *
         * But it's a bit confusing for me to have underline side-effect, so I create new Node instead.
         */
        private void touch(Node node) {
            cache_.remove(node);
            Node newNode = new Node(node.k, node.v, ++node.t, ++stamp);
            cache_.add(newNode);
            m_.put(newNode.k, newNode);

            // this is a bit confusing for me, but Huahua did in this way.
            // node.t++;
            // node.e = ++stamp;
            // cache_.add(node);
        }
    }

    class Node {
        int k, v, t, e;
        public Node(int key, int value, int times, int epoch) {
            this.k = key;
            this.v = value;
            this.t = times;
            this.e = epoch;
        }
    }
}

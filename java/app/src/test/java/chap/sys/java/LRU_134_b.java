package chap.sys.java;

import java.util.*;
import org.junit.Test;

/*-
您的提交打败了 37.80% 的提交!
Use dummy head/tail node for doubly linked list.
linked list O(1) remove!
*/
public class LRU_134_b {
    @Test
    public void test1() {
        LRUCache lru = new LRUCache(2);
        lru.set(2, 1);
        lru.set(1, 1);
        System.out.println(lru.get(2));
        lru.set(4, 1);
        System.out.println(lru.get(1));
        System.out.println(lru.get(2));
    }

    @Test
    public void test2() {
        LRUCache lru = new LRUCache(1);
        lru.set(2, 1);
        System.out.println(lru.get(2));
        lru.set(3, 2);
        System.out.println(lru.get(2));
        System.out.println(lru.get(3));
    }

    class LRUCache {
        private Map<Integer, Node> map;
        private Node head, tail;
        private int cap;
        /*
        * @param capacity: An integer
        */
        public LRUCache(int capacity) {
            // do intialization if necessary
            this.map = new HashMap<>();
            this.cap = capacity;
            this.head = new Node(-1,-1);
            this.tail = new Node(-1,-1);
            this.head.next = tail;
            this.tail.prev = head;
        }

        /*
         * @param key: An integer
         * @return: An integer
         */
        public int get(int key) {
            // write your code here
            if ( !map.containsKey(key)) {
                return -1;
            }
            // move to tail
            Node n = map.get(key);
            removeNode(n);
            offerNode(n);

            return n.v;
        }

        /*
         * @param key: An integer
         * @param value: An integer
         * @return: nothing
         */
        public void set(int key, int value) {
            // write your code here
            if (get(key) != -1) {
                map.get(key).v = value;
                return;
            }
            if (map.size() == cap ) {
                map.remove(head.next.k);
                removeNode(head.next);
            }
            Node insert = new Node(key, value);
            offerNode(insert);
            map.put(key, insert);
        }

        private void removeNode(Node n) {
            n.next.prev = n.prev;
            n.prev.next = n.next;
        }

        private void offerNode(Node n) {
            n.prev = tail.prev;
            tail.prev = n;
            n.prev.next = n;
            n.next = tail;
        }
    }

    class Node {
        int k,v;
        Node prev,next;

        public Node(int key, int value) {
            this.k = key;
            this.v = value;
            this.prev = null;
            this.next = null;
        }
    }
}

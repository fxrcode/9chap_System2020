package chap.sys.java;

import java.util.*;

import org.junit.Test;

/*-
Design and implement a data structure for Least Recently Used (LRU) cache. It should support the following operations: get and set.

get(key) Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
set(key, value) Set or insert the value if the key is not already present. When the cache reached its capacity, it should invalidate the least recently used item before inserting a new item.
Finally, you need to return the data from each get.
*/
public class LRU_134_a {
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

    /**
     * 您的提交打败了 0.00% 的提交!
     */
    class LRUCache {
        private Map<Integer, KV> map;
        private List<KV> orderll;
        private int capacity;

        /*
         * @param capacity: An integer
         */
        public LRUCache(int capacity) {
            // do intialization if necessary
            this.map = new HashMap<>();
            this.orderll = new LinkedList<>();
            this.capacity = capacity;
        }

        /*
         * @param key: An integer
         *
         * @return: An integer
         */
        public int get(int key) {
            // write your code here
            if (!map.containsKey(key)) {
                return -1;
            }
            KV kv = map.get(key);
            orderll.remove(kv);
            orderll.add(kv);
            return kv.v;
        }

        /*
         * @param key: An integer
         * 
         * @param value: An integer
         *
         * @return: nothing
         */
        public void set(int key, int value) {
            // write your code here
            int sz = orderll.size();
            // 1. capacity not reached
            if (sz < capacity) {
                // 1.1 not found.
                if (!map.containsKey(key)) {
                    KV kv = new KV(key, value);
                    orderll.add(kv);
                    map.put(key, kv);
                } else {
                    // 1.2 found
                    KV kv = map.get(key);
                    orderll.remove(kv);
                    kv.v = value;
                    orderll.add(kv);

                }
            }
            // 2. capacity reached
            else {
                // 2.1 not found.
                if (!map.containsKey(key)) {
                    KV oldest = orderll.remove(0);
                    map.remove(oldest.k);
                    KV kv = new KV(key, value);
                    orderll.add(kv);
                    map.put(key, kv);
                } else {
                    // 2.2 found
                    KV kv = map.get(key);
                    orderll.remove(kv);
                    kv.v = value;
                    orderll.add(kv);
                }
            }
        }

    }

    // inner class
    class KV {
        public int k, v;

        public KV(int k, int v) {
            this.k = k;
            this.v = v;
        }
    }
}

package chap.sys.java;

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

    }

    class LFUCache {
        Map<Integer, Integer> freqsByKey;
        int cap;
        /*
        * @param capacity: An integer
        */
        public LFUCache(int capacity) {
            // do intialization if necessary
            this.cap = capacity;
            this.freqsByKey = new HashMap<>();
        }

        /*
         * @param key: An integer
         * @param value: An integer
         * @return: nothing
         */
        public void set(int key, int value) {
            // write your code here

        }
    
        /*
         * @param key: An integer
         * @return: An integer
         */
        public int get(int key) {
            // write your code here
        }
    }
}

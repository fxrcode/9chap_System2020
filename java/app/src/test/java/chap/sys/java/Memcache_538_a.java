package chap.sys.java;

import java.util.*;
import org.junit.Test;

/*-
- Description
Implement a memcache which support the following features:

get(curtTime, key). Get the key's value, return 2147483647 if key does not exist.
set(curtTime, key, value, ttl). Set the key-value pair in memcache with a time to live (ttl). The key will be valid from curtTime to curtTime + ttl - 1 and it will be expired after ttl seconds. if ttl is 0, the key lives forever until out of memory.
delete(curtTime, key). Delete the key.
incr(curtTime, key, delta). Increase the key's value by delta return the new value. Return 2147483647 if key does not exist.
decr(curtTime, key, delta). Decrease the key's value by delta return the new value. Return 2147483647 if key does not exist.
It's guaranteed that the input is given with increasingcurtTime.

- Comment
Actually, a real memcache server will evict keys if memory is not sufficient, and it also supports variety of value types like string and integer. In our case, let's make it simple, we can assume that we have enough memory and all of the values are integers.
Search "LRU" & "LFU" on google to get more information about how memcache evict data.
Try the following problem to learn LRU cache:
http://www.lintcode.com/problem/lru-cache
*/

public class Memcache_538_a {

    @Test public void test1() {
        Memcache cache = new Memcache();
        System.out.println( cache.get(1, 0) );
        cache.set(2,1,1,2);
        System.out.println( cache.get(3,1));
        System.out.println( cache.get(4,1));
        System.out.println( cache.incr(5,1,1));
        cache.set(6,1,3,0);
        System.out.println( cache.incr(7,1,1));
        System.out.println( cache.decr(8,1,1));
        System.out.println( cache.get(9,1));
        cache.delete(10, 1);
        System.out.println( cache.get(11,1));
        System.out.println( cache.incr(12,1,1));
    }

    class Memcache {
        private Map<Integer, Resource> memcache;
        public Memcache() {
            // do intialization if necessary
            this.memcache = new HashMap<>();
        }

        /*
         * @param curtTime: An integer
         * @param key: An integer
         * @return: An integer
         */
        public int get(int curtTime, int key) {
            // write your code here
            if (!memcache.containsKey(key)) {
                return Integer.MAX_VALUE;
            }
            Resource r = memcache.get(key);
            if ( r.ttl != 0 && curtTime >= r.ttl ) {
                this.delete(curtTime, key);
                return Integer.MAX_VALUE;
            }
            return r.value;
        }

        /*
         * @param curtTime: An integer
         * @param key: An integer
         * @param value: An integer
         * @param ttl: An integer
         * @return: nothing
         */
        public void set(int curtTime, int key, int value, int ttl) {
            // write your code here
            Resource r = ttl == 0? new Resource(value, 0) : new Resource(value, curtTime+ttl);
            memcache.put(key, r);
        }

        /*
         * @param curtTime: An integer
         * @param key: An integer
         * @return: nothing
         */
        public void delete(int curtTime, int key) {
            // write your code here
            if (memcache.containsKey(key)) {
                memcache.remove(key);
            }
        }

        /*
         * @param curtTime: An integer
         * @param key: An integer
         * @param delta: An integer
         * @return: An integer
         */
        public int incr(int curtTime, int key, int delta) {
            // write your code here
            if ( !memcache.containsKey(key) ) {
                return Integer.MAX_VALUE;
            }
            Resource r = memcache.get(key);
            if ( r.ttl != 0 && curtTime >= r.ttl ) {
                this.delete(curtTime, key);
                return Integer.MAX_VALUE;
            }
            return memcache.get(key).incr(delta);
        }

        /*
         * @param curtTime: An integer
         * @param key: An integer
         * @param delta: An integer
         * @return: An integer
         */
        public int decr(int curtTime, int key, int delta) {
            // write your code here
            if ( !memcache.containsKey(key) ) {
                return Integer.MAX_VALUE;
            }
            Resource r = memcache.get(key);
            if ( r.ttl != 0 && curtTime >= r.ttl ) {
                this.delete(curtTime, key);
                return Integer.MAX_VALUE;
            }
            return memcache.get(key).decr(delta);
        }
    }

    class Resource {
        public int value;
        public int ttl;

        Resource(int v, int ttl) {
            this.value = v;
            this.ttl = ttl;
        }

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

        public int incr(int delta) {
            this.value += delta;
            return this.value;
        }

        public int decr(int delta) {
            this.value -= delta;
            return this.value;
        }
    }
}

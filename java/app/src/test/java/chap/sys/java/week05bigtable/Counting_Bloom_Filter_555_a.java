package chap.sys.java.week05bigtable;

import org.junit.Test;
import java.util.*;

/*-
Implement a counting bloom filter. Support the following method:

add(string). Add a string into bloom filter.
contains(string). Check a string whether exists in bloom filter.
remove(string). Remove a string from bloom filter.
*/
public class Counting_Bloom_Filter_555_a {
    @Test public void test() {
        CountingBloomFilter cbf = new CountingBloomFilter(3);
        cbf.add("lint");
        cbf.add("code");
        cbf.contains("lint") ;
        cbf.remove("lint");
        cbf.contains("lint");
    }

    @Test public void test2() {
        CountingBloomFilter cbf = new CountingBloomFilter(3);
        cbf.add("lint");
        cbf.add("lint");
        cbf.contains("lint");
        cbf.remove("lint");
        cbf.contains("lint");
    }

    /**
     * 您的提交打败了 85.40% 的提交!
     */
    public class CountingBloomFilter {
        private int k;
        private List<HashFunc> hashs;
        private int[] bits;
        private final int PRIMES[] = {31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199};
        /*
        * @param k: An integer
        */public CountingBloomFilter(int k) {
            // do intialization if necessary
            this.k = k;
            this.bits = new int[100000+k];
            this.hashs = new ArrayList<>(k);
            for (int i = 0; i < k; i++) {
                hashs.add(new HashFunc(100000 + k, PRIMES[i]));
            }
        }

        /*
         * @param word: A string
         * @return: nothing
         */
        public void add(String word) {
            // write your code here
            for (int i = 0; i < k; i++) {
                int pos = hashs.get(i).hashfunc(word);
                bits[pos]++;
            }
        }

        /*
         * @param word: A string
         * @return: nothing
         */
        public void remove(String word) {
            // write your code here
            for (int i = 0; i < k; i++) {
                int pos = hashs.get(i).hashfunc(word);
                bits[pos]--;
            }
        }

        /*
         * @param word: A string
         * @return: True if contains word
         */
        public boolean contains(String word) {
            int ret = Integer.MAX_VALUE;
            // write your code here
            for (int i = 0; i < k; i++) {
                int pos = hashs.get(i).hashfunc(word);
                ret = Math.min(ret, bits[pos]);
            }
            System.out.println(ret);
            return ret > 0;
        }

        class HashFunc {
            int hashsize, base;
            public HashFunc(int size, int base) {
                this.hashsize = size;
                this.base = base;
            }
            public int hashfunc(String word) {
                int code = 0;
                for (int i = 0; i < word.length(); i++) {
                    code = (code * base + word.charAt(i) ) % hashsize;
                }
                return code;
            }
        }
    }
}

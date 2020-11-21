package chap.sys.java.week05bigtable;

import org.junit.Test;
import java.util.*;

/*-
Implement a standard bloom filter. Support the following method:

StandardBloomFilter(k) The constructor and you need to create k hash functions.
add(string) Add a string into bloom filter.
contains(string) Check a string whether exists in bloom filter.
*/
public class Standard_Bloom_Filter_556_a {
    @Test
    public void test() {
        StandardBloomFilter sbf = new StandardBloomFilter(10);
        sbf.add("hello");
        sbf.contains("hell");
        sbf.contains("helloa");
        sbf.contains("hello");
        sbf.contains("hell");
        sbf.contains("helloa");
        sbf.contains("hello");
    }

    /**
     * 您的提交打败了 26.40% 的提交!
     */
    public class StandardBloomFilter {
        private final int PRIMES[] = { 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109,
                113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199 };
        private List<HashFunc> hashs;
        private BitSet bits;
        private int k;  // # of hashes (about 15)

        /*
         * @param k: An integer
         */public StandardBloomFilter(int k) {
            // do intialization if necessary
            this.k = k;
            this.hashs = new ArrayList<>(k);
            for (int i = 0; i < k; i++) {
                hashs.add(new HashFunc(100000 + i, PRIMES[i]));
            }
            this.bits = new BitSet(100000 + k);
        }

        /*
         * @param word: A string
         *
         * @return: nothing
         */
        public void add(String word) {
            // write your code here
            for (int i = 0; i < k; i++) {
                bits.set(hashs.get(i).hash(word));
            }
        }

        /*
         * @param word: A string
         *
         * @return: True if contains word
         */
        public boolean contains(String word) {
            // write your code here
            for (int i = 0; i < k; i++) {
                if (!bits.get(hashs.get(i).hash(word))) {
                    System.out.println(false);
                    return false;
                }
            }
            System.out.println(true);
            return true;
        }

        class HashFunc {
            int cap, base;

            public HashFunc(int c, int b) {
                this.cap = c;
                this.base = b;
            }

            public int hash(String s) {
                int code = 0;
                for (int i = 0; i < s.length(); i++) {
                    code = code * base + (int) s.charAt(i);
                    code %= cap;
                }
                return code;
            }
        }
    }
}

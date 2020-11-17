package chap.sys.java.week04dbconsisthashing;

import org.junit.Test;

/*-
* Description

In data structure Hash, hash function is used to convert a string(or any other type) into an integer smaller than hash size and bigger or equal to zero. The objective of designing a hash function is to "hash" the key as unreasonable as possible. A good hash function can avoid collision as less as possible. A widely used hash function algorithm is using a magic number 33, consider any string as a 33 based big integer like follow:

hashcode("abcd") = (ascii(a) * 333 + ascii(b) * 332 + ascii(c) *33 + ascii(d)) % HASH_SIZE 

                              = (97* 333 + 98 * 332 + 99 * 33 +100) % HASH_SIZE

                              = 3595978 % HASH_SIZE

here HASH_SIZE is the capacity of the hash table (you can assume a hash table is like an array with index 0 ~ HASH_SIZE-1).

Given a string as a key and the size of hash table, return the hash value of this key.
*/
public class HashFunction_128_a {
    @Test public void test1() {
        Solution_2 sol = new Solution_2();
        System.out.println( sol.hashCode("abcd".toCharArray(), 1000) );
        System.out.println( sol.hashCode("a".toCharArray(), 1000) );
    }

    class Solution_2 {
        public int hashCode(char[] key, int HASH_SIZE) {
            int ans = 0;
            for (int i = 0; i < key.length; i++) {
                ans = (ans * 33 + key[i]) % HASH_SIZE;
            }
            return ans;
        }
    }

    class Solution_1 {
        /**
         * @param key: A string you should hash
         * @param HASH_SIZE: An integer
         * @return: An integer
         */
        public int hashCode(char[] key, int HASH_SIZE) {
            // write your code here
            int v = 0;
            int i;
            for (i = 0; i < key.length-1; i++) {
                v += key[i];
                v *= 33;
                v %= HASH_SIZE;
            }
            v = (v+key[i] ) % HASH_SIZE;
            return v;
        }
    }
}

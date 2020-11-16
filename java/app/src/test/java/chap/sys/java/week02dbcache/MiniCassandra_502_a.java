package chap.sys.java;

import java.util.*;

import org.junit.Test;

/*-
- Description:
Cassandra is a NoSQL database (a.k.a key-value storage). One individual data entry in cassandra constructed by 3 parts:

row_key. (a.k.a hash_key, partition key or sharding_key.)
column_key.
value
row_key is used to hash and can not support range query. Let's simplify this to a string.
column_key is sorted and support range query. Let's simplify this to integer.
value is a string. You can serialize any data into a string and store it in value.

Implement the following methods:

insert(row_key, column_key, value)
query(row_key, column_start, column_end) return a list of entries

*/
public class MiniCassandra_502_a {
    @Test public void test1() {
        MiniCassandra cassandra = new MiniCassandra();
        cassandra.insert("google", 1, "haha");
        cassandra.insert("lintcode", 1, "Good");
        cassandra.insert("google", 2, "hehe");
        System.out.println( cassandra.query("google", 0, 1) );
        System.out.println( cassandra.query("google", 0, 2) );
        System.out.println( cassandra.query("go", 0, 1) );
        System.out.println( cassandra.query("lintcode", 0, 10) );
    }

    /**
     * This implementation is slower, because each query needs tons of memory allocation.
     */
    class MiniCassandra {
        private Map<String, NavigableMap<Integer, String>> nosql;
        public MiniCassandra() {
            // do intialization if necessary
            this.nosql = new HashMap<>();
        }

        /*
         * @param raw_key: a string
         *
         * @param column_key: An integer
         * 
         * @param column_value: a string
         *
         * @return: nothing
         */
        public void insert(String row_key, int column_key, String value) {
            // write your code here
            nosql.putIfAbsent(row_key, new TreeMap<>());
            NavigableMap<Integer, String> row = nosql.get(row_key);
            row.put(column_key, value);
            // System.out.println(nosql);
        }

        /*
         * @param row_key: a string
         *
         * @param column_start: An integer
         * 
         * @param column_end: An integer
         * 
         * @return: a list of Columns
         */
        public List<Column> query(String row_key, int column_start, int column_end) {
            // write your code here
            List<Column> rt = new ArrayList<>();
            if (!nosql.containsKey(row_key)) {
                return rt;
            }
            SortedMap<Integer, String> subMap = nosql.get(row_key).subMap(column_start, true, column_end, true); // subMap(column_start, column_end);
            subMap.forEach((k,v) -> rt.add(new Column(k,v)));
            return rt;
        }
    }

    // Definition of Column:
    class Column {
        public int key;
        public String value;

        public Column(int key, String value) {
            this.key = key;
            this.value = value;
        }

		@Override
		public String toString() {
			return "Column [key=" + key + ", value=" + value + "]";
		}
    }
}

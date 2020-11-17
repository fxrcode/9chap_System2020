package chap.sys.java.week02dbcache;

import java.util.*;
import org.junit.Test;

/*-
您的提交打败了 52.80% 的提交!
By new Column in insert, we can speed up query since no memory allocation.
*/
public class MiniCassandra_502_b {
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

    class MiniCassandra {
        private Map<String, NavigableMap<Integer, Column>> nosql;
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
            NavigableMap<Integer, Column> row = nosql.get(row_key);
            row.put(column_key, new Column(column_key, value));
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
            SortedMap<Integer, Column> subMap = nosql.get(row_key).subMap(column_start, true, column_end, true); // subMap(column_start, column_end);
            rt.addAll(subMap.values());
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
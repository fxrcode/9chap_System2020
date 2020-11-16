package chap.sys.java;

import java.util.*;

import org.junit.Test;

/*-
* Description
Given a long url, make it shorter.

You should implement two methods:

longToShort(url) Convert a long url to a short url which starts with http://tiny.url/.
shortToLong(url) Convert a short url to a long url.
You can design any shorten algorithm, the judge only cares about two things:

The short key's length should equal to 6 (without domain and slash). And the acceptable characters are [a-zA-Z0-9]. For example: abcD9E
No two long urls mapping to the same short url and no two short urls mapping to the same long url.
 */
public class TinyURL_22_a {
    @Test public void test1() {
        TinyUrl tu = new TinyUrl();
        System.out.println( tu.shortToLong( tu.longToShort("http://www.lintcode.com/faq/?id=10") ) );
        System.out.println( tu.shortToLong( tu.longToShort("http://www.lintcode.com/faq/?id=10") ) );
    }

    /**
     * 您的提交打败了 4.80% 的提交!
     */
    class TinyUrl {
        private Map<String, String> short_to_long;
        private Map<String, String> long_to_short;
        private final static String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        private Random rand;
        private static final String prefix = "http://tiny.url/";

        public TinyUrl() {
            this.rand = new Random();
            short_to_long = new HashMap<>();
            long_to_short = new HashMap<>();
        }

        /*
         * @param url: a long url
         * @return: a short url starts with http://tiny.url/
         */
        public String longToShort(String url) {
            // write your code here
            StringBuilder sb = new StringBuilder();
            if (long_to_short.containsKey(url)) {
                // already converted, no need to redo
                return long_to_short.get(url);
            }
            while (true) {  // this is the slow part when more and more hash collision happened.
                String sixrand = convert(url);
                if (!short_to_long.containsKey(sixrand)) {
                    sb.append(prefix).append(sixrand);
                    System.out.println( "sixrand: " + sixrand);
                    short_to_long.put(sb.toString(), url);
                    long_to_short.put(url, sb.toString());
                    break;
                }
            }
            return sb.toString();
        }

        private String convert(String url) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                sb.append(chars.charAt( rand.nextInt(62) ) );
            }
            return sb.toString();
        }
    
        /*
         * @param url: a short url starts with http://tiny.url/
         * @return: a long url
         */
        public String shortToLong(String url) {
            // write your code here
            String six = url.substring(prefix.length());
            System.out.println( " substring: " + six );
            return short_to_long.get(url);
        }
    }
    
}

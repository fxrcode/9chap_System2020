package chap.sys.java.week10crawlergooglesuggestion;

import java.util.*;

import org.junit.Test;


public class Typeahead_231_b {

    @Test public void test() {
        Set<String> dict = Set.of("Jason Zhang", "James Yu", "Lee Zhang", "Yanny Li");
        Typeahead ta = new Typeahead(dict);
        System.out.println( ta.search("Zhang"));
        System.out.println( ta.search("James"));
    }

    @Test public void test2() {
        Set<String> dict = Set.of("San Zhang","Lisi","Li Ma","Jimmy Wang");
        Typeahead ta = new Typeahead(dict);
        System.out.println( ta.search("Li"));
    }

    // 您的提交打败了 54.80% 的提交!
    public class Typeahead {
        private Map<String, Set<String>> OrigsBySub = new HashMap<>();
        /*
        * @param dict: A dictionary of words dict
        */
        public Typeahead(Set<String> dict) {
            // do intialization if necessary
            for (String s : dict) {
                for (int i = 0; i < s.length(); i++) {
                    for (int j = i+1; j <= s.length(); j++) {
                        String sub = s.substring(i, j);
                        OrigsBySub.putIfAbsent(sub, new HashSet<>());
                        OrigsBySub.get(sub).add(s);
                    }
                }
            }
        }

        /*
         * @param str: a string
         * @return: a list of words
         */
        public List<String> search(String str) {
            // write your code here
            List<String> ans = new ArrayList<>();
            if (!OrigsBySub.containsKey(str)) {
                return ans;
            }
            ans.addAll(OrigsBySub.get(str));
            return ans;
        }
    }
}

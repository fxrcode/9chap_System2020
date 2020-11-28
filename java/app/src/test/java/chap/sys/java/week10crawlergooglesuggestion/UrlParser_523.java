package chap.sys.java.week10crawlergooglesuggestion;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import org.junit.Test;

// Parse a html page, extract the Urls in it. Use regex to parse html.
public class UrlParser_523 {
    // used Guava's Resources to read file into a string
    @Test
    public void test() throws IOException {
        String t1 = "UrlParser1.txt";
        String content = Resources.toString(Resources.getResource(t1), Charsets.UTF_8);
        String t2 = "UrlParser1.txt";
        String content2 = Resources.toString(Resources.getResource(t2), Charsets.UTF_8);
        HtmlParser parser = new HtmlParser();
        parser.parseUrls(content).forEach(System.out::println);
        parser.parseUrls(content2).forEach( u -> System.out.println( "[" + u + "]"));
    }

    public class HtmlParser {
        // https://regexr.com/
        // (?i) mode modifier, it means the remainder of expression is case insensitive
        // (abc) means capture group, it's for extracing a substring or using a backreference
        // [^xyz] means: Match any character that is NOT in the sat
        private final String HREF_REGEX = "\\s+(?i)href\\s*=\\s*(\"|')+([^\"'\\s]*)";

        /*
         * @param content: content source code
         * @return: a list of links
         */
        public List<String> parseUrls(String content) {
            // write your code here
            List<String> urls = new ArrayList<>();
            Pattern pattern = Pattern.compile(HREF_REGEX);
            Matcher matcher = pattern.matcher(content);
            String url = null;
            while (matcher.find()) {
                url = matcher.group(2);
                if (url.length() == 0 || url.startsWith("#"))
                    continue;
                urls.add(url);
            }
            return urls;
        }
    }
}

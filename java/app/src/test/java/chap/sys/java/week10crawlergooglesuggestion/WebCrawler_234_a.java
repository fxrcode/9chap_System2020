package chap.sys.java.week10crawlergooglesuggestion;

import java.net.URL;
import java.util.*;

/**
 * Single thread
 */
public class WebCrawler_234_a {
    public class Solution {
        /**
         * @param url: a url of root page
         * @return: all urls
         */
        public List<String> crawler(String rootURL) {
            // 单线程 超级简化版
            // write your code here
            Set<String> res = new HashSet<>();

            // 标准宽搜
            Queue<String> queue = new LinkedList<>();
            Set<String> set = new HashSet<>();
            set.add(rootURL);
            queue.offer(rootURL);
            while (!queue.isEmpty()) {
                String head = queue.poll();
                res.add(head);
                List<String> neighbors = HtmlHelper.parseUrls(head);
                for (String neighbor : neighbors) {
                    try {
                        URL neighborURL = new URL(neighbor); // 可能会抛出Exception
                        // 只爬取wikipedia的网页
                        if (!neighborURL.getHost().endsWith("wikipedia.org")) {
                            continue;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // 相同的页面不重复爬取
                    if (!set.contains(neighbor)) {
                        set.add(neighbor);
                        queue.offer(neighbor);
                    }
                }
            }

            return new ArrayList<String>(res);
        }
    }


    public static  class HtmlHelper {
        public static List<String> parseUrls(String url) {
            return null;
        }
        // Get all urls from a webpage of given url.
    }
}

package chap.sys.java.week10crawlergooglesuggestion;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.net.*;

/**
 * Multi-thread version
 */
public class WebCrawler_234_b {
    public class Solution {
        ExecutorService pool = Executors.newFixedThreadPool(3);;
        AtomicLong numTasks = new AtomicLong(0); // wait for all task to finish
        Lock lock = new ReentrantLock(); // to protect ans::List<String> and visited::Set<String>.
        List<String> ans = new ArrayList<>();
        Set<String> visited = new HashSet<>();

        /**
         * @param url: a url of root page
         * @return: all urls
         */
        public List<String> crawler(String url) {
            // write your code here
            visited.add(url);
            ans.add(url);
            pool.execute(new crawlTask(url));
            numTasks.incrementAndGet();
            try {
                // wait until no more tasks
                while (numTasks.get() != 0)
                    Thread.sleep(30);
            } catch (Exception e) {
                e.printStackTrace();
            }
            pool.shutdown(); // otherwise program won't stop
            return ans;
        }

        private class crawlTask implements Runnable {
            String url;

            public crawlTask(String u) {
                url = u;
            }

            @Override
            public void run() {
                try {
                    for (String neighbor : HtmlHelper.parseUrls(url)) {
                        URL neighborURL = new URL(neighbor);
                        if (!neighborURL.getHost().endsWith("wikipedia.org"))
                            continue; // may throw exception
                        lock.lock();
                        if (!visited.contains(neighbor)) { // found new URL to crawl
                            visited.add(neighbor);
                            ans.add(neighbor);
                            pool.execute(new crawlTask(neighbor));
                            numTasks.incrementAndGet();
                        }
                        lock.unlock();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    numTasks.decrementAndGet();
                }
            }
        }
    }

    public static  class HtmlHelper {
        public static List<String> parseUrls(String url) {
            return null;
        }
        // Get all urls from a webpage of given url.
    }

}

package chap.sys.java.week01intro;

import org.junit.Test;
import java.util.*;
// import java.util.logging.*;

public class MiniTwitter_501_a {
    // private final static Logger LOGGER = Logger.getLogger(MiniTwitter_501.class.getName());

    @Test
    public void testExp1() {
        MiniTwitter mt = new MiniTwitter();
        System.out.println(mt.postTweet(1, "LintCode is Good!!!"));
        System.out.println(mt.getNewsFeed(1));
        System.out.println(mt.getTimeline(1));
        mt.follow(2, 1);
        System.out.println(mt.getNewsFeed(2));
        mt.unfollow(2, 1);
        System.out.println(mt.getNewsFeed(2));

        // mt.debugU2F();
        // mt.debugU2T();
    }

    @Test
    public void testExp2() {
        MiniTwitter mt = new MiniTwitter();
        System.out.println(mt.postTweet(1, "LintCode is Good!!!"));
        System.out.println(mt.getNewsFeed(1));
        System.out.println(mt.getTimeline(1));
        mt.follow(2, 1);
        System.out.println(mt.postTweet(1, "LintCode is Best!!!"));
        System.out.println(mt.getNewsFeed(2));
        mt.unfollow(2, 1);
        System.out.println(mt.getNewsFeed(2));

        // mt.debugU2F();
        // mt.debugU2T();
    }

    /*-
     * https://stackoverflow.com/questions/2148686/non-public-top-level-class-vs-static-nested-class
     * - use static nested class if the class is only used by the outer class, rather create multiple class.
     * - use pull model, so it's slow since the testcase has O(10k) max followers.
     */
    class MiniTwitter {
        // ppl that user_id followed.
        private Map<Integer, Set<Integer>> user_to_followers;
        private Map<Integer, LinkedList<Node>> user_to_tweets;
        private static final int LATEST = 10;
        private long global_order; // CAVEAT

        public MiniTwitter() {
            this.user_to_followers = new HashMap<>();
            this.user_to_tweets = new HashMap<>();
            this.global_order = 0;
        }

        public void debugU2T() {
            user_to_tweets.forEach((u, t) -> System.out.println("user: " + u + ", tweets: " + t));
        }

        public void debugU2F() {
            user_to_followers.forEach((u, fs) -> {
                if (user_to_followers.containsKey(u)) {
                    System.out.println("user: " + u + ", followers: " + fs);
                } else {
                    System.out.println("user: " + u + " no follower!");
                }
            });
        }

        // still reference to original tmp, should be assumed for ALL Java
        // container methods!
        public List<Node> getRecent10(List<Node> tmp) {
            int last = 10;
            if (tmp.size() < LATEST) {
                last = tmp.size();
            }
            return tmp.subList(0, last);
        }

        /*
         * @param user_id: An integer
         * 
         * @param tweet_text: a string
         * 
         * @return: a tweet
         */
        public Tweet postTweet(int user_id, String tweet_text) {
            if (!user_to_tweets.containsKey(user_id)) {
                user_to_tweets.put(user_id, new LinkedList<>());
            }
            global_order++;
            Tweet tweet = Tweet.create(user_id, tweet_text);
            user_to_tweets.get(user_id).addFirst(new Node(global_order, tweet));
            return tweet;
        }

        /*
         * @param user_id: An integer
         * 
         * @return: a list of 10 new feeds recently and sort by timeline
         */
        public List<Tweet> getNewsFeed(int user_id) {
            // write your code here
            List<Node> tmp = new ArrayList<>();
            if (user_to_tweets.containsKey(user_id)) {
                tmp.addAll(getRecent10(user_to_tweets.get(user_id)));
            }
            // get recent10 from all followers
            if (user_to_followers.containsKey(user_id)) {
                for (Integer f_id : user_to_followers.get(user_id)) {
                    if (user_to_tweets.containsKey(f_id)) {
                        tmp.addAll(getRecent10(user_to_tweets.get(f_id)));
                    }
                }
            }
            Collections.sort(tmp);
            tmp = getRecent10(tmp);
            List<Tweet> rt = new ArrayList<>();
            tmp.forEach( node -> rt.add(node.tweet));
            return rt;
        }

        /*
         * @param user_id: An integer
         * 
         * @return: a list of 10 new posts recently and sort by timeline
         */
        public List<Tweet> getTimeline(int user_id) {
            // write your code here
            List<Node> tmp = new ArrayList<>();
            if (user_to_tweets.containsKey(user_id)) {
                tmp.addAll(getRecent10(user_to_tweets.get(user_id)));
            }
            List<Tweet> rt = new ArrayList<>();
            tmp.forEach( node -> rt.add(node.tweet));
            return rt;
        }

        /*
         * @param from_user_id: An integer
         *
         * @param to_user_id: An integer
         *
         * @return: nothing
         */
        public void follow(int from_user_id, int to_user_id) {
            // write your code here
            if (!user_to_followers.containsKey(from_user_id)) {
                user_to_followers.put(from_user_id, new HashSet<Integer>());
            }
            user_to_followers.get(from_user_id).add(to_user_id);
        }

        /*
         * @param from_user_id: An integer
         *
         * @param to_user_id: An integer
         *
         * @return: nothing
         */
        public void unfollow(int from_user_id, int to_user_id) {
            // write your code here
            if (user_to_followers.containsKey(from_user_id)) {
                user_to_followers.get(from_user_id).remove(to_user_id);
            }
        }
    }

    // Definition of Tweet:
    static class Tweet {
        public int id;
        public int user_id;
        public String text;

        public Tweet(int user_id, String tweet_text) {
            this.id = 0;
            this.user_id = user_id;
            this.text = tweet_text;
        }

        public static Tweet create(int user_id, String tweet_text) {
            // This will create a new tweet object,
            // and auto fill id
            return new Tweet(user_id, tweet_text);
        }

        @Override
        public String toString() {
            return "Tweet [id=" + id + ", text=" + text + ", user_id=" + user_id + "]";
        }
    }

    class Node implements Comparable<Node> {
        private long order;
        private Tweet tweet;
        private Date date;

        public Node(long order, Tweet t) {
            this.order = order;
            this.tweet = t;
            this.date = new Date();
        }

        public String getMsg() {
            return this.tweet.text;
        }

        @Override
        public int compareTo(Node other) {
            if (this.order < other.order) {
                return 1;
            } else {
                return -1;
            }
        }

        @Override
        public String toString() {
            return "Node [date=" + date + ", order=" + order + ", tweet=" + tweet + "]";
        }
    }
}

// DON'T do this
// class BADMiniTwitter {
// }
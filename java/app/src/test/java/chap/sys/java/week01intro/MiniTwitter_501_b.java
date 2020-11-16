package chap.sys.java.week01intro;

import org.junit.Test;
import java.util.*;
// import java.util.logging.*;

public class MiniTwitter_501_b {
    private static int global_order = 0;

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
     * - use push model, it's responsive and server has O(10k) call is OK.
     */
    class MiniTwitter {
        // Big-V's followers.
        private Map<Integer, Set<Integer>> followersByVid;
        private Map<Integer, List<Tweet>> timelinesByUserid;
        private Map<Integer, List<Tweet>> newsfeedsByUserid;
        // private static final int LATEST = 10;

        public MiniTwitter() {
            this.followersByVid = new HashMap<>();
            this.timelinesByUserid = new HashMap<>();
            this.newsfeedsByUserid = new HashMap<>();
        }

        // Return References should be assumed for ALL Java container methods!
        public List<Tweet> getRecent10(List<Tweet> tmp) {
            List<Tweet> rt = new ArrayList<>(10);
            int sz = tmp.size();
            for (int i = sz - 1; i >= sz - 10 && i >= 0; i--) {
                rt.add(tmp.get(i));
            }
            return rt;
        }

        public Comparator<Tweet> tComparator = new Comparator<Tweet>() {
            @Override
            public int compare(Tweet left, Tweet right) {
                return left.id - right.id;
            }

        };

        /*
         * @param user_id: An integer
         * 
         * @param tweet_text: a string
         * 
         * @return: a tweet
         */
        public Tweet postTweet(int user_id, String tweet_text) {
            Tweet t = Tweet.create(user_id, tweet_text);
            timelinesByUserid.putIfAbsent(user_id, new ArrayList<>());
            timelinesByUserid.get(user_id).add(t);
            followersByVid.putIfAbsent(user_id, new HashSet<>());
            followersByVid.get(user_id).add(user_id);
            for (Integer f_id : followersByVid.get(user_id)) {
                newsfeedsByUserid.putIfAbsent(f_id, new ArrayList<>());
                newsfeedsByUserid.get(f_id).add(t);
            }
            return t;
        }

        /*
         * @param user_id: An integer
         * 
         * @return: a list of 10 new feeds recently and sort by timeline
         */
        public List<Tweet> getNewsFeed(int user_id) {
            // write your code here
            List<Tweet> tmp = new ArrayList<>();
            if (!newsfeedsByUserid.containsKey(user_id) || newsfeedsByUserid.get(user_id).size() == 0) {
                return tmp;
            }
            tmp.addAll(getRecent10(newsfeedsByUserid.get(user_id)));
            return tmp;
        }

        /*
         * @param user_id: An integer
         * 
         * @return: a list of 10 new posts recently and sort by timeline
         */
        public List<Tweet> getTimeline(int user_id) {
            // write your code here
            List<Tweet> tmp = new ArrayList<>();
            if (!timelinesByUserid.containsKey(user_id) || timelinesByUserid.get(user_id).size() == 0) {
                return tmp;
            }
            tmp.addAll(getRecent10(timelinesByUserid.get(user_id)));
            return tmp;
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
            followersByVid.putIfAbsent(to_user_id, new HashSet<>());
            followersByVid.get(to_user_id).add(to_user_id);
            followersByVid.get(to_user_id).add(from_user_id);
            // fetch to_user_id's timeline and add to from_user_id's newsfeed, and order it!
            newsfeedsByUserid.putIfAbsent(from_user_id, new ArrayList<>());
            if (!timelinesByUserid.containsKey(to_user_id)) {
                return;
            }
            List<Tweet> bigVTweets = timelinesByUserid.get(to_user_id);
            bigVTweets.forEach(tweet -> newsfeedsByUserid.get(from_user_id).add(tweet));
            Collections.sort(newsfeedsByUserid.get(from_user_id), tComparator);
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
            if (!followersByVid.containsKey(to_user_id) || followersByVid.get(to_user_id).size() == 0) {
                return;
            }
            followersByVid.get(to_user_id).remove(from_user_id);
            if (!newsfeedsByUserid.containsKey(from_user_id)) {
                return;
            }
            List<Tweet> vTweets = newsfeedsByUserid.get(from_user_id);
            vTweets.removeIf(t -> (t.user_id == to_user_id));
            Collections.sort(newsfeedsByUserid.get(from_user_id), tComparator);
        }
    }

    // Definition of Tweet:
    static class Tweet {
        // solution with push model and only Tweet static nested class, rather Node
        // inner class have pre-assumption: auto-fill id is global incremental. So I
        // don't like it.
        public int id;
        public int user_id;
        public String text;

        public Tweet(int user_id, String tweet_text) {
            this.id = global_order++;
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
}

// DON'T do this
// class BADMiniTwitter {
// }
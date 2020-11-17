package chap.sys.java.week02dbcache;

import java.util.*;
import org.junit.Test;

/*-
 * Your "following" list contains all the users you follow on Twitter; your
 * "followers" list contains the users who follow you.
 * -- use map.putIfAbsent to init value
 * -- no need to init in unfollow
 * -- be careful on the order of follow. it's quite confusing.
 * -- nested static class or inner class should not have public/private access control.
 */

public class FriendshipService_560_a {

    @Test
    public void test1() {
        FriendshipService service = new FriendshipService();
        service.follow(1,3);
        System.out.println(service.getFollowers(1));
        System.out.println(service.getFollowings(3));
        service.follow(2,3);
        System.out.println(service.getFollowings(3));
        service.unfollow(1,3);
        System.out.println(service.getFollowings(3));
    }

    @Test
    public void test2() {
        FriendshipService service = new FriendshipService();
        service.follow(1,2);
        service.unfollow(1, 2);
        System.out.println(service.getFollowings(1));
        service.unfollow(1, 2);
        System.out.println(service.getFollowings(1));
        service.unfollow(1,2);
        service.follow(1,2);
        System.out.println(service.getFollowings(1));
    }

    class FriendshipService {
        private Map<Integer, Set<Integer>> followers;
        private Map<Integer, Set<Integer>> followings;

        public FriendshipService() {
            // do intialization if necessary
            followers = new HashMap<>();
            followings = new HashMap<>();
        }

        /*
         * @param user_id: An integer
         * 
         * @return: all followers and sort by user_id
         */
        public List<Integer> getFollowers(int user_id) {
            // write your code here
            List<Integer> rt = new ArrayList<>();
            if (followers.containsKey(user_id)) {
                followers.get(user_id).forEach(follower -> rt.add(follower));
            }
            return rt;
        }

        /*
         * @param user_id: An integer
         * 
         * @return: all followings and sort by user_id
         */
        public List<Integer> getFollowings(int user_id) {
            // write your code here
            List<Integer> rt = new ArrayList<>();
            if (followings.containsKey(user_id)) {
                followings.get(user_id).forEach(following -> rt.add(following));
            }
            return rt;
        }

        /*
         * @param from_user_id: An integer
         * 
         * @param to_user_id: An integer
         * 
         * @return: nothing
         */
        public void follow(int to_user_id, int from_user_id) {
            // write your code here
            followers.putIfAbsent(to_user_id, new TreeSet<>());
            followers.get(to_user_id).add(from_user_id);
            followings.putIfAbsent(from_user_id, new TreeSet<>());
            followings.get(from_user_id).add(to_user_id);
        }

        /*
         * @param from_user_id: An integer
         * 
         * @param to_user_id: An integer
         * 
         * @return: nothing
         */
        public void unfollow(int to_user_id, int from_user_id) {
            // write your code here
            if (followers.containsKey(to_user_id)) {
                followers.get(to_user_id).remove(from_user_id);
            }
            if (followings.containsKey(from_user_id)) {
                followings.get(from_user_id).remove(to_user_id);
            }
        }
    }
}

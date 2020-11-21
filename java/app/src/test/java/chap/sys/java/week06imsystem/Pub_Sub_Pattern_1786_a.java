package chap.sys.java.week06imsystem;

import org.junit.Test;
import java.util.*;

/*-
Pub/Sub pattern is a wide used pattern in system design. In this problem, you need to implement a pub/sub pattern to support user subscribes on a specific channel and get notification messages from subscribed channels.

There are 3 methods you need to implement:

subscribe(channel, user_id): Subscribe the given user to the given channel.
unsubscribe(channel, user_id): Unsubscribe the given user from the given channel.
publish(channel, message): You need to publish the message to the channel so that everyone subscribed on the channel will receive this message. Call PushNotification.notify(user_id, message) to push the message to the user.
*/
public class Pub_Sub_Pattern_1786_a {
    @Test
    public void test() {
        PubSubPattern ps = new PubSubPattern();

        ps.subscribe("group1",  1);
        ps.publish("group1", "hello");
        ps.subscribe("group1", 2);
        ps.publish("group1", "thank you");
        ps.unsubscribe("group2", 3);
        ps.unsubscribe("group1", 1);
        ps.publish("group1", "thank you very much");
        ps.publish("group2", "are you ok?");
    }

    /*
     * Definition of PushNotification class PushNotification { public static void
     * notify(int user_id, String the_message) };
     */
    public class PubSubPattern {
        private Map<String, Set<Integer>> channels;
        public PubSubPattern() {
            // Write your code here
            this.channels = new HashMap<>();
        }

        /**
         * @param channel: the channel's name
         * @param user_id: the user who subscribes the channel
         * @return: nothing
         */
        public void subscribe(String channel, int user_id) {
            // Write your code here
            channels.putIfAbsent(channel, new HashSet<>());
            channels.get(channel).add(user_id);
        }

        /**
         * @param channel: the channel's name
         * @param user_id: the user who unsubscribes the channel
         * @return: nothing
         */
        public void unsubscribe(String channel, int user_id) {
            // Write your code here
            if (!channels.containsKey(channel))
                return;
            channels.get(channel).remove(user_id);
        }

        /**
         * @param channel: the channel's name
         * @param message: the message need to be delivered to the channel's subscribers
         * @return: nothing
         */
        public void publish(String channel, String message) {
            // Write your code here
            if (!channels.containsKey(channel))
                return;
            for(int user : channels.get(channel)) {
                PushNotification.notify(user, message);
            }
        }
    }

    public static class PushNotification {
        public static void notify(int user_id, String the_message) {
            System.out.println(user_id + "<<" + the_message);
        }
    }
}

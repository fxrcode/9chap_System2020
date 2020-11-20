package chap.sys.java.week04dbconsisthashing;

import java.util.*;

import org.junit.Test;

/*-
 * Question
Implement a rate limiter, provide one method: is_ratelimited(timestamp, event, rate, increment).

timestamp: The current timestamp, which is an integer and in second unit.
event: The string to distinct different event. for example, "login" or "signup".
rate: The rate of the limit. 1/s (1 time per second), 2/m (2 times per minute), 10/h (10 times per hour), 100/d (100 times per day). The format is [integer]/[s/m/h/d].
increment: Whether we should increase the counter. (or take this call as a hit of the given event)、
The method should return true or false to indicate the event is limited or not.
Note: Login failure is not recorded in login times.
 */
public class Rate_Limiter_215_a {
    @Test
    public void test() {
        Solution s = new Solution();
        s.is_ratelimited(1, "login", "3/m", true);
        s.is_ratelimited(11, "login", "3/m", true);
        s.is_ratelimited(21, "login", "3/m", true);
        s.is_ratelimited(30, "login", "3/m", true);
        s.is_ratelimited(65, "login", "3/m", true);
        s.is_ratelimited(300, "login", "3/m", true);
    }

    public class Solution {
        Map<String, List<Integer>> map = new HashMap<>();
        /*
         * @param timestamp: the current timestamp
         *
         * @param event: the string to distinct different event
         *
         * @param rate: the format is [integer]/[s/m/h/d]
         *
         * @param increment: whether we should increase the counter
         *
         * @return: true or false to indicate the event is limited or not
         */
        public boolean is_ratelimited(int timestamp, String event, String rate, boolean increment) {
            // write your code here
            int sep_idx = rate.indexOf("/");
            int limit = Integer.parseInt(rate.substring(0, sep_idx));
            String unit = rate.substring(sep_idx+1, rate.length());
            int duration = 1;
            switch (unit) {
                case "m":
                    duration = duration * 60;
                    break;
                case "h":
                    duration = duration * 60 * 60;
                    break;
                case "d":
                    duration = duration * 60 * 60 * 24;
                    break;
                default:
                    break;
            }
            int start_ts = timestamp - duration + 1;

            map.putIfAbsent(event, new ArrayList<>());
            int count = count_events(map.get(event), start_ts);
            boolean ratelimited = count >= limit;
            if (increment && !ratelimited) {
                insert_event(map.get(event), timestamp);
            }
            // System.out.println( ratelimited );
            return ratelimited;
        }
		
        private void insert_event(List<Integer> events, int timestamp) {
            events.add(timestamp);
        }

        /**
         * Binary search to find first event index that's >= start_ts
         */
        private int count_events(List<Integer> events, int start_ts) {
            int l = 0, r = events.size() - 1;
            if (r == -1)
                return 0;
            if (events.get(r) < start_ts)
                return 0;
            int b = 0;
            while (l + 1 < r) {
                int mid = (l+r) / 2;
                if (events.get(mid) >= start_ts) {
                    r = mid;
                } else {
                    l = mid;
                }
            }
            if (events.get(r) >= start_ts) {
                b = r;
            }
            if (events.get(l) >= start_ts) {
                b = l;
            }
            int cnt =  events.size() - 1 - b + 1;
            // System.out.println(cnt);
            return cnt;
		}


    }
}

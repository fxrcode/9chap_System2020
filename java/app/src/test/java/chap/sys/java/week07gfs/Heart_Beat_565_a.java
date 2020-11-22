package chap.sys.java.week07gfs;

import java.util.*;

import org.junit.Test;


/*-
* Description
In the Master-Slave architecture, slave server will ping master in every k seconds to tell master server he is alive. If a master server didn't receive any ping request from a slave server in 2 * k seconds, the master will trigger an alarm (for example send an email) to administrator.

Let's mock the master server, you need to implement the following three methods:

initialize(slaves_ip_list, k). salves_ip_list is a list of slaves' ip addresses. k is define above.
ping(timestamp, slave_ip). This method will be called every time master received a ping request from one of the slave server. timestamp is the current timestamp in seconds. slave_ip is the ip address of the slave server who pinged master.
getDiedSlaves(timestamp). This method will be called periodically (it's not guaranteed how long between two calls). timestamp is the current timestamp in seconds, and you need to return a list of slaves' ip addresses that died. Return an empty list if no died slaves found.
You can assume that when the master started, the timestamp is 0, and every method will be called with an global increasing timestamp.
*/
public class Heart_Beat_565_a {
    @Test public void test() {
        HeartBeat h = new HeartBeat();
        h.initialize(Arrays.asList("10.173.0.2", "10.173.0.3"), 10);
        h.ping(1, "10.173.0.2");
        h.getDiedSlaves(20);
        h.getDiedSlaves(21);
        h.ping(22, "10.173.0.2");
        h.ping(23, "10.173.0.3");
        h.getDiedSlaves(24);
        h.getDiedSlaves(42);
    }
    public class HeartBeat {
        private HashMap<String, Integer> lastpingByIp;
        private int k;

        public HeartBeat() {
            // do intialization if necessary
            this.lastpingByIp = new HashMap<>();
        }

        /*
         * @param slaves_ip_list: a list of slaves'ip addresses
         * @param k: An integer
         * @return: nothing
         */
        public void initialize(List<String> slaves_ip_list, int k) {
            // write your code here
            this.k = k;
            slaves_ip_list.stream().forEach( ip -> lastpingByIp.put(ip, 0) );
        }

        /*
         * @param timestamp: current timestamp in seconds
         * @param slave_ip: the ip address of the slave server
         * @return: nothing
         */
        public void ping(int timestamp, String slave_ip) {
            // write your code here
            if ( !lastpingByIp.containsKey(slave_ip) )
                return;
            lastpingByIp.put(slave_ip, timestamp);
        }

        /*
         * @param timestamp: current timestamp in seconds
         * @return: a list of slaves'ip addresses that died
         */
        public List<String> getDiedSlaves(int timestamp) {
            // write your code here
            List<String> ret = new ArrayList<>();
            lastpingByIp.forEach( (ip, ts) -> {
                if (ts <= timestamp - 2*k) {
                    ret.add(ip);
                }
            });
            System.out.println(ret);
            return ret;
        }
    }
}

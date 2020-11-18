package chap.sys.java.week04dbconsisthashing;

import org.junit.Test;

import java.util.*;
public class Load_Balancer_526_b {
    @Test
    public void test2() {
        LoadBalancer s = new LoadBalancer();
        s.add(406);
        s.add(347);
        s.remove(406);
        s.pick();
        s.pick();
        s.add(472);
        s.pick();
        s.pick();
        s.remove(347);
        s.remove(472);
        s.add(595);
        s.remove(595);
        s.add(200);
        s.remove(200);
        s.add(38);
        s.add(341);
        s.remove(341);
        s.pick();
        s.remove(38);
    }

    public class LoadBalancer {
        int n = 0;
        Map<Integer, Integer> pos = new HashMap<>();
        List<Integer> cluster = new ArrayList<>();
        Random rand = new Random();

        public LoadBalancer() {
            // do intialization if necessary
        }

        /*
         * @param server_id: add a new server to the cluster
         * @return: nothing
         */
        public void add(int server_id) {
            // write your code here
            if (!pos.containsKey(server_id)) {
                cluster.add(server_id);
                pos.put(server_id, n);
                n++;
            }
        }

        /*
         * @param server_id: server_id remove a bad server from the cluster
         * @return: nothing
         */
        public void remove(int server_id) {
            // write your code here
            if (pos.containsKey(server_id)) {
                int last_server = cluster.get(n-1);
                int remove_idx = pos.get(server_id);

                pos.put(last_server, remove_idx);
                cluster.set(remove_idx , last_server);

                pos.remove(server_id);
                cluster.remove(n-1);
                n--;
            }
            System.out.println(pos);
        }

        /*
         * @return: pick a server in the cluster randomly with equal probability
         */
        public int pick() {
            // write your code here
            return cluster.get(rand.nextInt(n));
        }
    }
}

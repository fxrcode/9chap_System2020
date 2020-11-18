package chap.sys.java.week04dbconsisthashing;

import org.junit.Test;

import java.util.*;

/**
 * Implement a load balancer for web servers. It provide the following
 * functionality:
 * 
 * Add a new server to the cluster => add(server_id). Remove a bad server from
 * the cluster => remove(server_id). Pick a server in the cluster randomly with
 * equal probability => pick(). At beginning, the cluster is empty. When pick()
 * is called you need to randomly return a server_id in the cluster.
 */
public class Load_Balancer_526_a {
    @Test
    public void test() {
        LoadBalancer s = new LoadBalancer();
        s.add(1);
        s.add(2);
        s.add(3);
        System.out.println(s.pick());
        System.out.println(s.pick());
        System.out.println(s.pick());
        System.out.println(s.pick());
        s.remove(1);
        System.out.println(s.pick());
        System.out.println(s.pick());
        System.out.println(s.pick());
    }

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

    /**
     * I thought it must be related to Consist hashing, but seems over-complicated
     * this question. Just need to pick random one
     */
    public class LoadBalancer {
        private List<Integer> cluster = new ArrayList<>();
        private Map<Integer, Integer> serversByid = new HashMap<>();
        private Random rand = new Random();

        public LoadBalancer() {
            // do intialization if necessary
        }

        /*
         * @param server_id: add a new server to the cluster
         *
         * @return: nothing
         */
        public void add(int server_id) {
            // write your code here
            if (!serversByid.containsKey(server_id)) {
                cluster.add(server_id);
                serversByid.put(server_id, cluster.size() - 1);
            }
        }

        /*
         * @param server_id: server_id remove a bad server from the cluster
         *
         * @return: nothing
         */
        public void remove(int server_id) {
            // write your code here
            if (serversByid.containsKey(server_id)) {
                int sz = cluster.size();
                int idx = serversByid.get(server_id);
                int last_server = cluster.get(sz - 1);
                serversByid.remove(last_server);
                serversByid.remove(server_id);
                if (idx != sz - 1) {
                    cluster.set(idx, last_server);
                    serversByid.put(last_server, idx);
                    cluster.remove(sz - 1);
                } else {
                    cluster.remove(sz - 1);
                }
                // System.out.println(serversByid);
            }
        }

        /*
         * @return: pick a server in the cluster randomly with equal probability
         */
        public int pick() {
            // write your code here
            int rt = cluster.get(rand.nextInt(cluster.size()));
            System.out.println(rt);
            return rt;
        }
    }
}

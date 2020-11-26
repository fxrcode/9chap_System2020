package chap.sys.java.week08lbs;

import java.util.*;

import org.junit.Test;

public class Mini_Yelp_509_b {

    @Test public void test2() {
        MiniYelp yelp = new MiniYelp();
        yelp.addRestaurant("cafe6", 41.003, 66.088);
        yelp.neighbors(41.004, 66.031, 92.592);
        yelp.addRestaurant("cafe7", 41.017, 66.024);
        yelp.addRestaurant("cafe8", 41.022, 66.074);
        yelp.removeRestaurant(3);
        yelp.addRestaurant("cafe9", 41.011, 66.014);
        yelp.removeRestaurant(2);
        yelp.addRestaurant("cafe10", 41.023, 66.114);
        yelp.addRestaurant("cafe11", 41.016, 66.01);
        yelp.removeRestaurant(1);
        yelp.addRestaurant("cafe12", 41.016, 66.118);
        yelp.removeRestaurant(4);
        yelp.removeRestaurant(6);
        yelp.addRestaurant("cafe13", 41.005, 66.035);
        yelp.removeRestaurant(8);
        yelp.addRestaurant("cafe14", 41.007, 66.054);
        yelp.removeRestaurant(7);
        yelp.removeRestaurant(5);
        yelp.addRestaurant("cafe15", 41.001, 66.011);
        yelp.removeRestaurant(9);
        yelp.addRestaurant("cafe16", 41.01, 66.09);
        yelp.neighbors(41.003, 66.096, 93.126);
    }

    /**
     * 您的提交打败了 9.20% 的提交!
     */
    public class MiniYelp {
        public NavigableMap<String, Restaurant> restaurants = new TreeMap<>(); // RestaurantByHash;
        public Map<Integer, String> ids = new HashMap<>();  // HashById;
        public MiniYelp() {
            // initialize your data structure here.
        }

        // @param name a string
        // @param location a Location
        // @return an integer, restaurant's id
        public int addRestaurant(String name, Location location) {
            // Write your code here
            Restaurant r = Restaurant.create(name, location);
            String hash = GeoHash.encode(r.location) + "." + r.id;
            ids.put(r.id, hash);
            restaurants.put(hash, r);
            return r.id;
        }

        public int addRestaurant(String name, double lati, double longi) {
            return addRestaurant(name, Location.create(lati, longi));
        }

        // @param restaurant_id an integer
        public void removeRestaurant(int restaurant_id) {
            // Write your code here
            if (ids.containsKey(restaurant_id)) {
                String hash = ids.get(restaurant_id);
                ids.remove(restaurant_id);
                if (restaurants.containsKey(hash)) {
                    restaurants.remove(hash);
                }
            }
        }

        // @param location a Location
        // @param k an integer, distance smaller than k miles
        // @return a list of restaurant's name and sort by
        // distance from near to far.
        public List<String> neighbors(Location location, double k) {
            // Write your code here
            int precision = get_length(k);
            String hash_target = GeoHash.encode(location).substring(0, precision);
            List<Map.Entry<Double, Restaurant>> results = new ArrayList<>();

            // learn how to use TreeMap.subMap and GeoHash, lower = hash, higher = hash+'{', both inclusive.
            for (Map.Entry<String, Restaurant> kv : restaurants.subMap(hash_target, true, hash_target+"{", true).entrySet() ) {
                Restaurant rest = kv.getValue();
                double dist = Helper.get_distance(location, rest.location);
                if (dist <= k) {
                    results.add( new AbstractMap.SimpleEntry<Double, Restaurant>(dist, rest) );// Java 9+: Map.entry(dist, rest) );
                }
            }
            results.sort( (a,b) -> {
                double d_a = a.getKey();
                double d_b = b.getKey();
                if (d_a < d_b ) {
                    return -1;
                } else if (d_a > d_b) {
                    return 1;
                } else {
                    return 0;
                }
            });
            // results.forEach( r -> System.out.println(r.getValue().name + ": " + r.getKey()));

            List<String> ans = new ArrayList<>();
            results.forEach( ent -> ans.add(ent.getValue().name));
            return ans;
        }

        public List<String> neighbors(double lati, double longi, double k) {
            List<String> ans = neighbors(Location.create(lati, longi), k);
            ans.forEach(n -> System.out.print(n + ","));
            System.out.println();
            return ans;
        }

        private int get_length(double k) {
            double[] ERROR = {2500, 630, 78, 20, 2.4, 0.61, 0.076, 0.01911, 0.00478, 0.0005971, 0.0001492, 0.0000186};
            for (int i = 0; i < 12; ++i)
                if (k  > ERROR[i])
                    return i;
            return 12;
        }
    };

    /*****************************************************************
     * All given class defined and ready to use
     */

    // Definition of Location:
    static class Location {
        public double latitude, longitude;
        
        public Location(double lati, double longi) {
            this.latitude = lati;
            this.longitude = longi;
        }

        public static Location create(double lati, double longi) {
            // This will create a new location object
            return new Location(lati, longi);
        }
    };
    // Definition of Restaurant
    static class Restaurant {
        public int id;
        public String name;
        public Location location;
        private Random rand = new Random();

        public Restaurant(String n, Location l) {
            id = rand.nextInt();
            name = n;
            location = l;
        }

        public static Restaurant create(String name, Location location) {
            // This will create a new restaurant object,
            // and auto fill id
            return new Restaurant(name, location);
        }
    };
    // Definition of Helper
    static class Helper {
        private static final Random rand = new Random();
        public static double get_distance(Location location1, Location location2) {
            // return distance between location1 and location2.
            return 100 * rand.nextDouble();
        }
    };
    static class GeoHash {
        static chap.sys.java.week08lbs.GeoHash_529 a = new GeoHash_529();
        static chap.sys.java.week08lbs.GeoHash_529.GeoHash geo = a.new GeoHash();
        public static String encode(Location location) {
            // return convert location to a GeoHash string
            return geo.encode(location.latitude, location.longitude, 12);
        }
        public static Location decode(String hashcode) {
             // return convert a GeoHash string to location
             return null;
        }
    };

}

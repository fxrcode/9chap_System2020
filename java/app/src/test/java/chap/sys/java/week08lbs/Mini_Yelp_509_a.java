package chap.sys.java.week08lbs;

import java.util.*;

/*-
Design a simple yelp system. Support the following features:

1. Add a restaurant with name and location.
2. Remove a restaurant with id.
3. Search the nearby restaurants by given location.
A location is represented by latitude and longitude, both in double. A Location class is given in code.

Nearby is defined by distance smaller than k Km .

Restaurant class is already provided and you can directly call Restaurant.create() to create a new object. Also, a Helper class is provided to calculate the distance between two location, use Helper.get_distance(location1, location2).

A GeoHash class is provided to convert a location to a string. Try GeoHash.encode(location) to convert location to a geohash string and GeoHash.decode(string) to convert a string to location.
*/
public class Mini_Yelp_509_a {
    public class MiniYelp {
        Map<Integer, Restaurant> restaurants = new HashMap<>();
        public MiniYelp() {
            // initialize your data structure here.
        }

        // @param name a string
        // @param location a Location
        // @return an integer, restaurant's id
        public int addRestaurant(String name, Location location) {
            // Write your code here
            Restaurant restaurant = Restaurant.create(name, location);
            restaurants.putIfAbsent(restaurant.id, restaurant);
            for( Map.Entry<Integer, Restaurant> kv : restaurants.entrySet()) {
                Restaurant r = kv.getValue();
                if (r.name == name && r.location.equals(location)) {
                    return kv.getKey();
                }
            }
            return -1;  // should be here
        }

        // @param restaurant_id an integer
        public void removeRestaurant(int restaurant_id) {
            // Write your code here
            restaurants.remove(restaurant_id);
        }

        // @param location a Location
        // @param k an integer, distance smaller than k miles
        // @return a list of restaurant's name and sort by
        // distance from near to far.
        public List<String> neighbors(Location location, double k) {
            // Write your code here
            List<String> names = new ArrayList<>();
            for (Map.Entry<Integer, Restaurant> kv : restaurants.entrySet()) {
                Restaurant r = kv.getValue();
                if (Helper.get_distance(r.location, location) < k) {
                    names.add(r.name);
                }
            }
            return names;
        }
    };

    /*****************************************************************
     * All given class defined and ready to use
     */

    // Definition of Location:
    static class Location {
        public double latitude, longitude;
        public static Location create(double lati, double longi) {
            // This will create a new location object
            return null;
        }
    };
    // Definition of Restaurant
    static class Restaurant {
        public int id;
        public String name;
        public Location location;
        public static Restaurant create(String name, Location location) {
            // This will create a new restaurant object,
            // and auto fill id
            return null;
        }
    };
    // Definition of Helper
    static class Helper {
        public static double get_distance(Location location1, Location location2) {
            // return distance between location1 and location2.
            return 0.0;
        }
    };
    static class GeoHash {
        public static String encode(Location location) {
            // return convert location to a GeoHash string
            return "enc";
        }
        public static Location decode(String hashcode) {
             // return convert a GeoHash string to location
             return null;
        }
    };

}

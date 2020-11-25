package chap.sys.java.week08lbs;

import java.util.*;

import com.google.common.collect.Maps;

/*-
Support two basic uber features:
1. Drivers report their locations.
2. Rider request a uber, return a matched driver.

When rider request a uber, match a closest available driver with him, then mark the driver not available.

When next time this matched driver report his location, return with the rider's information.

You can implement it with the following instructions:

* report(driver_id, lat, lng)
    - return null if no matched rider.
    - return matched trip information.
* request(rider_id, lat, lng)
    - create a trip with rider's information.
    - find a closest driver, mark this driver not available.
    - fill driver_id into this trip.
    - return trip

This is the definition of Trip in Java:
public class Trip {
    public int id; // trip's id, primary key
    public int driver_id, rider_id; // foreign key
    public double lat, lng; // pick up location
}
*/
public class Mini_Uber_525 {

    /*
    您的提交打败了 90.60% 的提交!
     */
    public class MiniUber {
        private Map<Integer, Loc> GeoByDriverid = new HashMap<>();  // Redis. All availble Driver for matching
        private Map<Integer, Trip> TripByDriverid= new HashMap<>();   // SQL. All matched driver to trip

        public MiniUber() {
            // initialize your data structure here.
        }

        // @param driver_id an integer
        // @param lat, lng driver's location
        // return matched trip information if there have matched rider or null
        public Trip report(int driver_id, double lat, double lng) {
            // Write your code here
            if(TripByDriverid.containsKey(driver_id)) {
                return TripByDriverid.get(driver_id);
            }
            // update loc
            GeoByDriverid.putIfAbsent(driver_id, new Loc(lat, lng));
            GeoByDriverid.get(driver_id).lat = lat;
            GeoByDriverid.get(driver_id).lng = lng;
            return null;
        }

        // @param rider_id an integer
        // @param lat, lng rider's location
        // return a trip
        public Trip request(int rider_id, double lat, double lng) {
            // Write your code here
            return dispatch(rider_id, lat, lng);
        }

        private Trip dispatch(int rider_id, double lat, double lng) {
            Trip trip = new Trip(rider_id, lat, lng);

            double minDist = Double.MAX_VALUE;
            int driver_id = -1;
            for ( Map.Entry<Integer, Loc> driverGeo: GeoByDriverid.entrySet()) {
                Loc loc = driverGeo.getValue();
                double newDist = Helper.get_distance(lat, lng, loc.lat, loc.lng);
                if (minDist > newDist) {
                    minDist = newDist;
                    driver_id = driverGeo.getKey();
                }
            }
            if (driver_id != -1)
                GeoByDriverid.remove(driver_id);
            trip.driver_id = driver_id;
            TripByDriverid.put(driver_id, trip);
            return trip;
        }
    }

    class Loc {
        double lat, lng;
        public Loc(double lat, double lng) {
            this.lat = lat;
            this.lng = lng;
        }
    }

    // Definition of Trip:
    public class Trip {
        public int id; // trip's id, primary key
        public int driver_id, rider_id; // foreign key
        public double lat, lng; // pick up location
        private Random rand = new Random();

        public Trip(int rider_id, double lat, double lng) {
            this.id =rand.nextInt();
            this.rider_id = rider_id;
            this.lat = lat;
            this.lng = lng;
        }
    }


    // Definition of Helper
    static class Helper {
        public static double get_distance(double lat1, double lng1, double lat2, double lng2) {
            // return distance between (lat1, lng1) and (lat2, lng2)
            return 0.0;
        }
    };

}

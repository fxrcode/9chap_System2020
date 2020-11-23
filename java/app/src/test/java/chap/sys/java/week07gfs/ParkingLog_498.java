package chap.sys.java.week07gfs;

import java.util.*;

import org.junit.Test;

/*-
* Description
Design a parking lot.

see CC150 OO Design for details.

n levels, each level has m rows of spots and each row has k spots.So each level has m x k spots.
The parking lot can park motorcycles, cars and buses
The parking lot has motorcycle spots, compact spots, and large spots
Each row, motorcycle spots id is in range [0,k/4)(0 is included, k/4 is not included), compact spots id is in range [k/4,k/4*3)(k/4*3 is not included) and large spots id is in range [k/4*3,k)(k is not included).
A motorcycle can park in any spot
A car park in single compact spot or large spot
A bus can park in five large spots that are consecutive and within same row. it can not park in small spots

您的提交打败了 8.80% 的提交!
*/
public class ParkingLog_498 {
    @Test
    public void test() {
        ParkingLot parkingLot = new ParkingLot(1, 1, 11);
        parkingLot.parkVehicle(new Motorcycle("Motorcycle_1"));
        parkingLot.parkVehicle(new Car("Car_1") );
        parkingLot.parkVehicle(new Car("Car_2") );
        parkingLot.parkVehicle(new Car("Car_3") );
        parkingLot.parkVehicle(new Car("Car_4") );
        Vehicle car5 = new Car("Car_5");
        parkingLot.parkVehicle( car5 );
        parkingLot.parkVehicle(new Bus("Bus_1") );
        parkingLot.unParkVehicle( car5) ;
        parkingLot.parkVehicle(new Bus("Bus_1") );
    }

    // enum type for Vehicle
    enum VehicleSize {
        Motorcycle, Compact, Large,
    }

    abstract class Vehicle {
        // Write your code here
        protected String name;
        protected int spotsneeded;
        protected VehicleSize size;

        protected List<ParkingSpot> parkingSpots = new ArrayList<>();

        protected Vehicle(String name) {
            this.name = name;
        }

        public int getSpotsneeded() {
            return spotsneeded;
        }

        public VehicleSize getSize() {
            return size;
        }

        void parkInSpot(ParkingSpot spot) {
            parkingSpots.add(spot);
        }

        void clearSpots() {
            parkingSpots.forEach(ps -> ps.removeVehicle());
            parkingSpots.clear();

        }

        abstract boolean canFitInSpot(ParkingSpot spot);
    }

    class Motorcycle extends Vehicle {
        // Write your code here
        public Motorcycle(String name) {
            super(name);
            spotsneeded = 1;
            size = VehicleSize.Motorcycle;
        }

        public boolean canFitInSpot(ParkingSpot spot) {
            return true;
        }
    }

    class Car extends Vehicle {
        // Write your code here
        public Car(String name) {
            super(name);
            spotsneeded = 1;
            size = VehicleSize.Compact;
        }

        public boolean canFitInSpot(ParkingSpot spot) {
            return spot.vehicleSize.equals(VehicleSize.Compact) || spot.vehicleSize.equals(VehicleSize.Large);
        }
    }

    class Bus extends Vehicle {
        // Write your code here
        public Bus(String name) {
            super(name);

            spotsneeded = 5;
            size = VehicleSize.Large;
        }

        public boolean canFitInSpot(ParkingSpot spot) {
            return spot.vehicleSize.equals(VehicleSize.Large);
        }
    }

    /* Represents a level in a parking garage */
    class Level {
        // Write your code here
        private ParkingSpot[] spots;
        private int availableSpots = 0;

        public Level(int floor, int num_rows, int spots_per_row) {
            spots = new ParkingSpot[num_rows * spots_per_row];
            int spotNum = 0;
            for (int r = 0; r < num_rows; r++) {
                for (int j = 0; j < spots_per_row / 4; j++) {
                    spots[spotNum] = new ParkingSpot(this, r, spotNum, VehicleSize.Motorcycle);
                    spotNum++;
                }
                for (int j = spots_per_row / 4; j < spots_per_row / 4 * 3; j++) {
                    spots[spotNum] = new ParkingSpot(this, r, spotNum, VehicleSize.Compact);
                    spotNum++;
                }
                for (int j = spots_per_row / 4 * 3; j < spots_per_row; j++) {
                    spots[spotNum] = new ParkingSpot(this, r, spotNum, VehicleSize.Large);
                    spotNum++;
                }
            }
            availableSpots = spotNum;
        }

        // real code to find place to park this vehicle
        public boolean parkVehicle(Vehicle vehicle) {
            System.out.println("availableSpots:" + availableSpots);
            if (availableSpots < vehicle.getSpotsneeded())
                return false;
            int spotNumber = findAvailableSpots(vehicle);
            System.out.println("spotNumber:" + spotNumber);
            if (spotNumber < 0)
                return false;
            return parkStaringAtSpot(spotNumber, vehicle);
        }

        // find a spot to park this vehicle. Return index of spot, or -1 on failure
        private int findAvailableSpots(Vehicle v) {
            int spotsneeded = v.spotsneeded;
            int lastRow = -1;
            int spotsFound = 0;
            for (int i = 0; i < spots.length; i++) {
                ParkingSpot spot = spots[i];
                if (lastRow != spot.row) {
                    spotsFound = 0;
                    lastRow = spot.row; // this is cool trick I learned today, therefore 2d -> 1d is good.
                }
                if (spot.canFit(v)) {
                    spotsFound++;
                } else {
                    spotsFound = 0;
                }
                if (spotsFound == spotsneeded) {
                    return i - (spotsneeded - 1);
                }
            }
            return -1;
        }

        // mark used spots
        private boolean parkStaringAtSpot(int spotNumber, Vehicle v) {
            v.clearSpots();
            boolean success = true;
            for (int i = spotNumber; i < spotNumber + v.spotsneeded; i++) {
                success &= spots[i].park(v);
            }
            availableSpots -= v.spotsneeded;
            return success;
        }

        public void spotFreed() {
            availableSpots++;
        }
    }

    /**
     * represent each spot as parkingspot
     */
    class ParkingSpot {
        Vehicle vehicle;
        VehicleSize vehicleSize;
        int row;
        int spotNumber;
        Level level;

        public ParkingSpot(Level l, int row, int n, VehicleSize v) {
            this.level = l;
            this.row = row;
            this.spotNumber = n;
            this.vehicleSize = v;
        }

        public void removeVehicle() {
            level.spotFreed();
            vehicle = null;
        }

        public boolean canFit(Vehicle v) {
            return isAvailable() && v.canFitInSpot(this);
        }

        private boolean isAvailable() {
            return vehicle == null;
        }

        public boolean park(Vehicle v) {
            if (!canFit(v)) {
                return false;
            }
            this.vehicle = v;
            v.parkInSpot(this);
            return true;
        }
    }

    public class ParkingLot {
        private Level[] levels;
        private int NUM_LEVELS;

        // @param n number of levels
        // @param num_rows each level has num_rows rows of spots
        // @param spots_per_row each row has spots_per_row spots
        public ParkingLot(int n, int num_rows, int spots_per_row) {
            // Write your code here
            NUM_LEVELS = n;
            levels = new Level[NUM_LEVELS];
            for (int i = 0; i < NUM_LEVELS; i++) {
                levels[i] = new Level(n, num_rows, spots_per_row);
            }
        }

        // Park the vehicle in a spot (or multiple spots)
        // Return false if failed
        public boolean parkVehicle(Vehicle vehicle) {
            // Write your code here
            for (int i = 0; i < NUM_LEVELS; i++) {
                if (levels[i].parkVehicle(vehicle)) {
                    System.out.println(true);
                    return true;
                }
            }
            System.out.println(false);
            return false;
        }

        // unPark the vehicle
        public void unParkVehicle(Vehicle vehicle) {
            // Write your code here
            for (int i = 0; i < NUM_LEVELS; i++) {
                vehicle.clearSpots();
            }
        }
    }
}

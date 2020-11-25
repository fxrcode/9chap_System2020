package chap.sys.java.week08lbs;

import org.junit.Test;

public class GeoHash_II_530 {
    @Test public void test1() {
        GeoHash gh = new GeoHash();
        gh.decode("wx4g0s");
        gh.decode("w");
    }
    public class GeoHash {

        public double[] decode(String geohash) {
            // Write your code here
            String _base32 = "0123456789bcdefghjkmnpqrstuvwxyz";

            int[] masks = {16, 8, 4, 2, 1}; // good to use masks to do bit check
            double[] interval_lng = {-180.0, 180.0};
            double[] interval_lat = {-90.0, 90.0};
            boolean is_even = true; // nice flag to on/off in loop to do interleave actions

            for (int i = 0; i < geohash.length(); i++) {
                int val = _base32.indexOf(geohash.charAt(i));
                for (int j = 0; j < 5; j++) {
                    if (is_even) {
                        refine_interval(interval_lng, val, masks[j]);
                    } else {
                        refine_interval(interval_lat, val, masks[j]);
                    }
                    is_even = !is_even;
                }
            }
            double[] ret = { (interval_lat[0] + interval_lat[1]) / 2, (interval_lng[0] + interval_lng[1]) / 2};
            System.out.println( ret[0] + " " + ret[1] );
            return ret;
        }

        public void refine_interval(double[] interval, int val, int mask) {
            if  ( (val & mask) > 0) {
                interval[0] = (interval[0] + interval[1] ) / 2;
            } else {
                interval[1] = (interval[0] + interval[1] ) / 2;
            }
        }
        /*
         * @param geohash: geohash a base32 string
         * @return: latitude and longitude a location coordinate pair
         */
        // public double[] decode(String geohash) {
        //     int precision = geohash.length();

        // private final String CS = "0123456789bcdefghjkmnpqrstuvwxyz";
        //     StringBuilder bin_lng = new StringBuilder();
        //     StringBuilder bin_lat = new StringBuilder();

        //     StringBuilder bin_combine = new StringBuilder();
        //     for (int i = 0; i < precision; i++) {
        //         String b = Integer.toBinaryString( CS.indexOf(geohash.charAt(i)) );
        //         bin_combine.append(b);
        //     }
        //     for (int i = 0; i < bin_combine.length(); i += 2) {
        //         bin_lng.append(bin_combine.charAt(i));
        //         if (i+1 < bin_combine.length())
        //             bin_lat.append(bin_combine.charAt(i+1));
        //     }
        //     System.out.println(bin_lat.toString() + " " + bin_lng.toString());

        //     double d_lng = getDeg(bin_lng.toString(), -180.0, 180.0);
        //     double d_lat = getDeg(bin_lat.toString(), -90.0, 90.0);
        //     System.out.println(d_lat + " " + d_lng);
        //     double[] ret = {d_lng, d_lat};
        //     return ret;
        // }

        // private double getDeg(String binary, double left, double right) {
        //     double mid = 0;
        //     for (int i = 0; i < binary.length(); ++i) {
        //         mid = (left + right) / 2;
        //         if (binary.charAt(i) == '1' ) {
        //             left = mid;
        //         } else {
        //             right = mid;
        //         }
        //     }
        //     return mid;
        // }
    }
}

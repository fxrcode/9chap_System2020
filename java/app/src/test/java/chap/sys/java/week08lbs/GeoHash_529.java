package chap.sys.java.week08lbs;

import org.junit.Test;

/*-
Geohash is a hash function that convert a location coordinate pair into a base32 string.

Check how to generate geohash on wiki: Geohash or just google it for more details.

Try http://geohash.co/.

You task is converting a (latitude, longitude) pair into a geohash string.
*/
public class GeoHash_529 {
    @Test public void test1() {
        GeoHash gh = new GeoHash();
        // gh.encode(39.92816697 , 116.38954991, 12);
        // gh.encode(-90, 180, 12);
        // gh.encode(0, 90, 12);
        // gh.encode(0, 90, 1);
        gh.encode(22.5, 112.5, 1);
    }

    // 您的提交打败了 94.20% 的提交!
    public class GeoHash {
        private final String CS = "0123456789bcdefghjkmnpqrstuvwxyz";
        /*
         * @param latitude: one of a location coordinate pair
         * @param longitude: one of a location coordinate pair
         * @param precision: an integer between 1 to 12
         * @return: a base32 string
         */
        public String encode(double latitude, double longitude, int precision) {
            // write your code here
            String s_lng = getBin(longitude, -180.0, 180.0, 30);
            String s_lat = getBin(latitude, -90.0, 90.0, 30);
            StringBuilder combine = new StringBuilder();

            for (int i = 0; i < 30; ++i) {
                combine.append(s_lng.charAt(i));
                combine.append(s_lat.charAt(i));
            }
            System.out.println( s_lat + ", " + s_lng);
            StringBuilder ret = new StringBuilder();
            for (int i = 0; i < 60; i+=5) {
                String sub = combine.substring(i, i +5);
                int idx = Integer.parseInt(sub, 2);
                ret.append(CS.charAt(idx));
            }
            System.out.println(ret.substring(0, precision));
            return ret.substring(0, precision);
        }

        private String getBin(double value, double left, double right, int cnt) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < cnt; i++) {
                double mid = (left + right) / 2.0;
                if (mid < value) {  // note: no equal here for 1. otherwise totally wrong.
                    left = mid;
                    sb.append("1");
                } else {
                    right = mid;
                    sb.append("0");
                }
            }
            return sb.toString();
        }

        // https://www.factual.com/blog/how-geohashes-work/, use i %2 to interleave
        long encodeGeohash(double lat, double lng, int bits) {
            double minLat = -90,  maxLat = 90;
            double minLng = -180, maxLng = 180;
            long result = 0;
            for (int i = 0; i < bits; ++i)
              if (i % 2 == 0) {                 // even bit: bisect longitude
                double midpoint = (minLng + maxLng) / 2;
                if (lng < midpoint) {
                  result <<= 1;                 // push a zero bit
                  maxLng = midpoint;            // shrink range downwards
                } else {
                  result = result << 1 | 1;     // push a one bit
                  minLng = midpoint;            // shrink range upwards
                }
              } else {                          // odd bit: bisect latitude
                double midpoint = (minLat + maxLat) / 2;
                if (lat < midpoint) {
                  result <<= 1;                 // push a zero bit
                  maxLat = midpoint;            // shrink range downwards
                } else {
                  result = result << 1 | 1;     // push a one bit
                  minLat = midpoint;            // shrink range upwards
                }
              }
            return result;
          }
    }

}

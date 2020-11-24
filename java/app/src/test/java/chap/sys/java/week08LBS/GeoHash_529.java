package chap.sys.java.week08LBS;

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
        gh.encode(39.92816697 , 116.38954991, 12);
        gh.encode(-90, 180, 12);
    }

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
            int cnt_lat = precision * 5 / 2;
            int cnt_lng = precision * 5 / 2 + 1;
            String s_lng = getBin(longitude, -180.0, 180.0, cnt_lng);
            String s_lat = getBin(latitude, -90.0, 90.0, cnt_lat);
            StringBuilder combine = new StringBuilder();

            for (int i = 0, j = 0; i+j < precision * 5; i++, j++) {
                combine.append(s_lng.charAt(i));
                combine.append(s_lat.charAt(j));
            }
            StringBuilder ret = new StringBuilder();
            for (int i = 0; i < precision; i++) {
                String sub = combine.substring(i * 5, i * 5 +5);
                int idx = Integer.parseInt(sub, 2);
                ret.append(CS.charAt(idx));
            }
            System.out.println(ret.toString());
            return ret.toString();
        }

        private String getBin(double value, double left, double right, int cnt) {
            StringBuilder sb = new StringBuilder();
            while (cnt > 0) {
                double mid = (left + right) / 2;
                if (mid <= value) {
                    left = mid;
                    sb.append("1");
                } else {
                    right = mid;
                    sb.append("0");
                }
                cnt--;
            }
            return sb.toString();
        }
    }

}

package chap.sys.java.week07gfs;

/*-
learn : https://leetcode.com/discuss/interview-question/object-oriented-design/124738/5-flavors-of-singleton
Singleton is a most widely used design pattern. If a class has and only has one instance at every moment, we call this design as singleton. For example, for class Mouse (not a animal mouse), we should design it in singleton.

You job is to implement a getInstance method for given class, return the same instance of this class every time you call this method.
*/
public class Singleton_204 {
    private static volatile Singleton_204 instance;

    private Singleton_204() {
    }

    /**
     * Double checked locking : (Note that in the above approach, the race condition
     * can be reached only once in the entire application lifecycle, ie when
     * instance is null! What we are doing is introducing a LOT of overhead wth the
     * synchronized keyword. So we need a way to ensure that the locking protection
     * only applies once)
     * 
     * @return: The same instance of this class every time
     */
    public Singleton_204 getInstance() {
        // write your code here
        if (instance == null) {
            synchronized (this) {   // only lock the 1st time
                if (instance == null)   // the "double" check
                    instance = new Singleton_204();

            }
        }
        return instance;
    }
};

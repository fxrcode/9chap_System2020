package chap.sys.java.week07gfs;

import org.junit.Test;
import java.util.*;

/*-
Implement a simple client for GFS (Google File System, a distributed file system), it provides the following methods:

read(filename). Read the file with given filename from GFS.
write(filename, content). Write a file with given filename & content to GFS.
There are two private methods that already implemented in the base class:

readChunk(filename, chunkIndex). Read a chunk from GFS.
writeChunk(filename, chunkIndex, chunkData). Write a chunk to GFS.
To simplify this question, we can assume that the chunk size is chunkSize bytes. (In a real world system, it is 64M). The GFS Client's job is splitting a file into multiple chunks (if need) and save to the remote GFS server. chunkSize will be given in the constructor. You need to call these two private methods to implement read & write methods.
*/
public class GFS_Client_566_a {
    @Test
    public void test() {
        /*-
        GFSClient(5)
        read("a.txt")
        >> null
        write("a.txt", "World")
        >> You don't need to return anything, but you need to call writeChunk("a.txt", 0, "World") to write a 5 bytes chunk to GFS.
        read("a.txt")
        >> "World"
        write("b.txt", "111112222233")
        >> You need to save "11111" at chunk 0, "22222" at chunk 1, "33" at chunk 2.
        write("b.txt", "aaaaabbbbb")
        read("b.txt")
        >> "aaaaabbbbb" */
    }

    /**
     * 您的提交打败了 100.00% 的提交!
     * Gatech IoS client-server to upload big pic.
     */
    public class GFSClient extends BaseGFSClient {
        private int chunksz = 0;
        private Map<String, Integer> chunknumsByFilename;
        /*
         * @param chunkSize: An integer
         */public GFSClient(int chunkSize) {
            // do intialization if necessary
            this.chunksz = chunkSize;
            this.chunknumsByFilename = new HashMap<>();
        }

        /*
         * @param filename: a file name
         *
         * @return: conetent of the file given from GFS
         */
        public String read(String filename) {
            // write your code here
            if ( !chunknumsByFilename.containsKey(filename) ) {
                return null;
            }
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < chunknumsByFilename.get(filename); i++) {
                String chunk;
                if ((chunk = readChunk(filename, i)) != null ) {
                    sb.append(chunk);
                }
            }
            return sb.toString();
        }

        /*
         * @param filename: a file name
         *
         * @param content: a string
         *
         * @return: nothing
         */
        public void write(String filename, String content) {
            // write your code here
            int length = content.length();
            int pos = 0;
            int idx = 0;
            int end = 0;
            do {
                end = Math.min(pos+chunksz, length);
                writeChunk(filename, idx, content.substring(pos, end));
                pos = end;
                idx++;
            } while (pos < length);
            chunknumsByFilename.put(filename, idx);
        }
    }

    // Definition of BaseGFSClient
    class BaseGFSClient {
        private Map<String, String> chunk_list;

        public BaseGFSClient() {
        }

        public String readChunk(String filename, int chunkIndex) {
            // Read a chunk from GFS
            return null;
        }

        public void writeChunk(String filename, int chunkIndex, String content) {
            // Write a chunk to GFS
        }
    }

}

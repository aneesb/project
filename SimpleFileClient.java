import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.*;


public class SimpleFileClient {

  public final static int SOCKET_PORT = 13267;      // you may change this
  public final static String SERVER = "127.0.0.1";  // localhost
  public final static String
       FILE_TO_RECEIVED = "/home/anees/Documents/project/newFile.pdf";  // you may change this, I give a
                                                            // different name because i don't want to
                                                            // overwrite the one used by server...

  public final static int FILE_SIZE = 6022386; // file size temporary hard coded
                                               // should bigger than the file to be downloaded

  public static void main (String [] args ) throws IOException {
    int bytesRead;
    int current = 0;
    FileOutputStream fos = null;
    BufferedOutputStream bos = null;
    Socket sock = null;
    try {
      sock = new Socket(SERVER, SOCKET_PORT);
      System.out.println("Connecting...");

      // receive file
      byte [] mybytearray  = new byte [FILE_SIZE];
      InputStream is = sock.getInputStream();
      fos = new FileOutputStream(FILE_TO_RECEIVED);
      bos = new BufferedOutputStream(fos);
      // bytesRead = is.read(mybytearray,0,mybytearray.length);
      //  current = bytesRead;
      byte[] array = new byte[16];
      bytesRead = is.read(array,0,array.length);
      current = bytesRead;

      System.out.println("STARTED READING");
      
      do {
	  if(bytesRead == array.length) break;
         bytesRead =
	     // is.read(mybytearray, current, (mybytearray.length-current));
	  is.read(array, current, (array.length-current));
	if(bytesRead >= 0) current += bytesRead;
      } while(bytesRead > -1);
       System.out.println("FINISHED READING");
      //bos.write(mybytearray, 0 , current);
      IntBuffer intBuf =
        		   ByteBuffer.wrap(array)
        		     .order(ByteOrder.BIG_ENDIAN)
        		     .asIntBuffer();
        		 int[] array2 = new int[intBuf.remaining()];
        		 intBuf.get(array2);
        		 for (int i=0; i < array2.length; i++)
        	        {
        	            System.out.println(i + ": " + array2[i]);
        	        }

      bos.flush();
      System.out.println("File " + FILE_TO_RECEIVED
          + " downloaded (" + current + " bytes read)");
    }
    finally {
      if (fos != null) fos.close();
      if (bos != null) bos.close();
      if (sock != null) sock.close();
    }
  }

}



package servers;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.Socket;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.ServerSocket;

/**

 */
public class WorkerRunnable implements Runnable {

	protected Socket clientSocket = null;
	protected String serverText = null;
	public final static int SOCKET_PORT = 13267; // you may change this
	public final static String FILE_TO_SEND = "/home/anees/Desktop/blabla.pdf"; // you
																				// may
																				// change
																				// this

	public WorkerRunnable(Socket clientSocket, String serverText) {
		this.clientSocket = clientSocket;
		this.serverText = serverText;
	}

	public void run() {
		BufferedInputStream bis = null;
		OutputStream os = null;
		FileInputStream fis = null;
		try {
			try {
				// send file
				File myFile = new File(FILE_TO_SEND);
				byte[] mybytearray = new byte[(int) myFile.length()];
				fis = new FileInputStream(myFile);
				bis = new BufferedInputStream(fis);
				bis.read(mybytearray, 0, mybytearray.length);
				os = clientSocket.getOutputStream();
				System.out.println("Sending " + FILE_TO_SEND + "("
						+ mybytearray.length + " bytes)");
				os.write(mybytearray, 0, mybytearray.length);
				os.flush();
				System.out.println("Done.");
				/*
				 * long time = System.currentTimeMillis();
				 * output.write(("HTTP/1.1 200 OK\n\nWorkerRunnable: " +
				 * this.serverText + " - " + time + "").getBytes());
				 */
				// System.out.println("Request processed: " + time);
			} catch (IOException e) {
				// report exception somewhere.
				e.printStackTrace();
			} finally {
				if (bis != null)
					bis.close();
				if (os != null)
					os.close();
				if (clientSocket != null)
					clientSocket.close();
			}
		} catch (IOException e) {
			// report exception somewhere.
			e.printStackTrace();
		}
	}
}

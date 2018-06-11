package server;

import java.io.IOException;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class MainServer {

	public static String NEWPICTURE = "C:\\Users\\Razvan\\Desktop\\folder\\poza.png";
	public static String PICTURE1 = "C:\\Users\\Razvan\\Desktop\\folder\\picture1.png";
	public static String PICTURE2 = "C:\\Users\\Razvan\\Desktop\\folder\\picture2.png";

	public static long dur;

	public static void main(String[] args) {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		try {
			MyServer server = new MyServer();
			boolean number = false;
			while (true) {

				server.getNewPicture();

				Mat color = Imgcodecs.imread(NEWPICTURE);

				number ^= true;

				String path = number ? PICTURE1 : PICTURE2;
				if (Imgcodecs.imwrite(path, color)) {
					System.out.println("edge has bee created");
				}
				
				System.out.println();
				int ret;
				ret = IMGProcessor.compareFeature(PICTURE1, PICTURE2);
				System.out.println(ret);

				if (ret > 0) {
					System.out.println("Two images are same.");
				} else {
					System.out.println("Two images are different.");

				}
				server.sendResponse(false);
				System.out.println("DURATION" + (System.currentTimeMillis() - dur));
				System.out.println();
			}

			// dOut.flush();
			// dOut.close();

			// fos.close();
			// acceptedSocket.close();
			// server.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			System.out.println("Program closed without errors");
			System.out.println();
		}

	}

}

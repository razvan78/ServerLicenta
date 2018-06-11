package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
	ServerSocket server;
	Socket acceptedSocket;
	public MyServer() throws IOException {
		 server = new ServerSocket(3000);
	}
	
	public void getNewPicture() throws IOException {
		
		System.out.println("Waiting for conenction");
		
		acceptedSocket = server.accept();
		System.out.println("Connected");
		MainServer.dur = System.currentTimeMillis();
		DataInputStream in = new DataInputStream(acceptedSocket.getInputStream());
		System.out.println("DataInputStream created");
		
		int size = in.readInt();
		System.out.println("Bytes size: " + size);
		
		byte[] bytes = new byte[size];
		in.readFully(bytes);
		
		System.out.println("Bytes were read");
		
		//TODO: DECRYPT HERE
		String pathname = "C:\\Users\\Razvan\\Desktop\\folder\\poza.png";
		FileOutputStream fos = new FileOutputStream(pathname);
		System.out.println("OutputStream has been created");
		
		fos.write(bytes);
		System.out.println("Bytes have been written into the file");
		
		
	}
	
	public  void sendResponse(boolean dangerFound) throws IOException {
		
		System.out.println("Preparing for response");
		DataOutputStream dOut = new DataOutputStream(acceptedSocket.getOutputStream());
		
		System.out.println("Sending response" + dangerFound);
		dOut.writeBoolean(dangerFound);
		System.out.println("Response sent");
		
	}
}

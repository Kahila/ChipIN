package model.sockets;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
//import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

public class ClientSocket {
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	
	private boolean connected = false;
	
	//method for connecting client to server
	private void connect() {
	    try {
	    	socket = new Socket("localhost", 5000);
	        out = new PrintWriter(socket.getOutputStream(), true); 
	        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//	        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
	        connected = true;
	    } catch (Exception e) {connected = false;}
	}
	
	//method that sends client input to server
	public boolean clientInput(String input1) {
		try {
			String userInput = input1;
			out.println(userInput);
			System.out.println("echo: " + in.readLine());
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	// getters
	public boolean getConnected() {
		connect();
		return connected;
	}
}

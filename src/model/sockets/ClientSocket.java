package model.sockets;

/*
 * @author Kahila kalombo
 * @version: 1.0
 * @class ClientSocker
 * This Class contains client code (uses UDP for data communication)
 * */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientSocket {
	private boolean connected = false;
	private DatagramSocket clientSocket;
	private InetAddress IPAddress;
	private String clients = "";
	private String funding = "";
	private String Transection = "";
	
	private void connect() {
		try {
			@SuppressWarnings("unused")
			BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		    clientSocket = new DatagramSocket();
		    IPAddress = InetAddress.getByName("localhost");
		    connected = true;
		} catch (Exception e) {
		    clientSocket.close();
		    connected = false;
		}
	}
	
	//send query to server
	public boolean clientInput(String line) {
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		try {
			sendData = line.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
			clientSocket.send(sendPacket);
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(receivePacket);
			String modifiedSentence = new String(receivePacket.getData());
			if (line.compareTo("client") == 0)
				clients = modifiedSentence;
			else if (line.compareTo("get") == 0)
				funding = modifiedSentence;
			else if (line.compareTo("his") == 0)
				Transection = modifiedSentence;
			System.out.println("FROM SERVER:" + modifiedSentence);
			return true;
		}catch(Exception e) {
			return (false);
		}
	}
	
	public String getClients() {
		return clients;
	}
	
	public boolean getConnected() {
		connect();
		return (connected);
	}
	
	public String getFunding() {
		return funding;
	}
	
	public String getTransection() {
		return (Transection);
	}
}

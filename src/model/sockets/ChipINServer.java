package model.sockets;

import java.io.*;
import java.net.*;

import model.graphs.ChipInGraph;

public class ChipINServer {
	public int id;
	private static ChipInGraph graph = new ChipInGraph();
	
	// method that adds clients to static graph
	//getter
	public static ChipInGraph getGraph() {
		return (ChipINServer.graph);
	}
	
	public static void setGraph(ChipInGraph graph) {
		ChipINServer.graph = graph;
	}
	
//	public static void cou(ChipInGraph graph) {
//	for(int i = 0; i < graph.getVertices().size(); i++) {
//		System.out.println(graph.getVertices().get(i).getValue().getUsername());
//	}
//	}
	
	// create client connection
    public static void main(String[] args) {
        ServerSocket echoServer;
        int id = 0;
        try {
            echoServer = new ServerSocket(5000);
            while (true) {
                Socket clientSocket = echoServer.accept();
                ClientThread cliThread = new ClientThread(clientSocket, id++);
                cliThread.start();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}

class ClientThread extends Thread {
    Socket clientSocket;
    int id;
    BufferedReader br;
    PrintWriter os;
    String line;
    ClientThread(Socket clientSocket, int id) {
        this.clientSocket = clientSocket;
        this.id = id;
    }
    
    public void run() {
    	String[] splitRegister = null;
        try {
            br = new BufferedReader(new InputStreamReader(clientSocket
                .getInputStream()));
            os = new PrintWriter(clientSocket.getOutputStream(), true);
            while ((line = br.readLine()) != null) {
                System.out.println(id + "-Received: " + line);
                os.println(line);
                System.out.println(line.split(",", 3)[0]);
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            try {
                br.close();
                os.close();
                clientSocket.close();
                System.out.println(id + "...Stopped");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
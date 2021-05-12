package model.sockets;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

import model.blockchain.Block;
import model.blockchain.Transaction;
import model.graphs.ChipInGraph;
import model.graphs.Client;

/*
 * @author Kahila kalombo
 * @version: 1.0
 * @class ChipInserver
 * This Class contains Server code (uses UDP for data communication)
 * */
public class ChipINServer{
	private static ChipInGraph graph = new ChipInGraph();
	public static ArrayList<Block> blockchain = new ArrayList<>();
	public static ArrayList <Transaction> transactions = new ArrayList <Transaction>();
	private static DatagramSocket serverSocket;
	private static byte[] receiveData = new byte[1024];
	private static byte[] sendData = new byte[1024];
	private static DatagramPacket receivePacket;
	private static String query = "";
	public static int difficulty = 4;
	private static String trans = "";
	
	public static void main(String args[]) throws Exception {
		serverSocket = new DatagramSocket(9876);
		
		String output = "";
		String[] clientInput;
		int len2;
		while(true){
			receiveData = new byte[1024];
			sendData = new byte[1024];
			receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			String sentence = new String( receivePacket.getData());
			System.out.println("RECEIVED: " + sentence);
			InetAddress IPAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();
			String capitalizedSentence = sentence.toUpperCase();
			
			clientInput = sentence.trim().split(",");
			len2 = clientInput.length;
			if (clientInput[len2-1].compareTo("register") == 0) {
				createClient(capitalizedSentence); // creating client				
			}else if (clientInput[len2-1].compareTo("client") == 0) {
				output = getClients(capitalizedSentence);				
			}else if (clientInput[len2-1].compareTo("exit") == 0) {
				removeVertices(clientInput[0]);
			}else if (clientInput[len2-1].compareTo("qry") == 0) {
				query += clientInput[0]+ "," + clientInput[1] + "\n," +clientInput[2] ;
			}else if (clientInput[len2-1].compareTo("get") == 0) {
				output = query;				
			}else if (clientInput[len2-1].compareTo("send") == 0) {
				performTransection(clientInput[0].toUpperCase(), clientInput[1].toUpperCase(), Float.parseFloat(clientInput[2]));
				trans += "from " + clientInput[0].toUpperCase() + " to " + clientInput[1].toUpperCase() + "," + clientInput[2];
			}else if (clientInput[len2-1].compareTo("his") == 0) {
				output = trans;
			}
			sendData = output.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			serverSocket.send(sendPacket);
		}
	}
	
	//method for creating transaction
	public static void performTransection(String reciever, String sender, float amount) {
		Client A = null;
		Client B = null;
		int len = graph.getVertices().size();
		
		//find clients
		  for (int i = 0; i < len; i++) {
			  if (graph.getVertices().get(i).getValue().getUsername().compareTo(reciever) == 0) {
				  A = graph.getVertices().get(i).getValue();
			  }else if (graph.getVertices().get(i).getValue().getUsername().compareTo(sender) == 0) {
				  B = graph.getVertices().get(i).getValue();
			  }
		  }
		  Transaction tran1 = A.send(B.getPublicKey(), amount);
		  // setting the new balances
		  A.setBalance(-amount);
		  B.setBalance(amount);
		 if (tran1 != null) {
	        transactions.add(tran1);
	    }
		 Block b = new Block(0, null, transactions);
		 b.mineBlock(difficulty);
		 blockchain.add(b);
		 System.out.println("Wallet A Balance: " + A.getwallet());
		 System.out.println("Wallet B Balance: " + B.getwallet());
		 System.out.println("Blockchain Valid : " +
		validateChain(blockchain));
	}
	
	// adding clients to the graph and creating egdes
  public static void createClient(String line) {
	  String[] clientInfo = line.trim().split(",");
	  int accountType = 1;
	  int len = graph.getVertices().size();
	  
	  if (clientInfo[3].compareTo("GET FUNDING") == 0)
		  accountType = 0;
	  Client client = new Client(clientInfo[0], clientInfo[1], accountType, Float.parseFloat(clientInfo[2]));
	  if (len == 0)
		  graph.addClient(client, null, 0); // adding the client to the graph
	  else if (len > 0)
		  graph.addClient(client, graph.getVertices().get(len-1), len);			
	  System.out.println(graph.getVertices().size());
  }
  
  //get the client
  public static String getClients(String sentence) {
	  int len = graph.getVertices().size();
	  String clients = "";
	  
	  for (int i = 0; i < len; i++)
		  if (graph.getVertices().get(i).getValue().getUsername().compareTo("") != 0)
			  clients += graph.getVertices().get(i).getValue().getClintInfo() + "\n";
	  return (clients);
  }
  
//  removing the client from the graph
  public static void removeVertices(String name) {
	  int len = graph.getVertices().size();
	  
	  //finding the client to remove
	  for (int i = 0; i < len; i++)
		  if (graph.getVertices().get(i).getValue().getUsername().trim().compareTo(name.trim().toUpperCase()) == 0) {
			  graph.getVertices().get(i).getValue().setUsername();;
			  break;
		  }
  }
  
  // method that gets the query
  public String getQuery() {
	  return (query);
  }
  
	/*
	 * method that will check if the blocks in the chain are valid
	 * @return boolean value
	 * @param the new block and the previous block
	 */
	public static boolean validateBlock(Block newBlock, Block previousBlock) {
		if (previousBlock == null) { //The first block
		    if (newBlock.index != 0) {
		        return false;
		    }
		    if (newBlock.previousHash != null) {
		        return false;
		    }
		    if (newBlock.currentHash == null ||
		        !newBlock.calculateHash().equals(newBlock.currentHash)) {
		        return false;
		    }
		    return true;
		} else { //The rest blocks
		    if (newBlock != null) {
		        if (previousBlock.index + 1 != newBlock.index) {
		            return false;
		        }
		        if (newBlock.previousHash == null ||
		            !newBlock.previousHash.equals(previousBlock.currentHash)) {
		            return false;
		        }
		        if (newBlock.currentHash == null ||
		            !newBlock.calculateHash().equals(newBlock.currentHash)) {
		            return false;
		        }
		        return true;
		    }
		    return false;
		}
	}
	
	/*
	 * method that will validate the entire block chain
	 * @return boolean value
	 * @param the block chain
	 */
	public static boolean validateChain(ArrayList < Block > blockchain) {
	    if (!validateBlock(blockchain.get(0), null)) {
	        return false;
	    }
	    for (int i = 1; i < blockchain.size(); i++) {
	        Block currentBlock = blockchain.get(i);
	        Block previousBlock = blockchain.get(i - 1);
	        if (!validateBlock(currentBlock, previousBlock)) {
	            return false;
	        }
	    }
	    return true;
	}
}
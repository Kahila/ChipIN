package model.graphs;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.ArrayList;
import model.blockchain.*;

public class Client implements Comparable<Client>{
	private String username;
	private String password;
	private String privateKey;
	private String publicKey;
	private int accountType;
    private float balance; // amount in the wallet
    private int clientId; // identifies client on the server
    private ArrayList<Block> blockchain = new ArrayList < Block > ();
	
	public Client(String username,String password, int clientId, int accountType, float balance, ArrayList<Block> blockchain ) {
		this.username = username;
		this.password = password;
		this.accountType = accountType;
		this.balance = balance;
		this.clientId = clientId;
		generateKeyPair();
        this.blockchain = blockchain;
	}
	
	//method that calculates the key pair value (primary and private keys)
	public void generateKeyPair() {
        try {
            KeyPair keyPair;
            String algorithm = "RSA"; //DSA DH etc
            keyPair = KeyPairGenerator.getInstance(algorithm).
            generateKeyPair();
            privateKey = keyPair.getPrivate().toString();
            publicKey = keyPair.getPublic().toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
	//getting the current balance 
	 public float getBalance() {
	        float total = balance;
	        for (int i = 0; i < blockchain.size(); i++) {
	            Block currentBlock = blockchain.get(i);
	            for (int j = 0; j < currentBlock.transactions.size(); j++) {
	                Transaction tr = currentBlock.transactions.get(j);
	                if (tr.recipient.equals(publicKey)) {
	                    total += tr.value;
	                }
	                if (tr.sender.equals(publicKey)) {
	                    total -= tr.value;
	                }
	            }
	        }
	        return total;
	   }
	
	 //sending money
	 public Transaction send(String recipient, float value) {
	        if (getBalance() < value) {
	            System.out.println("!!!Not Enough funds. TransactionDiscarded.");
	                return null;
	            }
	            Transaction newTransaction = new Transaction(publicKey,
	                recipient, value);
	            return newTransaction;
	        }
	 
	@Override
	public int compareTo(Client o) {
		// TODO Auto-generated method stub
		return 0;
	}

	//getters
//	public String getUsername() {
//		return (username);
//	}
//	
//	public String getPassword() {
//		return (password);
//	}
	
	//setters
//	public void setUsername(String username) {
//		this.username = username;
//	}
//	
//	public void setPassword(String password) {
//		this.password = password;
//	}
}

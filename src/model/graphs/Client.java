package model.graphs;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.ArrayList;
import model.blockchain.*;

public class Client implements Comparable<Client>{
	private String username;
	private String privateKey;
	private String publicKey;
	private int accountType;
    private float balance; // amount in the wallet
    private ArrayList<Block> blockchain = new ArrayList < Block > ();
	
	public Client(String username,String password, int accountType, float balance ) {
		this.username = username;
		this.accountType = accountType;
		this.balance = balance;
		
		generateKeyPair();
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

	public String getClintInfo() {
		String type = "Funding";
		if (accountType == 1)
			type = "Need Funds";
		return ("client :" + username + "\t" + type);
	}
	
	public String getUsername() {
		return (username);
	}
	
	public float getwallet() {
		return balance;
	}
	public void setUsername() {
		username = "";
	}
	
	public String getPublicKey() {
		return publicKey;
	}
	
	public String getPrivateKey() {
		return privateKey;
	}
	
	public void setBalance(float amount) {
		balance += amount;
	}
	
}

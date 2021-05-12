package model.blockchain;

import java.security.MessageDigest;
import java.util.ArrayList;


public class Block {
	// for better abstraction make all private and then create getters and setters
	public int index;
	public String currentHash;
	public String previousHash;
	public Long timestemp;
	public String data;
	public int nonce;
	public ArrayList<Transaction> transactions = new ArrayList<Transaction>(); //list of transections 
	
	// constructor that will be used to set values for each block.
	public Block(int index, String previousHash, ArrayList<Transaction> transactions) {
		this.transactions = transactions;
		this.previousHash = previousHash;
		this.timestemp = System.currentTimeMillis(); // get systems current time
		this.index = index;
		nonce = 0;
		currentHash = calculateHash();
	}
	
	/*
	 * calculate hash of the current block using its' index, timestemp, data, previous hash and nonce
	 * @return newly created hash
	 * @param NULL
	 */
	public String calculateHash() {
		try {
			data = "";
			for (int j = 0; j < transactions.size(); j++) {
			    Transaction tr = transactions.get(j);
			    data = data + tr.sender + tr.recipient + tr.value;
			}
			String input = index + timestemp + previousHash + data +
			    nonce;
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(input.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < hash.length; i++) {
			    String hex = Integer.toHexString(0xff & hash[i]);
			    if (hex.length() == 1) hexString.append('0');
			    hexString.append(hex);
			}
			return hexString.toString();
			}
			catch (Exception e) {
			    throw new RuntimeException(e);
			}
	}
	
	/*
	 * public method that will mine the blocks in order to enforce 'proof of work'
	 * @param Integer value that will determine the difficulty of the hash
	 * @return NULL 
	 */
	public void mineBlock(int difficulty) {
	    nonce = 0;
	    String target = new String(new char[difficulty]).replace('\0',
	        '0');
	    while (!currentHash.substring(0, difficulty).equals(target)) {
	        nonce++;
	        currentHash = calculateHash();
	    }
	}
	
	// method for displaying block (good for debugging)
	public String toString() {
		return (
				"\nBlock #		: " + index + "\r\n"
						+ "PreviousHash	: " + previousHash + "\r\n"
								+ "TimeStamp	: " + timestemp + "\r\n"
										+ "Transactions	: " + data + "\r\n"
												+ "Nonce		: " + nonce + "\r\n"
														+ "CurrentHash	: " + currentHash + "\r\n"
				);
	}
}
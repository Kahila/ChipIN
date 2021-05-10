package model.blockchain;

import java.util.ArrayList;

public class BlockChain {
	public static ArrayList<Block> blockchain = new ArrayList<>();
	public static int difficulty = 3;
	
//	public static ArrayList < Transaction > transactions = new ArrayList < Transaction > ();
//	public static void main(String[] args) {
//	    Wallet A = new Wallet(blockchain);
//	    Wallet B = new Wallet(blockchain);
//	    System.out.println("Wallet A Balance: " + A.getBalance());
//	    System.out.println("Wallet B Balance: " + B.getBalance());
//
//	    System.out.println("Add two transactions... ");
//	    Transaction tran1 = A.send(B.publicKey, 10);
//	    if (tran1 != null) {
//	        transactions.add(tran1);
//	    }
//	    Transaction tran2 = A.send(B.publicKey, 20);
//	    if (tran2 != null) {
//	        transactions.add(tran2);
//	    }
//	    Block b = new Block(0, null, transactions);
//	    b.mineBlock(difficulty);
//	    blockchain.add(b);
//	    System.out.println("Wallet A Balance: " + A.getBalance());
//	    System.out.println("Wallet B Balance: " + B.getBalance());
//	    System.out.println("Blockchain Valid : " +
//	        validateChain(blockchain));
//	}
			
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


package com.muktadirshawn;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class Block {
	/*
	 * Basically, a block contains the following information :

		Index
		Timestamp to store the creation date of the Block
		Hash of the previous Block
		Data stored in the Block. For the Bitcoin and the other crypto currencies,
		data are transactions
		Hash of the current Block to ensure integrity of its content
	 */
	  private int index;
	  private long timestamp;
	  private String hash;
	  private String previousHash;
	  private String data;
	  private int nonce;//(of a word or expression) coined for or used on one occasion.

	  
	  public Block(int index, long timestamp, String previousHash, String data) {
		    this.index = index;
		    this.timestamp = timestamp;
		    this.previousHash = previousHash;
		    this.data = data;
		    nonce = 0;
		    hash = Block.calculateHash(this);
		  }

		  public int getIndex() {
		    return index;
		  }

		  public long getTimestamp() {
		    return timestamp;
		  }

		  public String getHash() {
		    return hash;
		  }

		  public String getPreviousHash() {
		    return previousHash;
		  }

		  public String getData() {
		    return data;
		  }

		  public String str() {
		    return index + timestamp + previousHash + data + nonce;
		  }

		  public String toString() {
		    StringBuilder builder = new StringBuilder();
		    builder.append("Block #").append(index).append(" [previousHash : ").append(previousHash).append(", ").
		    append("timestamp : ").append(new Date(timestamp)).append(", ").append("data : ").append(data).append(", ").
		    append("hash : ").append(hash).append("]");
		    return builder.toString();
		  }
			
		  //SHA-256 Cryptographic Hash Function
		  
		  public static String calculateHash(Block block) {
		    if (block != null) {
		      MessageDigest digest = null;

		      try {
		    	  
		    	  /*The cryptography algorithms offered by Java are recoverable 
		      		via the MessageDigest class, 
			        which allows you to retrieve the instance of an algorithm by entering
			        the name of the getInstance() method. */
		        digest = MessageDigest.getInstance("SHA-256"); 
		      } catch (NoSuchAlgorithmException e) {
		        return null;
		      }
		      /*
			   * *It then remains to enter in parameter of the digest() method a textual representation of the 
			   * blocks’s content to hash for getting the result of its hashing via the SHA-256 algorithm in the 
			   * form of an array of bytes. 
			   * To finish, we transform this array of bytes into a string of characters and we return this last 
			   * function output
			   */		
		      String txt = block.str();
		      final byte bytes[] = digest.digest(txt.getBytes());
		      final StringBuilder builder = new StringBuilder();
					
		      for (final byte b : bytes) {
		        String hex = Integer.toHexString(0xff & b);

		        if (hex.length() == 1) {
		          builder.append('0');
		        }
						
		        builder.append(hex);
		      }
					
		      return builder.toString();
		    }
			  
		    return null;
		  }

		  //Mining the Blocks
		  
		  /*Our block is practically functional. It only remains to add a method to carry out its mining. 
		   * The mining process will allow us to solve the enigma posed by the famous “Proof of Work”. 
		   * Given some difficulty passed as input, 
		   * we will have to find a hash for the block starting with a given number of zeros
		   * *
		   */
		  public void mineBlock(int difficulty) {
		     nonce = 0;
				
		     while (!getHash().substring(0,  difficulty).equals(Utils.zeros(difficulty))) {
		       nonce++;
		       hash = Block.calculateHash(this);
		     }
		  }
}

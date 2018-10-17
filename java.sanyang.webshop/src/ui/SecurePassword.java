package ui;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class SecurePassword {
	
	private static SecurePassword sp = new SecurePassword();
	
	private SecurePassword() { }
	
	/**
	 * This is a singleton class, ergo the getInstance method
	 * @return
	 */
	public static SecurePassword getInstance() {
		return sp;
	}
	
	/**
	 * Generate a hash through the hash function SHA-256.
	 * It is most IMPORTANT that database use the same SHA-256 hash-function
	 * otherwise different password could be generated in future
	 * @param password
	 * @return
	 */
	public String getHashSHA256(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] digest = md.digest(password.getBytes("UTF-8"));
			return password = DatatypeConverter.printHexBinary(digest);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Message digest caught ERROR, abort");
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}

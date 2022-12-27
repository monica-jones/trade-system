package accounts;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @author Aly Abdelwahed
 */

public class Authenticator {
	/**
	   * Returns a hashed version of password stored in the passwords file.
	   * @param password the unhashed password
	   * @return the hashed password
	   */
	public String passwordHash(String password) {
		MessageDigest messageDigest;
	    try {
	      messageDigest = MessageDigest.getInstance("SHA-256");
	      messageDigest.update(password.getBytes(StandardCharsets.UTF_8));
	      byte[] digest = messageDigest.digest();
	      return String.format("%064x", new java.math.BigInteger(1, digest));
	      
	    } catch (Exception e) {
	      return null;
	    }
	}

	 /**
	   * check if the password in the files password matches user provided password.
	   * @param filePassword the password stored in the passwords file.
	   * @param enteredPassword the user provided password (unhashed).
	   * @return true if passwords match, false otherwise.
	   */
	public boolean comparePassword(String filePassword, String enteredPassword) {
	    return filePassword.equals(passwordHash(enteredPassword));
	}

}

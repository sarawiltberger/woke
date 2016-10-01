package objects;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypt {
	
	public static String hash(String password){
		MessageDigest md;
		String hashed = "";
		try{
			 md = MessageDigest.getInstance("MD5");
	         byte[] byteArr = md.digest(password.getBytes());
	         BigInteger number = new BigInteger(1, byteArr);
	         String hashtext = number.toString(16);
	         while (hashtext.length() < 32) {
	             hashtext = "0" + hashtext;
	         }
	         return hashtext;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hashed;
	}
}

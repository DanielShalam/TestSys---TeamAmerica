package il.cshaifasweng.HSTS.server.utilities;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing {
	
	private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
	 
    public static String hashing(String originalString) throws NoSuchAlgorithmException {
    	final MessageDigest digest = MessageDigest.getInstance("SHA3-512");
    	final byte[] hashbytes = digest.digest(
    	  originalString.getBytes(StandardCharsets.UTF_8));
    	return bytesToHex(hashbytes);
    }  
    
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
	
}

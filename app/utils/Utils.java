package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

	public static double twoDecimalDouble(double value) {
		return Math.round(value*100)/100.0d;
	}
	
	public static String md5(String input) throws NoSuchAlgorithmException{
        String res = "";
        
        MessageDigest algorithm = MessageDigest.getInstance("MD5");
        algorithm.reset();
        algorithm.update(input.getBytes());
        byte[] md5 = algorithm.digest();
        String tmp = "";
        for (int i = 0; i < md5.length; i++) {
        	tmp = (Integer.toHexString(0xFF & md5[i]));
        	if (tmp.length() == 1) {
        		res += "0" + tmp;
        	} else {
        		res += tmp;
        	}
        }

        return res;
    }
}

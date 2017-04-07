package util;

import javax.crypto.Mac;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Created by felipeplazas on 4/6/17.
 */
public class SecurityManager {

    public static byte[] hashDigest(byte[] message) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(message);
            return md5.digest();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String generateSessionToken(){
        return new BigInteger(90, new Random()).toString(32);
    }
}

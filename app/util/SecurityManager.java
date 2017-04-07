package util;

import controllers.base.EPCrudService;
import models.auth.UserGroup;

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

    protected static final EPCrudService<UserGroup> userGroupsCrud = new EPCrudService<>("userGroups", UserGroup.class);

    public static boolean validatePermission(String permission, String userGroup) throws Exception{
        UserGroup ug = userGroupsCrud.collection().findOne(EPJson.string("name", userGroup)).as(UserGroup.class);
        if (ug.hasPermission(permission) || ug.getName().equals("admins")) return true;
        else
            throw new Exception("You do not have enough permissions to execute this task.");
    }

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

package utils;

import models.Model;
import org.apache.commons.lang3.SerializationUtils;
import play.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static String getMD5hash(String input) {
        String md5 = null;
        if(null == input)
            return null;

        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(input.getBytes(), 0, input.length());
            md5 = new BigInteger(1, digest.digest()).toString(16);

        } catch(NoSuchAlgorithmException e) {
            Logger.error(e.getMessage(), e);
        }
        return md5;
    }

    public static String transformToHTML(String input) {
        if(input != null) {
            input = input.replace("\n", "<br/>");
        }
        return input;
    }

    public static String getMD5hash(Object object) {
        String md5 = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] data = null;
            if(object instanceof Model) {
                Model model = (Model) object;
                data = SerializationUtils.serialize(model);
            } else if(object instanceof ArrayList) {
                ArrayList arrayList = (ArrayList) object;
                data = SerializationUtils.serialize(arrayList);
            }
            messageDigest.update(data);
            md5 = new BigInteger(1, messageDigest.digest()).toString(16);
        } catch(NoSuchAlgorithmException e) {
            Logger.error(e.getMessage(), e);
        }
        return md5;
    }

    public static boolean isObjectEqualToHash(Object object, String hash) {
        String hashFromObject = getMD5hash(object);
        return hashFromObject.equals(hash);
    }
}


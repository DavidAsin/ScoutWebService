package webServer.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by David on 23/04/2015.
 */

public class SHA1 {

    public static String getHash(String message) throws NoSuchAlgorithmException {
        byte[] buffer, digest;
        buffer = message.getBytes();
        MessageDigest md = MessageDigest.getInstance("SHA1");
        md.update(buffer);
        digest = md.digest();

        String hash = "";
        for(byte aux : digest) {
            int b = aux & 0xff;
            if (Integer.toHexString(b).length() == 1) hash += "0";
            hash += Integer.toHexString(b);
        }
        return hash;
    }

    public static String cript(String pass){
        String basura="rwr24t5yt25y543td32ty6";
        try {
            return getHash(pass+basura);
        }
        catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "0";
    }
}


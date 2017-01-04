package com.jd.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
  * @ClassName: TestEncrypt
  * @Description: MD5 or SHA-1,etc
  * @author Comsys-lanny
  * @date 2015年7月31日 下午5:34:49
  *  
 */
public class Encrypt {

    public Encrypt(){
    }

    public static String getEncrypt(String strSrc, String encName) {
        // parameter strSrc is a string will be encrypted,
        // parameter encName is the algorithm name will be used.
        // encName dafault to "MD5"
        MessageDigest md = null;
        String strDes = null;

        byte[] bt = strSrc.getBytes();
        try {
            if (encName == null || encName.equals("")) {
                encName = "MD5";
            }
            md = MessageDigest.getInstance(encName);
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Invalid algorithm.");
            return null;
        }
        return strDes;
    }

    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

       public static void main(String[] args) {
           Encrypt te = new Encrypt();
           String strSrc = "可以加密汉字.Oh,and english";
           System.out.println("Source String:" + strSrc);
           System.out.println("Encrypted String:");
           System.out.println("Use Def:" + te.getEncrypt(strSrc, null));
           System.out.println("Use MD5:" + te.getEncrypt(strSrc, "MD5"));
           System.out.println("Use SHA:" + te.getEncrypt(strSrc, "SHA-1"));
           System.out.println("Use SHA-256:" + te.getEncrypt(strSrc, "SHA-256"));
    }
}

package com.jd.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密类
 */
public class MD5Utils {

    private static MessageDigest md5 = null;


    protected static MessageDigest messageDigest = null;

    static {
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.err.println(MD5Utils.class.getName() + "初始化失败，MessageDigest不支持MD5Util.");
            e.printStackTrace();
        }
    }


    /**
     * 计算文件的MD5
     * @param fileName 文件的绝对路径
     * @return
     * @throws IOException
     */
    //public static String getFileMD5String(String fileName) throws IOException{
    //	File f = new File(fileName);
    //	return getFileMD5String(f);
    //}

    /**
     * 计算文件的MD5，重载方法
     *
     * @param file 文件对象
     * @return
     * @throws IOException
     */
    public static String getFileMD5String(File file) throws IOException {
        FileInputStream stream = new FileInputStream(file);
        FileChannel ch = stream.getChannel();
        MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
        messageDigest.update(byteBuffer);
        String hex = bufferToHex(messageDigest.digest());
        stream.close();
        return hex;
    }


    public static String getFileMD5String(byte[] bytes) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        messageDigest.update(byteBuffer);
        return bufferToHex(messageDigest.digest());
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }


    public static String MD5(String inStr) {

        try {
            if (md5 == null) md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }

    public static void main(String args[]) {
        System.out.println(MD5("123456"));
        System.out.println(MD5("e10adc3949ba59abbe56e057f20f883e" + "2"));
        System.out.println(MD5("8e114197e1b33783a00542ad67e80516" + "7524099"));

    }

    /**
     * @param psw  密码
     * @param salt 严
     * @return MD5 32位
     */
    public static String pswMD5(String psw, String salt) {
        if (psw == null) {
            psw = "";
        }
        if (salt == null) {
            salt = "";
        }
        return MD5(MD5(psw) + salt);
    }

    /**
     * @param psw  密码
     * @param salt 严
     * @return MD5 32位
     */
    public static String pswMD52(String psw, String salt) {
        if (psw == null) {
            psw = "";
        }
        if (salt == null) {
            salt = "";
        }
        return MD5(psw + salt);
    }

    /**
     * MD532位加密
     *
     * @param s
     * @return
     */
    public final static String MD5To32byte(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] strTemp = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return (new String(str)).toUpperCase();
        } catch (Exception e) {
            return null;
        }
    }

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes(charsetname)));
        } catch (Exception exception) {
        }
        return resultString;
    }

    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
}

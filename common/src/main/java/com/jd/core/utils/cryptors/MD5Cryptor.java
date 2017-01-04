package com.jd.core.utils.cryptors;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Cryptor {
    private MD5Cryptor() {
    }

    private static MD5Cryptor instance = new MD5Cryptor();

    public String md5(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5实例初始化失败", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("包含不支持编码", e);
        }
        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }

        return md5StrBuff.toString();
    }

    /**
     * 计算文件的MD5
     *
     * @param fileName 文件的绝对路径
     * @return
     */
    public String getFileMD5String(String fileName) {
        File f = new File(fileName);
        return getFileMD5String(f);
    }

    /**
     * 计算文件的MD5，重载方法
     *
     * @param file 文件对象
     * @return
     */
    public String getFileMD5String(File file) {
        MessageDigest messageDigest = null;
        String hex = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            FileInputStream stream = new FileInputStream(file);
            FileChannel ch = stream.getChannel();
            MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            messageDigest.update(byteBuffer);
            hex = bufferToHex(messageDigest.digest());
            stream.close();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5实例初始化失败", e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("文件找不到", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return hex;
    }

    public static MD5Cryptor getInstance() {
        return instance;
    }

    public String getMD5String(byte[] bytes) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5实例初始化失败", e);
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        messageDigest.update(byteBuffer);
        return bufferToHex(messageDigest.digest());
    }

    private String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            stringbuffer.append(Integer.toHexString(0xff & bytes[l]));
        }
        return stringbuffer.toString();
    }

}
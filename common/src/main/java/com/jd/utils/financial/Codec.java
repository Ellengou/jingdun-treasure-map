package com.jd.utils.financial;

import java.nio.charset.Charset;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Codec {

    private static Logger       logger            = LoggerFactory.getLogger(Codec.class);

    public static final Charset CHARSET           = Charset.forName("utf-8");

    public static final byte    keyStrSzie        = 16;

    public static final int     BLOCK_SIZE        = 32;

    public static final String  ALGORITHM         = "AES";

    public static final String  AES_CBC_NOPADDING = "AES/CBC/NoPadding";

    /**
     * AES/CBC/NoPadding encrypt 16 bytes secretKeyStr 16 bytes intVector
     *
     * @paramsecretKeyStr
     * @paramintVector
     * @param input
     * @return
     */
    public static byte[] encryptCBCNoPadding(byte[] secretKeyBytes, byte[] intVectorBytes, byte[] input) {
        try {
            IvParameterSpec iv = new IvParameterSpec(intVectorBytes);
            SecretKey secretKey = new SecretKeySpec(secretKeyBytes, ALGORITHM);

            Cipher cipher = Cipher.getInstance(AES_CBC_NOPADDING);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            byte[] encryptBytes = cipher.doFinal(input);
            return encryptBytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES/CBC/NoPadding decrypt 16 bytes secretKeyStr 16 bytes intVector
     *
     * @paramsecretKeyStr
     * @paramintVector
     * @param input
     * @return
     */
    public static byte[] decryptCBCNoPadding(byte[] secretKeyBytes, byte[] intVectorBytes, byte[] input) {
        try {
            IvParameterSpec iv = new IvParameterSpec(intVectorBytes);
            SecretKey secretKey = new SecretKeySpec(secretKeyBytes, ALGORITHM);

            Cipher cipher = Cipher.getInstance(AES_CBC_NOPADDING);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            byte[] encryptBytes = cipher.doFinal(input);
            return encryptBytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 用 AES 算法加密inputStr。 使用secretStr作为 key，secretStr的前 16 个字节作为 iv。
     *
     * @paramsecretStr
     * @paraminputStr
     * @return
     * @throwsCodecException
     */
    public static byte[] encode(String secretStr, String inputStr) {
        if (keyStrSzie != secretStr.length()) {
            return null;
        }
        byte[] secretKeyBytes = secretStr.getBytes(CHARSET);
        byte[] ivBytes = Arrays.copyOfRange(secretKeyBytes, 0, 16);
        byte[] inputBytes = inputStr.getBytes(CHARSET);

        int inputSize = inputBytes.length;
        int padSize = BLOCK_SIZE - (inputSize % BLOCK_SIZE);
        byte padByte = (byte) padSize;
        int withPadSize = inputSize + padSize;
        byte[] inputBytePading = new byte[withPadSize];
        System.arraycopy(inputBytes, 0, inputBytePading, 0, inputSize);
        for (int i = 1; i <= padSize; i++) {
            inputBytePading[withPadSize - i] = padByte;
        }

        byte[] outputBytes = encryptCBCNoPadding(secretKeyBytes, ivBytes, inputBytePading);
        return outputBytes;
    }

    /**
     * 用 AES 算法加密inputStr。 使用secretStr作为 key，secretStr的前 16 个字节作为 iv。 并对加密后的字节数组调用 sun.misc.BASE64Encoder.encode 方法， 转换成
     * base64 字符串返回。
     *
     * @paramsecretStr
     * @paraminputStr
     * @return
     * @throwsCodecException
     */
    public static String strEncodBase64(String secretStr, String inputStr) {
        String base64Str = Base64.encodeBase64String(encode(secretStr, inputStr));
        logger.debug("strEncodBase64 > base64 encrypt = {}", base64Str);
        return base64Str;
    }

    /**
     * 用 AES 算法加密inputStr。 使用secretStr作为 key，secretStr的前 16 个字节作为 iv。
     *
     * @paramsecretStr
     * @paraminputStr
     * @return
     * @throwsCodecException
     */
    public static byte[] decode(String secretStr, byte[] inputBytes) {
        if (keyStrSzie != secretStr.length()) {
            return null;
        }
        byte[] secretKeyBytes = secretStr.getBytes(CHARSET);
        byte[] ivBytes = Arrays.copyOfRange(secretKeyBytes, 0, 16);

        byte[] outputBytes = decryptCBCNoPadding(secretKeyBytes, ivBytes, inputBytes);

        int outputSize = outputBytes.length;
        byte padByte = outputBytes[outputSize - 1];
        int noPadSize = outputSize - padByte;
        byte[] outputByteNoPading = new byte[noPadSize];
        System.arraycopy(outputBytes, 0, outputByteNoPading, 0, noPadSize);

        return outputByteNoPading;
    }

    /**
     * 用 AES 算法加密inputStr。 使用secretStr作为 key，secretStr的前 16 个字节作为 iv。 并对加密后的字节数组调用 sun.misc.BASE64Encoder.encode 方法， 转换成
     * base64 字符串返回。
     *
     * @paramsecretStr
     * @paraminputStr
     * @return
     * @throwsCodecException
     * @throwsIOException
     */
    public static String base64StrDecode(String secretStr, String inputStr) {
        byte[] inputBytes;
        inputBytes = Base64.decodeBase64(inputStr);
        String outputStr = new String(decode(secretStr, inputBytes), CHARSET);
        logger.debug("base64Decode > base64 decrypt = {}", outputStr);
        return outputStr;
    }

    public static void main(String[] args) {
        String key = "1234567890123456";
        String userAttrStr = "{\"phone\":\"13800138000\",\"name\":\"张三\",\"idcard\":\"320882198512022828\",\"gender\":\"1\"}";
        String encodedStr = strEncodBase64(key, userAttrStr);
        logger.info("encodedStr = {}", encodedStr);
        logger.info("decodedStr = {}", new String(base64StrDecode(key, encodedStr)));
    }

}

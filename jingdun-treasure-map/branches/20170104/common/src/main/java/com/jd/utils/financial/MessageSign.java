package com.jd.utils.financial;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageSign {

    public static final Logger  logger          = LoggerFactory.getLogger(MessageSign.class);

    public static final Charset CHARSET         = Charset.forName("utf-8");

    public static final String  ALGORITHMS_SHA1 = "SHA-1";

    /**
     * 消息摘要，使用参数 algorithms 指定的算法
     *
     * @param algorithms
     * @paraminputStr
     * @return
     * @throwsNoSuchAlgorithmException
     */
    private static byte[] sign(String algorithms, String inputStr) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHMS_SHA1);
        messageDigest.update(inputStr.getBytes(CHARSET));
        return messageDigest.digest();
    }

    /**
     * byte 数组转 十六进制字符串
     *
     * @parambyteArray
     * @return
     */
    public static String byte2HexStr(byte[] byteArray) {

        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] resultCharArray = new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }

        return new String(resultCharArray);
    }

    /**
     * SHA1 签名,并将签名结果转换成 十六进制字符串
     *
     * @paraminputStr
     * @return
     */
    public static String signSHA1ToHexStr(String inputStr) {
        try {
            return byte2HexStr(sign(ALGORITHMS_SHA1, inputStr));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 拼接参数字符串
     *
     * @paramparamMap
     * @return
     */
    public static String paramTreeMapToString(TreeMap<String, String> paramMap) {
        StringBuilder paramStrBuilder = new StringBuilder();

        Iterator<Map.Entry<String, String>> it = paramMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entrySet = it.next();
            paramStrBuilder.append(entrySet.getKey()).append(entrySet.getValue());
        }
        return paramStrBuilder.toString();
    }

    /**
     * 利用 TreeMap 对 param 参数名称进行字典排序，然后拼接字符串，然后进行签名
     *
     * @paramapplyNo
     * @paramchannelNo
     * @paramapplyInfo
     * @paramuserAttribute
     * @return
     */
    public static String signTest() {

        String applyNo = "201512140075000002";
        String channelNo = "211";
        String userAttribute = "3Zp3/xXZ6elYPlqfsWgiR45kPk+oiKUqu0fW2a5hdhMURh58/PlfYjY0TGXNmc9QDlQ3aM14ShlJ\njgqVom4RSOsdQwhhlqbyWP77UjoDVBKCRdcIhzPFfhDSLAkK6d/0";

        String pNameApplyNo = "apply_no";
        String pNameChannelNo = "channel_no";
        String pNameUserAttribute = "user_attribute";
        TreeMap<String, String> paramMap = new TreeMap<>();
        paramMap.put(pNameApplyNo, applyNo);
        paramMap.put(pNameChannelNo, channelNo);
        paramMap.put(pNameUserAttribute, userAttribute);

        return signSHA1ToHexStr(paramTreeMapToString(paramMap));
    }

    public static void main(String[] args) {
        logger.info(signTest());
    }

}

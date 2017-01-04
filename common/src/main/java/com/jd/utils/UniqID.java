package com.jd.utils;


import java.io.IOException;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * UniqID
 *
 * @author zhanghongyuan
 * @date 2015/7/7
 */
public class UniqID{

    private static  final Logger log = Logger.getLogger(UniqID.class);

    private static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    private static UniqID me = new UniqID();
    private String hostAddr;
    private Random random = new SecureRandom();
    private MessageDigest mHasher;
    private UniqTimer timer = new UniqTimer();

    private UniqID() {
        try {
            InetAddress addr = InetAddress.getLocalHost();

            this.hostAddr = addr.getHostAddress();
        } catch (IOException e) {
            log.error("[UniqID] Get HostAddr Error", e);
            this.hostAddr = String.valueOf(System.currentTimeMillis());
        }

        if ((StringUtils.isBlank(this.hostAddr)) || ("127.0.0.1".equals(this.hostAddr))) {
            this.hostAddr = String.valueOf(System.currentTimeMillis());
        }

        if (log.isDebugEnabled()) {
            log.debug("[UniqID]hostAddr is:" + this.hostAddr);
        }
        try
        {
            this.mHasher = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nex) {
            this.mHasher = null;
            log.error("[UniqID]new MD5 Hasher error", nex);
        }
    }

    public static UniqID getInstance() {
        return me;
    }

    public String getUniqID() {
        StringBuffer sb = new StringBuffer();
        long t = this.timer.getCurrentTime();

        sb.append(t);

        sb.append("-");

        sb.append(this.random.nextInt(8999) + 1000);

        sb.append("-");
        sb.append(this.hostAddr);

        sb.append("-");
        sb.append(Thread.currentThread().hashCode());

        if (log.isDebugEnabled()) {
            log.debug("[UniqID.getUniqID]" + sb.toString());
        }

        return sb.toString();
    }

    public String getUniqIDHash() {
        return hash(getUniqID());
    }

    public synchronized String hash(String str) {
        byte[] bt = this.mHasher.digest(str.getBytes());
        int l = bt.length;

        char[] out = new char[l << 1];

        int i = 0; for (int j = 0; i < l; i++) {
            out[(j++)] = digits[((0xF0 & bt[i]) >>> 4)];
            out[(j++)] = digits[(0xF & bt[i])];
        }

        if (log.isDebugEnabled()) {
            log.debug("[UniqID.hash]" + new String(out));
        }

        return new String(out);
    }

    private class UniqTimer {
        private long lastTime = System.currentTimeMillis();

        private UniqTimer() {  }
        public synchronized long getCurrentTime() { long currTime = System.currentTimeMillis();

            this.lastTime = Math.max(this.lastTime + 1L, currTime);

            return this.lastTime;
        }
    }

    public static void main(String[] args) {
        System.out.println(DigestUtils.md5Hex(UniqID.getInstance().getUniqID()));
    }
}
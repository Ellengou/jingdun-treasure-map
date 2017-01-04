package com.jd.core.utils;

import com.jd.core.utils.cryptors.*;

public class EnCryptors {

    public static final SHA512Cryptor SHA512 = SHA512Cryptor.getInstance();
    public static final Base64Cryptor BASE64 = Base64Cryptor.getInstance();
    public static final MD5Cryptor MD5 = MD5Cryptor.getInstance();
    public final static AESCryptor AES = AESCryptor.getInstance();
    public final static DSACryptor DSA = DSACryptor.getInstance();
    public final static RSACryptor RSA = RSACryptor.getInstance();
}

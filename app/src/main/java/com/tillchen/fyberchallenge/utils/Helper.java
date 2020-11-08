package com.tillchen.fyberchallenge.utils;

import com.google.common.hash.Hashing;

import java.nio.charset.Charset;

public class Helper {

    /**
     * Hash the string with the securityToken (API key) concatenated.
     *
     * Here SHA1 is used since it's required by the Fyber API, though it's deprecated by Guava as
     * SHA256 is preferred.
     *
     * @param stringToHash The string to hash.
     * @param securityToken The security token (API key) to concatenate.
     * @return The hashed string.
     */
    static public String hashStringWithSecurityToken(String stringToHash, String securityToken) {
        return Hashing.sha1().hashString(stringToHash + "&" + securityToken,
                Charset.defaultCharset()).toString().toLowerCase();
    }

}

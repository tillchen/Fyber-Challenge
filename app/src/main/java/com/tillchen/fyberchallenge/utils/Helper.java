package com.tillchen.fyberchallenge.utils;

import com.google.common.hash.Hashing;

import java.nio.charset.Charset;

public class Helper {

    static public String hashStringWithSecurityToken(String stringToHash, String securityToken) {
        return Hashing.sha1().hashString(stringToHash + "&" + securityToken,
                Charset.defaultCharset()).toString().toLowerCase();
    }

}

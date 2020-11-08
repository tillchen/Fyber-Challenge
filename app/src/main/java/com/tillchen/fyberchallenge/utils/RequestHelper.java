package com.tillchen.fyberchallenge.utils;

import com.google.common.base.Joiner;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.TreeMap;

public class RequestHelper {

    public static final String FYBER_API_OFFERS_BASE_URL
            = "https://api.fyber.com/feed/v1/offers.json?";
    public static final String LOCALE = "DE";
    public static final String OFFER_TYPES = "112";
    public static final String IP = "109.235.143.113";
    public static final String RESPONSE_SIGNATURE = "X-Sponsorpay-Response-Signature";

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

    /**
     * Gets the request URL.
     *
     * Here a TreeMap is used to make sure that the parameters are sorted, which is a requirement
     * for computing the hashKey.
     *
     * @param appId The application ID.
     * @param userId The user ID.
     * @param securityToken The security token (API key).
     * @return The complete request URL.
     */
    static public String getRequestUrl(String appId, String userId, String securityToken) {
        Map<String, String> requestParams = new TreeMap<>();
        requestParams.put("appid", appId);
        requestParams.put("uid", userId);
        requestParams.put("ip", IP);
        requestParams.put("locale", LOCALE);
        requestParams.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000L));
        requestParams.put("offer_types", OFFER_TYPES);
        String paramsConcatenated = Joiner.on("&").withKeyValueSeparator("=").join(requestParams);
        String hashKey = RequestHelper.hashStringWithSecurityToken(paramsConcatenated, securityToken);
        return FYBER_API_OFFERS_BASE_URL + paramsConcatenated + "&hashkey=" + hashKey;
    }

    /**
     * Check if the response is corrupted by checking the HTTP header signature.
     * @param responseBody The response body in string.
     * @param securityToken The security token (API key).
     * @param validSignature The valid signature given in the HTTP header.
     * @return True if the response is corrupted.
     */
    static public boolean isResponseCorrupted(String responseBody, String securityToken,
                                       String validSignature) {
        String hashedResponseSignature = RequestHelper.hashStringWithSecurityToken(
                responseBody, securityToken);
        return !hashedResponseSignature.equals(validSignature);
    }

}

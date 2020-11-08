package com.tillchen.fyberchallenge;

import com.tillchen.fyberchallenge.utils.RequestHelper;

import org.junit.Assert;
import org.junit.Test;

public class RequestHelperUnitTest {

    @Test
    public void testHashStringWithSecurityToken() {
        // The expected hash is obtained from http://www.sha1-online.com
        String expected = "2d43ca826012f8d8d2f430950fbd22a15704b2b1";
        String stringToHash = "appid=2070&ip=109.235.143.113&locale=DE&offer_types=112" +
                "&timestamp=1604870998&uid=superman";
        String securityToken = "1c915e3b5d42d05136185030892fbb846c278927";
        String actual = RequestHelper.hashStringWithSecurityToken(stringToHash, securityToken);
        Assert.assertEquals(expected, actual);
    }
}
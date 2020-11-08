package com.tillchen.fyberchallenge;

import com.tillchen.fyberchallenge.utils.RequestHelper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RequestHelper.class)
public class RequestHelperUnitTest {

    @Test
    public void testHashStringWithSecurityToken() {
        // The expected hash is obtained from http://www.sha1-online.com
        String expected = "2d43ca826012f8d8d2f430950fbd22a15704b2b1";
        String stringToHash = "appid=2070&ip=109.235.143.113&locale=DE&offer_types=112"
                + "&timestamp=1604870998&uid=superman";
        String securityToken = "1c915e3b5d42d05136185030892fbb846c278927";
        String actual = RequestHelper.hashStringWithSecurityToken(stringToHash, securityToken);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetRequestUrl() {
        String expected = "https://api.fyber.com/feed/v1/offers.json?"
                + "appid=2070&ip=109.235.143.113&locale=DE&offer_types=112"
                + "&timestamp=1604870998&uid=superman"
                + "&hashkey=2d43ca826012f8d8d2f430950fbd22a15704b2b1";
        PowerMockito.mockStatic(System.class);
        PowerMockito.when(System.currentTimeMillis()).thenReturn(1604870998000L);
        String actual = RequestHelper.getRequestUrl("2070", "superman",
                "1c915e3b5d42d05136185030892fbb846c278927");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testIsResponseCorrupted() {
        String securityToken = "1c915e3b5d42d05136185030892fbb846c278927";
        String validSignature =
                RequestHelper.hashStringWithSecurityToken("foo", securityToken);
        Assert.assertFalse(RequestHelper.isResponseCorrupted(
                "foo", securityToken, validSignature));
        Assert.assertTrue(RequestHelper.isResponseCorrupted(
                "fo", securityToken, validSignature
        ));
    }

}
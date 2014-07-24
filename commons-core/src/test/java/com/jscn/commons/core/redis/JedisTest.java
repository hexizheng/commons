/**
 * JSCN APPLIANCE CHAINS.
 * Copyright (c) 2012-2012 All Rights Reserved.
 */
package com.jscn.commons.core.redis;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * jedis cache testcase
 * 
 * @author 袁兵 2012-7-5
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/application-test-redis.xml" })
public class JedisTest {
    @Autowired
    JedisCache jedisCache;

    @Test
    public void testJedisCache() throws InterruptedException {
        String key = "ddafdsafdasf";
        String value = "123456";
        assertFalse(jedisCache.isExist(key));
        jedisCache.save(key, value);
        assertTrue(jedisCache.isExist(key));
        assertEquals(value, jedisCache.get(key));
        // Thread.sleep(15);
        // assertFalse(jedisCache.isExist(key));
    }
}

/**
 * JSCN APPLIANCE CHAINS.
 * Copyright (c) 2012-2012 All Rights Reserved.
 */
package com.jscn.commons.core.utils;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 请输入功能描述
 * 
 * @author 袁兵 2012-9-14
 */
public class UUIDTest {

    @Test
    public void testUUID() {
        String uuid32 = UUID.random32UUID();
        System.out.println(uuid32);
        assertEquals(uuid32.length(), 32);
        
        String uuid22 = UUID.random22UUID();
        System.out.println(uuid22);
        assertEquals(UUID.random22UUID().length(),22);
    }
}

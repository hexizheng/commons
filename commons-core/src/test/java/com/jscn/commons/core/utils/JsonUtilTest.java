/**
 * JSCN APPLIANCE CHAINS.
 * Copyright (c) 2012-2012 All Rights Reserved.
 */
package com.jscn.commons.core.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Test;

/**
 * 请输入功能描述
 * 
 * @author 袁兵 2012-4-27
 */
public class JsonUtilTest {

    @Test
    public void testJson() throws JsonGenerationException, JsonMappingException, IOException {
        String json = null;
        Map<String, Object> userData = new HashMap<String, Object>();
        Map<String, String> nameStruct = new HashMap<String, String>();
        nameStruct.put("first", "Joe");
        nameStruct.put("last", "Sixpack");
        userData.put("name", nameStruct);
        userData.put("gender", "MALE");
        userData.put("gender2", 2);
        userData.put("verified", Boolean.FALSE);
        userData.put("userImage", "Rm9vYmFyIQ==");
        json = JsonUtil.object2Json(userData);
        assertNotNull(json);
        Map<?, ?> userData1 = JsonUtil.json2Object(json, HashMap.class);
        assertEquals(userData, userData1);

        json = JsonUtil.objectToJson(userData);
        assertNotNull(json);
        Map<?, ?> userData2 = JsonUtil.jsonToObject(json);
        assertEquals(userData, userData2);
    }
    
    @Test
    public void testJson2Obj(){
        String json = "{\"scid\":\"\",\"scStarttime\":\"00:00:00\",\"scEndtime\":\"02:01:59\",\"groupname\":\"分组1\",\"groupid\":\"402888a9389a32ce01389a32ce190000\"}";
        ProductScheduledetail obj = JsonUtil.json2Object(json, ProductScheduledetail.class);
        assertNotNull(obj);
    }
}

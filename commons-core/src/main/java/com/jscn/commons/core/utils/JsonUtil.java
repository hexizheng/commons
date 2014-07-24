package com.jscn.commons.core.utils;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jscn.commons.core.exceptions.SystemException;

/**
 * 
 * 修改与2012-4-27
 * 
 * @author 袁兵 2012-4-27
 */
public class JsonUtil {
    final static Logger LOG = LoggerFactory.getLogger(JsonUtil.class);

    public static String objectToJson(Object bean) {
        try {
            ObjectMapper om = new ObjectMapper();
            String json = om.writeValueAsString(bean);
            return json;
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new SystemException(e);
        }
    }

    public static String object2Json(Object obj) {
        return objectToJson(obj);
    }

    public static Map<?, ?> jsonToObject(String json) {
        try {
            ObjectMapper om = new ObjectMapper();
            Map<?, ?> map = om.readValue(json, Map.class);
            return map;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new SystemException(e);
        }
    }

    public static <T> T json2Object(String json, Class<T> clz) {
        try {
            ObjectMapper om = new ObjectMapper();
            T obj = om.readValue(json, clz);
            return obj;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new SystemException(e);
        }
    }

}

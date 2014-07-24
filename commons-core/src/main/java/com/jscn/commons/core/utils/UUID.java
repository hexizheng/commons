package com.jscn.commons.core.utils;

import org.apache.commons.codec.binary.Base64;

public class UUID {
    
    /**
     * 生成32位的16进制字符串的UUID
     * @return
     */
    public static String random32UUID() {
        return java.util.UUID.randomUUID().toString().replaceAll("-", "");
    }
    
    /**
     * 生成22位base64编码字符串的UUID
     * @return
     */
    public static String random22UUID() {
        byte[] b = StringUtils.hexStringToByte(random32UUID());
        return Base64.encodeBase64URLSafeString(b);
    }

}

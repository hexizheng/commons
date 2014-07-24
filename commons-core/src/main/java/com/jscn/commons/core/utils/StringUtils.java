package com.jscn.commons.core.utils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;

import org.apache.commons.codec.net.URLCodec;

/**
 * 字符串工具类
 * 
 * @description 提供操作字符串的常用工具方法
 * @author Lincoln
 */
public class StringUtils {
    static Map<String, String> weekName = new HashMap<String, String>();
    static {
        weekName.put("2", "星期一");
        weekName.put("3", "星期二");
        weekName.put("4", "星期三");
        weekName.put("5", "星期四");
        weekName.put("6", "星期五");
        weekName.put("7", "星期六");
        weekName.put("1", "星期日");
    }

    /**
     * 将对象数组拼接成字符串 以 "," 号分隔 返回String
     * 
     * @param ids
     * @return
     */
    public static String getString(Object[] objArr) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < objArr.length; i++) {
            if (i > 0) {
                buf.append(",");
            }
            buf.append(objArr[i]);
        }
        return buf.toString();
    }

    /**
     * 将对象数组转换为可显字符串
     * 
     * @param objArr
     * @return
     */
    public static String toString(Object[] objArr) {
        if (objArr == null) {
            return null;
        }

        StringBuffer buf = new StringBuffer("[");
        for (int i = 0; i < objArr.length; i++) {
            buf.append((i > 0 ? "," : "") + objArr[i]);
        }
        buf.append("]");
        return buf.toString();
    }

    /**
     * 获取星期几的名称
     * 
     * @param str
     * @return
     */
    public static String getWeekName(String str) {
        return weekName.get(str);
    }

    /**
     * 将单个对象转换为可显字符串
     * 
     * @param obj
     * @return
     */
    public static String toString(Object obj) {
        if (obj instanceof String) {
            return "\"" + obj + "\"";
        }
        if (obj instanceof Object[]) {
            return toString((Object[]) obj);
        } else {
            return String.valueOf(obj);
        }
    }

    public static void main(String[] args) {
        System.out.println(leftPad("abc", 2, '0'));
    }

    /**
     * 使用正则表达式验证字符串格式是否合法
     * 
     * @param piNoPattern
     * @param str
     * @return
     */
    // public static boolean patternValidate(String pattern, String str) {
    // if (pattern == null || str == null) {
    // throw new SystemException("参数格式不合法[patternValidate(String " + pattern +
    // ", String " + str + ")]");
    // }
    // return Pattern.matches(pattern, str);
    // }

    /**
     * 验证字符串是否为空字符
     * 
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().equals("") || str.trim().toLowerCase().equals("null")
                || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return (!isEmpty(str));
    }

    /**
     * 验证字符串是否为空字符
     * 
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().equals("") || str.trim().toLowerCase().equals("null")
                || str.trim().toLowerCase().equals("all");
    }

    /**
     * 判断字符串不为空
     * 
     * @param str
     * @return
     */
    public static boolean notBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 如果为空,将字符串转换为NULL
     * 
     * @param str
     * @return
     */
    public static String trimToNull(String str) {
        String s = null;
        if (isBlank(str)) {
            return s;
        }
        s = str.trim();
        return s;
    }

    /**
     * 字符编码转换器
     * 
     * @param str
     * @param newCharset
     * @return
     * @throws Exception
     */
    public static String changeCharset(String str, String newCharset) throws Exception {
        if (str != null) {
            byte[] bs = str.getBytes();
            return new String(bs, newCharset);
        }
        return null;
    }

    /**
     * 判断一个字符串是否为boolean信息
     * 
     * @param str
     * @return
     */
    public static boolean isBooleanStr(String str) {
        try {
            Boolean.parseBoolean(str);
            return true;
        } catch (Throwable t) {
            return false;

        }
    }

    /**
     * 取得指定长度的字符串(如果长度过长,将截取后半部分特定长度,如果长度太短,则使用指定字符进行左补齐)
     * 
     * @param str 原始字符串
     * @param length 要求的长度
     * @param c 用于补位的支付
     * @return 指定长度的字符串
     */
    public static String getLengthStr(String str, int length, char c) {
        if (str == null) {
            str = "";
        }
        int strPaymentIdLength = str.length();
        if (strPaymentIdLength > length) {
            str = str.substring(strPaymentIdLength - length);
        } else {
            str = leftPad(str, length, c);
        }
        return str;
    }

    private static String leftPad(String str, int length, char c) {
        if (str.length() >= length) {
            return str;
        }

        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length - str.length(); i++) {
            buf.append(c);
        }
        buf.append(str);
        return buf.toString();
    }

    /**
     * 
     * : convlToLong
     * 
     * @Description: TODO String 作非空处理
     * @param @param orgStr
     * @param @param convertStr
     * @param @return
     * @return Long
     * @throws
     */
    public static String convertNullToString(Object orgStr, String convertStr) {
        if (orgStr == null) {
            return convertStr;
        }
        return orgStr.toString();
    }

    /**
     * 
     * : convertNulg
     * 
     * @Description: TODO Long 作非空处理
     * @param @param orgStr
     * @param @param convertStr
     * @param @return
     * @return Long
     * @throws
     */
    public static Long convertNullToLong(Object orgStr, Long convertStr) {
        if (orgStr == null || Long.parseLong(orgStr.toString()) == 0) {
            return convertStr;
        } else {
            return Long.valueOf(orgStr.toString());
        }
    }

    /**
     * 
     * : convertNullTolon * @Description: TODO long 作非空处理
     * 
     * @param @param orgStr
     * @param @param convertStr
     * @param @return
     * @return long
     * @throws
     */
    public static long convertNullTolong(Object orgStr, long convertStr) {
        if (orgStr == null || Long.parseLong(orgStr.toString()) == 0) {
            return convertStr;
        } else {
            return Long.parseLong(orgStr.toString());
        }
    }

    /**
     * 
     * : convertNullToInt cription: TODO Int 作非空处理
     * 
     * @param @param orgStr
     * @param @param convertStr
     * @param @return
     * @return int
     * @throws
     */
    public static int convertNullToInt(Object orgStr, int convertStr) {
        if (orgStr == null || Long.parseLong(orgStr.toString()) == 0) {
            return convertStr;
        } else {
            return Integer.parseInt(orgStr.toString());
        }
    }

    /**
     * 
     * : convertNullToInt
     * 
     * @Deson: TODO Integer 作非空处理
     * @param @param orgStr
     * @param @param convertStr
     * @param @return
     * @return int
     * @throws
     */
    public static int convertNullToInteger(Object orgStr, int convertStr) {
        if (orgStr == null) {
            return convertStr;
        } else {
            return Integer.valueOf(orgStr.toString());
        }
    }

    /**
     * 
     * : convertNullToDate
     * 
     * @DescriptODO Date 作非空处理
     * @param @param orgStr
     * @param @return
     * @return Date
     * @throws
     */
    public static Date convertNullToDate(Object orgStr) {
        if (orgStr == null || orgStr.toString().equals("")) {
            return new Date();
        } else {
            return (Date) (orgStr);
        }
    }

    /**
     * 
     * : convertNullToDate
     * 
     * @Description: ate 作非空处理
     * @param @param orgStr
     * @param @return
     * @return Date
     * @throws
     */
    public static BigDecimal convertNullToBigDecimal(Object orgStr) {
        if (orgStr == null || orgStr.toString().equals("")) {
            return new BigDecimal("0");
        } else {
            return (BigDecimal) (orgStr);
        }
    }

    /**
     * 对字符串 - 在左边填充指定符号
     * 
     * @param s
     * @param fullLength
     * @param addSymbol
     * @return
     */
    public static String addSymbolAtLeft(String s, int fullLength, char addSymbol) {
        if (s == null) {
            return null;
        }

        int distance = 0;
        String result = s;
        int length = s.length();
        distance = fullLength - length;

        if (distance <= 0) {
            System.out
                    .println("StringTools:addSymbolAtleft() --> Warinning ,the length is equal or larger than fullLength!");
        }

        else {
            char[] newChars = new char[fullLength];
            for (int i = 0; i < length; i++) {
                newChars[i + distance] = s.charAt(i);
            }

            for (int j = 0; j < distance; j++) {
                newChars[j] = addSymbol;
            }

            result = new String(newChars);
        }

        return result;
    }

    /**
     * 对字符串 - 在右边填充指定符号
     * 
     * @param s
     * @param fullLength
     * @param addSymbol
     * @return
     */
    public static String addSymbolAtRight(String s, int fullLength, char addSymbol) {
        if (s == null) {
            return null;
        }

        String result = s;
        int length = s.length();

        if (length >= fullLength) {
            System.out
                    .println("StringTools:addSymbolAtRight() --> Warinning ,the length is equal or larger than fullLength!");
        }

        else {
            char[] newChars = new char[fullLength];

            for (int i = 0; i < length; i++) {
                newChars[i] = s.charAt(i);
            }

            for (int j = length; j < fullLength; j++) {
                newChars[j] = addSymbol;
            }
            result = new String(newChars);
        }

        return result;
    }

    /**
     * 判断两个字符串是否相同
     * 
     * @param str1
     * @param str2
     * @return
     */
    public static boolean isEquals(String str1, String str2) {
        if (str1 == null) {
            return str2 == null;
        } else {
            return str1.equals(str2);
        }
    }

    /**
     * 判断两个字符串是否不同
     * 
     * @param str1
     * @param str2
     * @return
     */
    public static boolean notEquals(String str1, String str2) {
        return !isEquals(str1, str2);
    }

    /**
     * 分隔字符串
     * 
     * @param srcStr 被分隔的字符串
     * @param splitChars 多个分隔符
     * @return 分隔结果
     */
    public static List<String> splitString(String srcStr, String splitChars) {
        if (isBlank(srcStr)) {
            return null;
        }
        List<String> strList = new ArrayList<String>();
        StringTokenizer tok = new StringTokenizer(srcStr, splitChars);
        while (tok.hasMoreTokens()) {
            strList.add(tok.nextToken());
        }
        return strList;
    }

    /**
     * 格式化字符串
     * 
     * @param src
     * @param params
     * @return
     */
    public static String formatString(String src, Object... params) {
        String[] paramsStrArr = params != null ? new String[params.length] : null;
        for (int i = 0; params != null && i < params.length; i++) {
            paramsStrArr[i] = String.valueOf(params[i]);
        }

        return MessageFormat.format(src, paramsStrArr);
    }

    /**
     * 获取UUID
     * 
     * @return
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-{1}", "");
    }

    public static String urlEncode(String str, String charSet) {
        try {
            URLCodec urlCodec = new URLCodec();
            return urlCodec.encode(str, charSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String urlDecode(String str, String charSet) {
        try {
            URLCodec urlCodec = new URLCodec();
            return urlCodec.decode(str, charSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String subStrByBytes(String str, int len, String tail) {
        if (str == null) {
            return "";
        }
        if (str.getBytes().length <= len) {
            return str.trim();
        }

        str = str.trim();
        String s = "";
        char[] c = str.toCharArray();
        int i = 0;
        if (tail != null) {
            len -= tail.getBytes().length;
        }
        while (s.getBytes().length < len) {
            s = s + String.valueOf(c[i]);
            i++;
        }
        if (s.getBytes().length > len) {
            s = s.substring(0, s.length() - 1);
        }
        if (tail != null)
            s = s + tail;
        return s;
    }

    /**
     * 把16进制字符串转换成字节数组
     * @param hex
     * @return
     */
    public static byte[] hexStringToByte(String hex) {
        if (hex == null) {
            return null;
        }
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toUpperCase().toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static byte toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }

    /** */
    /**
     * 把字节数组转换成16进制字符串
     * @param bArray
     * @return
     */
    public static final String bytesToHexString(byte[] bArray) {
        if (bArray == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

}

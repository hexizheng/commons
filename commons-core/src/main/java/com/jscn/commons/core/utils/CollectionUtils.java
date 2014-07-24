package com.jscn.commons.core.utils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.jscn.commons.core.exceptions.SystemException;

/**
 * 集合工具类
 */
public class CollectionUtils {

    /**
     * 将列表转换为数组
     *
     * @param list 列表
     * @return 数组
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] listToArray(List<T> list) {
        if (list == null) {
            return null;
        }

        T[] tArr = (T[]) Array.newInstance(list.get(0).getClass(), list.size());
        return list.toArray(tArr);
    }

    /**
     * 将数组转换为列表
     *
     * @param array 数组
     * @return 列表
     */
    public static <T> List<T> arrayToList(T[] array) {
        if (array == null) {
            return null;
        }

        return Arrays.asList(array);
    }

    /**
     * 截取数组
     *
     * @param srcArr 原始数组
     * @param index  起始位置，从0开始
     * @param length 长度
     * @return 子数组
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] subArr(T[] srcArr, int index, int length) {
        //验证参数合法性
        if (srcArr == null || index < 0 || length < 0
                || srcArr.length < index + length) {
            throw new SystemException("参数不合法：subArr(T[] " + Arrays.toString(srcArr) + ", int "
                    + index + ", int " + length + ")");
        }

        //构造新数据并拷贝
        T[] tArr = (T[]) Array.newInstance(srcArr[0].getClass(), length);
        System.arraycopy(srcArr, index, tArr, 0, length);
        return tArr;
    }

    /**
     * 截取数组
     *
     * @param srcArr 原始数组
     * @param index  起始位置，从0开始
     * @param length 长度
     * @return 子数组
     */
    public static byte[] subArr(byte[] srcArr, int index, int length) {
        //验证参数合法性
        if (srcArr == null || index < 0 || length < 0
                || srcArr.length < index + length) {
            throw new SystemException("参数不合法：subArr(T[] " + Arrays.toString(srcArr) + ", int "
                    + index + ", int " + length + ")");
        }

        //构造新数据并拷贝
        byte[] tArr = new byte[length];
        System.arraycopy(srcArr, index, tArr, 0, length);
        return tArr;
    }

    /**
     * 将字符串表示的长整型数组转换为长整型数组
     *
     * @param strArr 字符串数组
     * @return 长整型数组
     */
    public static Long[] convertArray(String[] strArr) {
        if (strArr == null) {
            return null;
        }

        Long[] longArr = new Long[strArr.length];
        for (int i = 0; i < longArr.length; i++) {
            if (!NumberUtils.isLong(strArr[i])) {
                throw new SystemException("将字符串数组转换为长整型数组过程中，" +
                        "发现字符串数组索引 [" + i + "] 的值 [" + strArr[i] + "] 为非长整型！");
            }

            longArr[i] = Long.parseLong(strArr[i]);
        }
        return longArr;
    }

    /**
     * 将数组转换为逗号分隔的字符串
     *
     * @param objArr 对象数组
     * @return 逗号分隔的字符串
     */
    public static String arrayToString(Object[] objArr) {
        if (objArr == null || objArr.length == 0) {
            return null;
        }

        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < objArr.length; i++) {
            buf.append((i > 0 ? "," : "") + objArr[i]);
        }
        return buf.toString();
    }

    /**
     * 将逗号分隔的字符串转换为数组
     *
     * @param str 逗号分隔的字符
     * @return 字符串数组
     */
    public static String[] stringToArray(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }

        return str.split(",");
    }

    /**
     * 将一个字符串使用分隔符分割后转换为字符串列表
     *
     * @param str     字符串
     * @param splitter 分隔符
     * @return 字符串列表
     */
    public static List<String> splitStringToList(String str, String splitter) {
        if (StringUtils.isBlank(str)) {
            return null;
        }

        return arrayToList(str.split(splitter));
    }

    /**
     * 判断一个列表是否为空
     *
     * @param list 列表
     * @return 为空返回<code>true</code>，非空返回<code>false</code>
     */
    public static boolean isEmpty(List<?> list) {
        return list == null || list.size() == 0;
    }

    /**
     * 将Map<String,String[]>转换为Map<String,String>
     *
     * @param srcMap Map<String,String[]>
     * @return Map<String,String>
     */
    public static Map<String, String> convertMap(Map<String, String[]> srcMap) {
        Map<String, String> aimMap = new HashMap<String, String>();
        for (Entry<String, String[]> entry : srcMap.entrySet()) {
            String[] srcValue = entry.getValue();
            String aimValue = srcValue != null && srcValue.length > 0 ? srcValue[0]
                    : null;
            aimMap.put(entry.getKey(), aimValue);
        }
        return aimMap;
    }
}

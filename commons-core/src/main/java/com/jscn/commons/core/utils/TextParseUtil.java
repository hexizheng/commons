package com.jscn.commons.core.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


/**
 * Utility class for text parsing.
 * 
 * @author Jason Carreira
 * @author Rainer Hermanns
 * @author tm_jee
 * 
 * @version $Date: 2009-03-17 22:41:20 +0100 (Di, 17 M盲r 2009) $ $Id:
 *          TextParseUtil.java 1945 2009-03-17 21:41:20Z musachy $
 */
public class TextParseUtil {

    private static final int MAX_RECURSION = 1;
    
    /**
     * Returns a set from comma delimted Strings.
     * 
     * @param s
     *            The String to parse.
     * @return A set from comma delimted Strings.
     */
    public static Set<String> commaDelimitedStringToSet(String s) {
        Set<String> set = new HashSet<String>();
        String[] split = s.split(",");
        for (String aSplit : split) {
            String trimmed = aSplit.trim();
            if (trimmed.length() > 0)
                set.add(trimmed);
        }
        return set;
    }

    /**
     * 截取字符串，长度大于maxLength的字符串，保留前maxLength位加"..."
     * @param s
     * @return
     */
    public static String cutString(String s, int maxLength) {
        if(maxLength > 0){
            if (s != null && s.length() > maxLength) {
                return s.substring(0, maxLength) + "...";
            }
        }
        return s;
    }

    /**
     * 判断参数是否允许打入log
     * @param excludeParams
     * @param param
     * @return true 允许打入log
     */
    public static boolean applyParam(Set<String> excludeParams, String param) {
        // quick check to see if any actual pattern matching is needed
        boolean needsPatternMatch = false;
        
        if(excludeParams==null){
            return true;
        }
        
        if (!needsPatternMatch) {
            if (excludeParams != null && excludeParams.contains(param)) {
                return false;
            }
        }  
        
        for (String incMeth : excludeParams) {
            if (!"*".equals(incMeth) && incMeth.contains("*")) {
                needsPatternMatch = true;
            }
        }

        

        // test the params using pattern matching
        WildcardHelper wildcard = new WildcardHelper();
        String paramCopy;
        if (param == null) { // no param specified
            paramCopy = "";
        } else {
            paramCopy = new String(param);
        }
        if (excludeParams.contains("*")) {
            return false;
        }

        for (String pattern : excludeParams) {
            if (pattern.contains("*")) {
                int[] compiledPattern = wildcard.compilePattern(pattern);
                HashMap<String, String> matchedPatterns = new HashMap<String, String>();
                boolean matches = wildcard.match(matchedPatterns, paramCopy, compiledPattern);
                if (matches) {
                    return false;
                }
            } else {
                if (pattern.equals(paramCopy)) {
                    return false;
                }
            }
        }

        return !(excludeParams.contains(param) || excludeParams.contains("*"));
    }

    /**
     * 判断参数是否允许打入log
     * @param excludeParams
     * @param param
     * @return
     */
    public static boolean applyParam(String excludeParams, String param) {
        Set<String> excludeParamsSet = TextParseUtil
                .commaDelimitedStringToSet(excludeParams == null ? "" : excludeParams);
        return applyParam(excludeParamsSet, param);
    }
}

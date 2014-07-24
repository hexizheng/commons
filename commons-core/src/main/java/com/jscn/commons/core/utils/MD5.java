package com.jscn.commons.core.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * 
 * MD5 digest
 *
 * @author  2012-7-16
 * @see EncryptUtil
 */
@Deprecated
public class MD5 {  
	
	private MD5(){};
	
	public static final String md5(String str){
		if(str == null || str.equals(""))
			return null;
		
		try{
			return encodeHex(hash(str).getBytes("UTF-8"));
		}catch(Exception e){
			System.out.println("转换字符串到MD5+Hex的字符串异常");
		}
		return null;
	}

	private static final String hash(String data) {
		return hash(data.getBytes());
	}
	private static final String hash(byte[] data) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(data);
			return encodeHex(digest.digest());
		} catch (NoSuchAlgorithmException nsae) {
			System.err.println("Failed to load the MD5 MessageDigest. "
					+ "Jive will be unable to function normally.");
			nsae.printStackTrace();
		}
		return null;
	}
	
	private static final String encodeHex(byte[] bytes) {
		StringBuffer buf = new StringBuffer(bytes.length * 2);
		int i;

		for (i = 0; i < bytes.length; i++) {
			if (((int) bytes[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString((int) bytes[i] & 0xff, 16));
		}
		return buf.toString();
	}
}
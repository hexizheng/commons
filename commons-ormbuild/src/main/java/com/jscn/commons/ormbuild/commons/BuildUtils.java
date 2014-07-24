package com.jscn.commons.ormbuild.commons;

public class BuildUtils {

	/**
	 * 根据列名取得属性名
	 * 
	 * @param colName
	 * @param hasTypePostfix TODO
	 * @return
	 */
	public static String getPropertyNameByColName2(String colName, boolean hasTypePostfix) {
		int indexOf = -1;
		StringBuffer buf = new StringBuffer();
		colName=colName.toUpperCase();
		while ((indexOf = colName.indexOf("_")) != -1) {
			buf.append(colName.substring(0, indexOf).toLowerCase());
			buf.append(colName.substring(indexOf + 1, indexOf + 2));
			colName = colName.substring(indexOf + 2);
		}
		String propName = buf.toString();
		return propName.substring(0, propName.length() - 1);
	}
	
	public static String getPropertyNameByColName(String colName, boolean hasTypePostfix) {
		colName=colName.toLowerCase();
		int index;
		while((index=colName.indexOf("_"))!=-1){
			String a = colName.substring(0,index);
			String b = colName.substring(index+1,index+2).toUpperCase();
			String c = colName.substring(index+2);
			colName=a+b+c;
		}
		
		return hasTypePostfix?colName.substring(0,colName.length()-1):colName;
	}
	
	public static void main(String[] args) {
		System.out.println(getPropertyNameByColName2("GB_ID_N", false));
	}

}

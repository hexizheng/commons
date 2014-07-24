package com.jscn.commons.core.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.jscn.commons.core.exceptions.SystemException;


/**
 * description
 * @usage
 */
public class NumberUtils {
	private static final BigDecimal BIGDEC_ZERO = new BigDecimal(0);

	/**
	 * 整数拆分
	 *
	 * @param sourDecimal 待拆分的数字 （350）
	 * @param splitDecimal 以什么数来拆分 （100,50）大的在前 小的再后
	 * @return｛100,100,100,50｝
	 * @throws ErrorMessageException
	 */
	public static BigDecimal[] splitDecimal(BigDecimal sourDecimal, BigDecimal[] splitDecimal) {
		List<BigDecimal> splitDecimalForList = splitDecimalForList(sourDecimal, splitDecimal);
		return CollectionUtils.listToArray(splitDecimalForList);

	}

	/**
	 * 将BigDecimal型的金额转换成金额显示形式(小数点后两位,多余位忽略)
	 * @param desAmount
	 * @return 金额(不为空)
	 */
	public static String toStringAmount(BigDecimal desAmount) {
		if(desAmount==null) {
			return "";
		}

		return desAmount.setScale(2, BigDecimal.ROUND_DOWN).toString();
	}

	/**
	 * 整数拆分
	 *
	 * @param sourDecimal 待拆分的数字 （350）
	 * @param splitDecimal 以什么数来拆分 （100,50）大的在前 小的再后
	 * @return｛100,100,100,50｝
	 * @throws ErrorMessageException
	 */
	public static List<BigDecimal> splitDecimalForList(BigDecimal sourDecimal, BigDecimal[] splitDecimal) {

		if (sourDecimal == null || splitDecimal == null) {
			throw new SystemException("ec.commons.topupamounterror");
		}
		int splitLength = splitDecimal.length;
		if (splitLength == 0) {
			throw new SystemException("ec.commons.topupamounterror");
		}
		List<BigDecimal> amountList = new ArrayList<BigDecimal>();
		BigDecimal splitDecimal_i = BIGDEC_ZERO;
		for (int i = 0; i < splitLength; i++) {
			if (i != 0 && splitDecimal[i].compareTo(splitDecimal_i) >= 0) {
				throw new SystemException("ec.commons.topupamounterror");
			}
			BigDecimal[] bdFeeDividedBy_i = sourDecimal.divideAndRemainder(splitDecimal[i]);
			int split_i_leng = bdFeeDividedBy_i[0].intValue();
			for (int l = 0; l < split_i_leng; l++) {
				amountList.add(splitDecimal[i]);
			}
			if (i == (splitLength - 1) && bdFeeDividedBy_i[1].compareTo(BIGDEC_ZERO) != 0) {
				throw new SystemException("ec.commons.topupamounterror");
			}
			sourDecimal = sourDecimal.add((splitDecimal[i].multiply(bdFeeDividedBy_i[0])).negate());
			splitDecimal_i = splitDecimal[i];
		}

		return amountList;
	}

	/**
	 * 验证指定字符串是否为指定长度的数字字符串
	 *
	 * @param numStr
	 * @param length
	 * @return
	 */
	public static boolean isNumberForLength(String numStr, int length) {
		String regex = "[0-9]{" + length + "}";
		return numStr != null && Pattern.matches(regex, numStr);
	}

	/**
	 * 验证指定字符串是否为指定长度的数字字符串
	 *
	 * @param numStr
	 * @param length
	 * @return
	 */
	public static boolean isNumberForLength(String numStr) {
		if (numStr == null) {
			return false;
		}

		return isNumberForLength(numStr, numStr.length());
	}

	/**
	 * 是数字
	 *
	 * @param str
	 * @return
	 */
	public static boolean isDouble(String str) {
		try {
			Double.valueOf(str);
			return true;
		} catch (Throwable e) {
			return false;
		}
	}

	/**
	 * 是整数
	 *
	 * @param str
	 * @return
	 */
	public static boolean isLong(String str) {
		try {
			Long.valueOf(str);
			return true;
		} catch (Throwable e) {
			return false;
		}
	}

	/**
	 * 是整数
	 *
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {
		//如果字符串根本不是数字,则直接返回false;
		if (!isNumber(str)) {
			return false;
		}

		//不过数字不包含小数点,则直接视为整数
		int dotIndex = str.indexOf('.');
		if (dotIndex == -1) {
			return true;
		}

		//对于存在小数点的,将其放大一万倍,看齐是否大于1来判断是否为整数
		String substring = "0" + str.substring(dotIndex);
		double valueOf = Double.valueOf(substring).doubleValue();
		return valueOf * 100000000 < 1;
	}

	private static boolean isNumber(String str) {
		try{
			Double.parseDouble(str);
			return true;
		}catch(Throwable t){
			return false;
		}
	}

	/**
	 * 是金钱
	 *
	 * @param money
	 * @return
	 */
	public static boolean isMoney(String money) {
		if (!isNumber(money)) {
			return false;
		}
		if (money.equals("0")) {
			return false;
		}
		for (int i = 0; i < money.length(); i++) {
			char ch = money.charAt(i);
			if (i == 0) {
				if (ch == '.') {
					return false;
				}
				if (ch == '0') {
					char ch1 = money.charAt(i + 1);
					if (ch1 != '.') {
						return false;
					}
				}
			}
			if (!isDigital(ch)) {
				return false;
			}
		}
		try {
			double d = Double.parseDouble(money);

			if (d < 0.01) {

				return false;
			}
		} catch (Exception e) {
			return false;
		}
		if (money.contains(".")) {
			int start = money.indexOf(".") + 1;
			String suffix = money.substring(start, money.length());
			if (suffix.length() > 2) {
				return false;
			}
		}
		return true;
	}

	public static boolean isDigital(char c) {
		if (c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9' || c == '0' || c == '.') {
			return true;
		}
		return false;
	}

	/**
	 * 校验身份证的校验码
	 */
	@SuppressWarnings("unchecked")
	public static boolean verifyCid(String idcard) {
		Map month = new HashMap();
		month.put("01", "01");
		month.put("03", "03");
		month.put("05", "05");
		month.put("07", "07");
		month.put("08", "08");
		month.put("10", "10");
		month.put("12", "12");

		if (!isNumber(idcard)) {
			return false;
		}
		if (idcard.length() == 15) {
			idcard = uptoeighteen(idcard);
		}
		if (idcard.length() != 18) {
			return false;
		}
		if (month.containsKey(idcard.substring(10, 12))) {
			if (Integer.valueOf(idcard.substring(12, 14)) > 31) {
				return false;
			}
		} else if (idcard.substring(10, 12).equals("02")) {
			if (Integer.valueOf(idcard.substring(12, 14)) > 29) {
				return false;
			}
		} else {
			if (Integer.valueOf(idcard.substring(12, 14)) > 30) {
				return false;
			}
		}
		String verify = idcard.substring(17, 18);
		if (verify.equals(getVerify(idcard))) {
			return true;
		}
		return false;
	}

	// 15位转18位
	public static String uptoeighteen(String fifteen) {
		String eightcardid = fifteen.substring(0, 6);
		eightcardid = eightcardid + "19";
		eightcardid = eightcardid + fifteen.substring(6, 15);
		eightcardid = eightcardid + getVerify(eightcardid);
		return eightcardid;
	}

	// 计算最后一位校验值
	public static String getVerify(String eighteen) {
		// wi =2(n-1)(mod 11);加权因子
		int[] wi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 };
		// 校验码
		int[] vi = { 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 };
		int[] ai = new int[18];

		int remain = 0;
		if (eighteen.length() == 18) {
			eighteen = eighteen.substring(0, 17);
		}
		if (eighteen.length() == 17) {
			int sum = 0;
			for (int i = 0; i < 17; i++) {
				String k = eighteen.substring(i, i + 1);
				ai[i] = Integer.valueOf(k);
			}
			for (int i = 0; i < 17; i++) {
				sum += wi[i] * ai[i];
			}
			remain = sum % 11;
		}
		return remain == 2 ? "X" : String.valueOf(vi[remain]);
	}

	/**
	 * 转换数字为大写金额的字符(注:小数点后最多支持两位[角分])
	 *
	 * @param value
	 * @return
	 */
	public static String numberToBig(double value) {
		char[] hunit = { '拾', '佰', '仟' }; //段内位置表示
		char[] vunit = { '万', '亿' }; //段名表示
		char[] digit = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' };//数字表示
		long midVal = (long) (value * 100); //转化成整形
		String valStr = String.valueOf(midVal); //转化成字符串
		String head = "";
		String rail = "";
		if(valStr .equals( "0")){
			return "零元";
		}else if(valStr.length()<2){
			head = "0";
			rail = "0"+valStr; //取小数部分
		}else{
			head = valStr.substring(0, valStr.length() - 2); //取整数部分
			rail = valStr.substring(valStr.length() - 2); //取小数部分
		}
		String prefix = ""; //整数部分转化的结果
		String suffix = ""; //小数部分转化的结果
		//处理小数点后面的数
		if (rail.equals("00"))//如果小数部分为0
		{
			suffix = "整";
		} else {
			suffix = digit[rail.charAt(0) - '0'] + "角" + digit[rail.charAt(1) - '0'] + "分";
		}
		//处理小数点前面的数
		char[] chDig = head.toCharArray(); //把整数部分转化成字符数组
		char zero = '0'; //标志'0'表示出现过0
		byte zeroSerNum = 0; //连续出现0的次数
		for (int i = 0; i < chDig.length; i++) { //循环处理每个数字
			int idx = (chDig.length - i - 1) % 4; //取段内位置
			int vidx = (chDig.length - i - 1) / 4; //取段位置
			if (chDig[i] == '0') { //如果当前字符是0
				zeroSerNum++; //连续0次数递增
				if (zero == '0') { //标志
					zero = digit[0];
				} else if (idx == 0 && vidx > 0 && zeroSerNum < 4) {
					prefix += vunit[vidx - 1];
					zero = '0';
				}
				continue;
			}
			zeroSerNum = 0; //连续0次数清零
			if (zero != '0') { //如果标志不为0,则加上,例如万,亿什么的
				prefix += zero;
				zero = '0';
			}
			prefix += digit[chDig[i] - '0']; //转化该数字表示
			if (idx > 0)
				prefix += hunit[idx - 1];
			if (idx == 0 && vidx > 0) {
				prefix += vunit[vidx - 1];
			}//段结束位置应该加上段名如万,亿
		}

		if (prefix.length() > 0)
			prefix += '元'; //如果整数部分存在,则有圆的字样
		String result =  prefix + suffix; //返回正确表示
		if(result.startsWith("零角")){
			return result.substring(2);
		}
		
		return result;
	}

	//测试hangeToBig方法
	public static void main(String args[]) {
//		System.out.println(toStringAmount(new BigDecimal("-63.3567")));
//		System.out.println(toStringAmount(new BigDecimal("-63.35")));
//		System.out.println(toStringAmount(new BigDecimal("-63.3548")));
//		System.out.println(toStringAmount(new BigDecimal("-63.0000")));
//		System.out.println(toStringAmount(new BigDecimal("-63")));
//		System.out.println(toStringAmount(new BigDecimal("-63.00")));
		
		
		System.out.println(rightMove("0.11",2));
		System.out.println(numberToBig(0));
		System.out.println(numberToBig(10.1));
		System.out.println(numberToBig(1000.01));
		System.out.println(numberToBig(0.06));
		
	}

	/**
	 * 将类似于"1,2,3"样的字符串分隔成长整型数组
	 *
	 * @param parentMenuIds
	 * @return
	 */
	public static Long[] splitLong(String parentMenuIds) {
		if (StringUtils.isBlank(parentMenuIds)) {
			return null;
		}

		String[] longStrArr = parentMenuIds.split(",");
		Long[] longArr = new Long[longStrArr.length];
		for (int i = 0; i < longStrArr.length; i++) {
			if (!isLong(longStrArr[i])) {
				throw new SystemException("无法将字符串[" + longStrArr[i] + "]转换为长整型");
			}

			longArr[i] = Long.parseLong(longStrArr[i]);
		}
		return longArr;
	}

	/**
	 * 取得指定范围的随机数 注:取得的值不包含最大值(例如:max=3时值为0,1,2)
	 *
	 * @param maxValue
	 * @return
	 */
	public static int getRandomIntInMax(int maxValue) {
		return (int) (Math.random() * maxValue % maxValue);
	}

	/**
	 * 对资金进行格式化
	 * @param amount
	 * @return
	 */
	public static String formatAmount(BigDecimal amount){
		if(amount==null){
			return null;
		}

		return amount.setScale(2, BigDecimal.ROUND_DOWN).toString();
	}
	
	
	/**
	 * 小数点右移
	 * @param number 
	 * @param n 右移的位数
	 * @return 移位后的数字字符串
	 */
	public static String rightMove(String number,int n){
		BigDecimal decimal = new BigDecimal(number);
		return decimal.movePointRight(n).toString();
	}
	/**
	 * 小数点左移，
	 * @param number
	 * @param n 移位后的字符串
	 * @return
	 */
	public static String leftMove(String number,int n){
		BigDecimal bd = new BigDecimal(number);
		return bd.movePointLeft(n).toString();
	
	}
	/**
	 * 相加
	 * @param number1
	 * @param number2
	 * @return
	 */
	public static String add(String number1,String number2){
		BigDecimal bd = new BigDecimal(number1);
		return bd.add(new BigDecimal(number2)).toString();
	}
}

package com.krm.web.util;

import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TypeChecker {

	private final static Log log = LogFactory.getLog(TypeChecker.class);

	/**
	 * 
	 * @param str
	 *            -
	 * @return java.lang.Boolean
	 * @roseuid 3E01F9AB028B
	 */
	public static boolean isEmail(String str) {
		if (str == null || str.trim() == "") {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param str
	 * @return java.lang.Boolean
	 * @roseuid 3E01F9BB02F2
	 */
	public static boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
		}
		catch (Exception ex) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param str
	 * @return java.lang.Boolean
	 * @roseuid 3E70325B0297
	 */
	public static boolean isLong(String str) {
		try {
			Long.parseLong(str);
		}
		catch (Exception ex) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param str
	 * @return java.lang.Boolean
	 * @roseuid 3E70341A025E
	 */
	public static boolean isEmpty(String str) {
		if (str == null || str.equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isEmpty(String[] str) {
		if (str == null || (str.length <= 0)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isEmpty(StringBuffer strbuffer) {
		if (strbuffer == null || (strbuffer.length() <= 0)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param map
	 * @return boolean
	 */
	public static boolean isEmpty(java.util.Map map) {
		if (map == null || map.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param list
	 * @return boolean
	 */
	public static boolean isEmpty(java.util.List list) {
		if (list == null || list.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param collection
	 * @return boolean
	 */
	public static boolean isEmpty(Collection collection) {
		if (collection == null || collection.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param arrayList
	 * @return boolean
	 */
	public static boolean isEmpty(ArrayList arrayList) {
		if (arrayList == null || arrayList.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param Object
	 * @return boolean
	 */
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param str
	 *            -
	 * @return java.lang.Boolean
	 * @roseuid 3E01F9C902B6
	 */
	public static boolean isFloat(String str) {
		try {
			Float.parseFloat(str);
			return true;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 
	 * @param str
	 *            -
	 * @return java.lang.Boolean
	 * @roseuid 3E01FA820013
	 */
	public static boolean isNumber(String str) {
		try {
			Double.parseDouble(str);
			return true;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * @param str
	 *            -
	 * @return java.lang.Boolean
	 * @roseuid 3E01F9DC02BE
	 */
	public static boolean isDate(String str) {
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
		try {
			java.util.Date date = df.parse(str);
			String apple = df.format(date);
			if (!apple.equals(str)) {
				return false;
			}
		}
		catch (Exception ex) {
			return false;
		} // end try - catch
		return true;
	}

	/**
	 * 
	 * @param str
	 *            -
	 * @return java.lang.Boolean
	 * @roseuid 3E01F9F20157
	 */
	public static boolean isDatetime(String str) {
		if (str.length() < 19) {
			return false;
		}
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		java.text.SimpleDateFormat df400 = new java.text.SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS");
		if (!str.endsWith(".") && str.length() == 19) {
			str = str + ".";
		} // end if
		while (str.length() < 23) {
			str = str + "0";
		} // end while
		try {
			java.util.Date date = df.parse(str);
			String apple = df.format(date);
			if (!apple.equals(str)) {
				return false;
			}
		}
		catch (java.text.ParseException ex) {
			try {
				java.util.Date date2 = df400.parse(str);
				String apple1 = df400.format(date2);
				if (!apple1.equals(str)) {
					return false;
				}
			}
			catch (java.text.ParseException exa) {
				log.error(exa);
				return false;
			}
			return true;
		}
		return true;
	}

	/**
	 * 
	 * @param str
	 *            -
	 * @return java.lang.Boolean
	 * @roseuid 3E01FA12029D
	 */
	public static boolean isTime(String str) {
		return true;
	}

	/**
	 * 
	 * @param decimal
	 *            -
	 * @return java.lang.Boolean
	 * @roseuid 3E01FA12029D
	 */
	public static void isDecimal(String decimal) throws Exception {
		if (isEmpty(decimal)) {
			return;
		}
		int commaPos = decimal.indexOf(",");
		if (commaPos < 1) {
			throw new Exception(decimal + " Decimal Exception 10,2");
		}
		int fractionValue = Integer.parseInt(decimal.substring(commaPos + 1));
		int integerValue = Integer.parseInt(decimal.substring(0, commaPos)) - fractionValue + 1;
		if (integerValue < 1 || fractionValue < 0) {
			throw new Exception(decimal + " Decimal Exception!");
		}
		if (fractionValue == 0) {
			throw new Exception("Type decimal integer");
		}
	}
	
	public static boolean nonEmpty(String str) {
		return !isEmpty(str);
	}
	public static boolean nonEmpty(String[] str) {
		return !isEmpty(str);
	}
	public static boolean nonEmpty(StringBuffer strbuffer) {
		return !isEmpty(strbuffer);
	}
	public static boolean nonEmpty(java.util.Map map) {
		return !isEmpty(map);
	}
	public static boolean nonEmpty(java.util.List list) {
		return !isEmpty(list);
	}
	public static boolean nonEmpty(Collection collection) {
		return !isEmpty(collection);
	}
	public static boolean nonEmpty(Object obj) {
		return !isEmpty(obj);
	}
	public static boolean nonEmpty(ArrayList arrayList) {
		return !isEmpty(arrayList);
	}
}

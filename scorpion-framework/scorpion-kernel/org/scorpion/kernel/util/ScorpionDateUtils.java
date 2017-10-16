package org.scorpion.kernel.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.scorpion.api.exception.ScorpionBaseException;

/**
 * 天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>
 * com.SCORPION.Scorpion.common
 * <p>
 * File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37
 * </p>
 * <p>
 * Title: abstract factory class
 * </p>
 * <p>
 * Description: the annotation is used to signal the method of component
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015 SCORPION.COM.CN
 * </p>
 * <p>
 * Company: SCORPION.COM.CN
 * </p>
 * <p>
 * module: common abstract class
 * </p>
 * 
 * @author 郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class ScorpionDateUtils {

	/**
	 * 预定的日期格式
	 */
	public static final String[] dateFormat = { "yyyy-MM-dd HH:mm:ss","yyyy/MM/dd HH:mm:ss", "yyyy年MM月dd日HH时mm分ss秒", "yyyy-MM-dd",
			"yyyy/MM/dd", "yy-MM-dd", "yy/MM/dd", "yyyy年MM月dd日", "HH:mm:ss",
			"yyyyMMddHHmmss", "yyyyMMdd", "yyyy.MM.dd", "yy.MM.dd",
			"yyyyMMddHHmmssSSS", "yyyy-MM-dd HH:mm:ss:SSS",
			"yyyy-MM-dd HH:mm:ss.SSS", "yyyy", "yyyyMM", "yyyyMMdd HH",
			"yyyyMMdd HH:mm", "yyyyMMdd HH:mm:ss" };

	/**
	 * 线程绑定的日期格式转换器缓存
	 */
	private final static ThreadLocal<Map<String, SimpleDateFormat>> dateFormatersCache = new ThreadLocal<Map<String, SimpleDateFormat>>();

	/**
	 * 获取当前系统时间
	 *
	 * @Description 相关说明
	 * @param format
	 * @return
	 * @Time 创建时间:2014年8月2日下午4:14:45
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getSystemCurrentTime(int format) {
		return toDateStrByFormatIndex(Calendar.getInstance(), format);
	}

	/**
	 * 获取当前系统时间
	 *
	 * @Description 相关说明
	 * @param format
	 * @return
	 * @Time 创建时间:2014年8月2日下午4:14:45
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getSystemCurrentTime(String format) {
		return toDateStrByFormat(Calendar.getInstance(), format);
	}

	// ======================日期转字符串基础格式化方法======================================================================================

	private static SimpleDateFormat getDateFormater(String format) {
		Map<String, SimpleDateFormat> dateFormaters = dateFormatersCache.get();
		SimpleDateFormat dateFormater = null;
		boolean formatersIsNull = false;

		if (dateFormaters == null) {
			dateFormaters = new HashMap<String, SimpleDateFormat>(3, 0.2f);
			dateFormatersCache.set(dateFormaters);
			formatersIsNull = true;
		}

		if (formatersIsNull || (dateFormater = dateFormaters.get(format)) == null) {
			dateFormater = new SimpleDateFormat(format);
			dateFormaters.put(format, dateFormater);
		}

		return dateFormater;
	}

	private static SimpleDateFormat getDateFormater(int format) {
		return getDateFormater(dateFormat[format]);
	}

	// ======================日期转字符串基础方法======================================================================================

	public static String toDateStrByFormat(Calendar date, String format) {
		if (date == null) {
			return null;
		}
		return getDateFormater(format).format(date.getTime());
	}

	public static String toDateStrByFormatIndex(Calendar date, int format) {
		return toDateStrByFormat(date, dateFormat[format]);
	}

	/**
	 * 
	 * java.util.Date日期转指定日期格式的字符串
	 * 
	 * @Description java.util.Date日期转指定日期格式的字符串<br>
	 * @param date
	 *            日期对象
	 * @param format
	 *            指定的日期格式，对应日期数组<code>dateFormat[format]</code>的值
	 * @return String 返回日期字符串，如果需要转换的java.util.Date日期对象为null，那么返回null
	 * @Time 创建时间:2011-12-29下午3:31:05
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String toDateStrByFormat(Date date, String format) {
		if (date == null) {
			return null;
		}
		return getDateFormater(format).format(date.getTime());
	}

	/**
	 * 
	 * java.util.Date日期转指定格式的字符串
	 * 
	 * @Description java.util.Date日期转指定格式的字符串<br>
	 * @param date
	 *            日期对象
	 * @param format
	 *            指定的日期格式，对应日期数组<code>dateFormat[format]</code>的值
	 * @return String 返回日期字符串，如果需要转换的java.util.Date日期对象为null，那么返回null
	 * @Time 创建时间:2011-12-29下午3:35:05
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String toDateStrByFormatIndex(Date date, int format) {
		return toDateStrByFormat(date, dateFormat[format]);
	}

	// ======================日期转字符串方法======================================================================================

	/**
	 * 
	 * Calendar日期转日期字符串
	 * 
	 * @Description Calendar日期转日期字符串，格式为"yyyy-MM-dd HH:mm:ss"
	 * @param date
	 *            日期对象
	 * @return String 返回日期字符串，如果需要转换的Calendar日期对象为null，那么返回null
	 * @Time 创建时间:2011-12-29下午3:35:05
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String toDateTimeStr(Calendar date) {
		return toDateStrByFormatIndex(date, 0);
	}

	/**
	 * 
	 * Calendar日期转指定日期格式的字符串
	 * 
	 * @Description Calendar日期转指定日期格式的字符串
	 * @param format
	 *            指定的日期格式，对应日期数组<code>dateFormat[format]</code>的值
	 * @param date
	 *            日期对象
	 * @return String 返回日期字符串，如果需要转换的Calendar日期对象为null，那么返回null
	 * @Time 创建时间:2011-12-29下午3:31:05
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String toDateTimeStr(int format, Calendar date) {
		return toDateStrByFormatIndex(date, format);
	}

	/**
	 * 
	 * Calendar日期转日期字符串
	 * 
	 * @Description Calendar日期转日期字符串，格式为"yyyy-MM-dd"
	 * @param date
	 *            日期对象
	 * @return String 返回日期字符串，如果需要转换的Calendar日期对象为null，那么返回null
	 * @Time 创建时间:2011-12-29下午3:35:05
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String toDateStr(Calendar date) {
		return toDateStrByFormatIndex(date, 3);
	}

	/**
	 * 
	 * java.util.Date日期转指定格式的日期字符串
	 * 
	 * @Description java.util.Date日期转换成指定格式的日期字符串
	 * @param date
	 *            日期对象
	 * @param format
	 *            指定的日期格式，对应日期数组<code>dateFormat[format]</code>的值
	 * @return String 返回日期字符串，如果需要转换的java.util.Date日期对象为null，那么返回null
	 * @Time 创建时间:2011-12-29下午3:35:05
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String dateToString(Date date, int format) {
		return toDateStrByFormatIndex(date, format);
	}

	/**
	 * 
	 * java.util.Date日期转日期字符串
	 * 
	 * @Description java.util.Date日期转日期字符串，格式为"yyyy-MM-dd"
	 * @param date
	 *            日期对象
	 * @return String 返回日期字符串，如果需要转换的java.util.Date日期对象为null，那么返回null
	 * @Time 创建时间:2011-12-29下午3:35:05
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String dateToString(Date date) {
		return toDateStrByFormatIndex(date, 3);
	}

	// ======================xx转Date方法======================================================================================

	/**
	 * 
	 * Calendar日期转java.util.Date日期
	 * 
	 * @Description Calendar日期转java.util.Date日期
	 * @param c
	 *            需要转换的Calendar日期
	 * @return Date 返回java.util.Date日期
	 * @Time 创建时间:2011-12-29下午3:45:05
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static Date convertCalendarToDate(Calendar c) {
		Date d = new Date();
		d.setTime(c.getTimeInMillis());
		return d;
	}

	public static Date StringToDate(String dateStr) throws ScorpionBaseException {
		return parseDate(dateStr, 3);
	}

	public static Date parseDate(String dateStr, int format)throws ScorpionBaseException {
		
		if (dateStr == null || dateStr.length() == 0) {
			return null;
		}

		try {
			return getDateFormater(format).parse(dateStr);
		} catch (ParseException ex) {
			return parseDate(dateStr, format + 1);
		} catch (ArrayIndexOutOfBoundsException ex) {
			throw new ScorpionBaseException("UT-07001:日志字符串" + dateStr + "格式不支持",ex);
		}
	}

	// ======================xx转Calendar方法======================================================================================

	public static Calendar convUtilDateToUtilCalendar(Date date) {
		if (date == null) {
			return null;
		}

		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeInMillis(date.getTime());

		return gc;
	}

	public static Calendar convSqlTimestampToUtilCalendar(Timestamp date) {
	
		if (date == null) {
			return null;
		}
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeInMillis(date.getTime());
		return gc;
	}

	public static Calendar parseDate(String dateStr) throws ScorpionBaseException {
		
		Date result = parseDate(dateStr, 0);
		Calendar cal = null;

		if (result != null) {
			cal = new GregorianCalendar(0, 0, 0, 0, 0, 0);
			cal.setTime(result);
		}

		return cal;
	}

	// ======================日期转Timestamp方法======================================================================================

	/**
	 * 
	 * java.util.Date转java.sql.Timestamp
	 * 
	 * @Description java.util.Date转java.sql.Timestamp
	 * @param date
	 *            需要转换的java.util.Date对象
	 * @return Timestamp 返回Timestamp日期对象，如果需要转换的java.util.Date日期对象为null，那么返回null
	 * @Time 创建时间:2011-12-29上午11:07:08
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static Timestamp convUtilDateToSqlTimestamp(Date date) {
		if (date == null) {
			return null;
		}
		return new Timestamp(date.getTime());
	}

	/**
	 * 
	 * Calendar日期对象转Timestamp日期对象
	 * 
	 * @Description Calendar日期对象转Timestamp日期对象
	 * @param calendar
	 *            需要转换的Calendar日期对象
	 * @return Timestamp 返回Timestamp日期对象，如果需要转换的Calendar日期对象为null，那么返回null
	 * @Time 创建时间:2011-12-29上午11:07:31
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static Timestamp convUtilCalendarToSqlTimestamp(Calendar date) {
		
		if (date == null) {
			return null;
		}
		return new Timestamp(date.getTimeInMillis());
	}

	/**
	 * 
	 * Calendar日期对象转Timestamp日期对象
	 * 
	 * @Description Calendar日期对象转Timestamp日期对象
	 * @param calendar
	 *            需要转换的Calendar日期对象
	 * @return Timestamp 返回Timestamp日期对象
	 * @Time 创建时间:2011-12-29上午11:07:31
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static Timestamp parseTimestamp(Calendar calendar) {
		return new Timestamp(calendar.getTimeInMillis());
	}

	public static Timestamp parseTimestamp(String dateStr)throws ScorpionBaseException {
		try {
			return new Timestamp(getDateFormater(3).parse(dateStr).getTime());
		} catch (ParseException ex) {
			throw new ScorpionBaseException("UT-07001:日志字符串" + dateStr
					+ "格式不正确，格式:" + dateFormat[3], ex);
		}
	}

	public static Timestamp parseTimestamp(String dateStr, int format)throws ScorpionBaseException {
		try {
			return new Timestamp(getDateFormater(format).parse(dateStr).getTime());
		} catch (ParseException ex) {
			throw new ScorpionBaseException("UT-07001:日志字符串" + dateStr + "格式不支持",ex);
		}
	}

	// ======================日期计算方法======================================================================================

	/**
	 * 
	 * 获取两个Calendar日期对象的天数差
	 * 
	 * @Description 计算两个Calendar日期对象的天数差,返回int型天数差
	 * @param d1
	 *            需要计算的日期
	 * @param d2
	 *            需要计算的日期
	 * @return int 返回两个Calendar日期对象的天数差
	 * @Time 创建时间:2011-12-29上午11:07:46
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static int calendarMinus(Calendar d1, Calendar d2) {
		if (d1 == null || d2 == null) {
			return 0;
		}

		d1.set(11, 0);
		d1.set(12, 0);
		d1.set(13, 0);
		d1.set(14, 0);

		d2.set(11, 0);
		d2.set(12, 0);
		d2.set(13, 0);
		d2.set(14, 0);

		long t1 = d1.getTimeInMillis();
		long t2 = d2.getTimeInMillis();
		long daylong = 86400000L;
		t1 -= t1 % daylong;
		t2 -= t2 % daylong;

		long t = t1 - t2;
		int value = (int) (t / daylong);

		return value;
	}

	/**
	 * 
	 * 获取两个Calendar日期对象的天数差
	 * 
	 * @Description 计算两个Calendar日期对象的天数差,返回long型天数差
	 * @param d1
	 *            需要计算的日期
	 * @param d2
	 *            需要计算的日期
	 * @return long 返回两个Calendar日期对象的天数差
	 * @Time 创建时间:2011-12-29上午11:07:53
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static long calendarminus(Calendar d1, Calendar d2) {
		if (d1 == null || d2 == null) {
			return 0L;
		}
		return (d1.getTimeInMillis() - d2.getTimeInMillis()) / 86400000L;
	}

	/**
	 * 
	 * 给定任意日期Calendar对象，计算所在月天数
	 * 
	 * @Description 给定任意日期Calendar对象，计算所在月天数
	 * @param date
	 *            需要计算的日期
	 * @return int 返回所在月的天数
	 * @Time 创建时间:2011-12-29上午11:07:59
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static int calcMonthDays(Calendar date) {
		Calendar t1 = (Calendar) date.clone();
		Calendar t2 = (Calendar) date.clone();
		int year = date.get(1);
		int month = date.get(2);
		t1.set(year, month, 1);
		t2.set(year, month + 1, 1);
		t2.add(6, -1);
		return calendarMinus(t2, t1) + 1;
	}

	private static int calcDays(long t1, long t2) {
		long millis = t1 - t2;
		if (millis == 0) {
			return 0;
		}
		return (int) (millis / (24 * 3600 * 1000));
	}

	/**
	 * 
	 * 获取两个Calendar日期对象的天数差
	 * 
	 * @Description 计算两个Calendar日期对象的天数差,返回int型天数差
	 * @param d1
	 *            需要计算的日期
	 * @param d2
	 *            需要计算的日期
	 * @return int 返回两个Calendar日期对象的天数差
	 * @Time 创建时间:2011-12-29上午11:07:53
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static int calcDays(Calendar c1, Calendar c2) {
		return calcDays(c1.getTimeInMillis(), c2.getTimeInMillis());
	}

	/**
	 * 
	 * 获取两个java.util.Date日期对象的天数差
	 * 
	 * @Description 计算两个java.util.Date日期对象的天数差，返回int型天数差
	 * @param d1
	 *            需要计算的日期
	 * @param d2
	 *            需要计算的日期
	 * @return int 返回两个日期对象的天数差
	 * @Time 创建时间:2011-12-29上午11:07:53
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static int calcDays(Date d1, Date d2) {
		return calcDays(d1.getTime(), d2.getTime());
	}

	public static Calendar lastDay(Calendar c) {
		Calendar t = (Calendar) c.clone();
		t.set(Calendar.DAY_OF_MONTH, 1);
		t.add(Calendar.MONTH, 1);
		t.add(Calendar.DAY_OF_MONTH, -1);
		return t;
	}

	public static Calendar lastDay(String dateStr) throws ScorpionBaseException {
		Calendar t = parseDate(dateStr);
		t.set(Calendar.DAY_OF_MONTH, 1);
		t.add(Calendar.MONTH, 1);
		t.add(Calendar.DAY_OF_MONTH, -1);
		return t;

	}

	public static Calendar[] calcAQuarter(Calendar day) {
		Calendar[] quarter = new Calendar[2];

		int month = 0;
		quarter[0] = (Calendar) day.clone();
		month = quarter[0].get(Calendar.MONTH);

		if (month < 3) {
			month = 0;
		} else if (month < 6) {
			month = 3;
		} else if (month < 9) {
			month = 6;
		} else {
			month = 9;
		}

		quarter[0].set(Calendar.MONTH, month);
		quarter[0].set(Calendar.DAY_OF_MONTH, 1);

		quarter[1] = (Calendar) quarter[0].clone();
		quarter[1].set(Calendar.MONTH, month + 2);
		quarter[1] = lastDay(quarter[1]);

		return quarter;
	}

	/**
	 * 获取年、月、日、时、分、秒、毫秒
	 * 
	 * @Description 相关说明
	 * @param calendar
	 * @return
	 * @Time 创建时间:2014年6月23日上午11:26:45
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static int[] getYearMonthDayHH24MiMM(Calendar calendar) {
		return new int[] { calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH) + 1,
				calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND),
				calendar.get(Calendar.MILLISECOND) };
	}

	/**
	 * 获取年、月、日、时、分、秒、毫秒
	 * 
	 * @Description 相关说明
	 * @param date
	 * @return
	 * @Time 创建时间:2014年6月23日上午11:26:45
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static int[] getYearMonthDayHH24MiMM(Date date) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return getYearMonthDayHH24MiMM(calendar);
	}

}
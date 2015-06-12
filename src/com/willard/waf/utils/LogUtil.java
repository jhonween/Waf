package com.willard.waf.utils;

import java.util.Locale;

import android.content.Context;
import android.util.Log;

/**
 * 日志类
 * @LogUtil.java
 * @author willard
 * @2015-6-12
 */
public class LogUtil {
	private LogUtil() {
	}

	public static boolean allowD = true;
	public static boolean allowE = true;
	public static boolean allowI = true;
	public static boolean allowV = true;
	public static boolean allowW = true;
	public static boolean allowWtf = true;

	// d
	public static void d(String tag, String content) {
		if (!allowD)
			return;
		Log.d(tag, buildMessage(content));
	}

	public static void d(Context context, String content) {
		if (!allowD)
			return;
		String tag = context.getClass().getSimpleName();
		d(tag, content);
	}

	public static void d(Class<?> clazz, String content) {
		if (!allowD)
			return;
		String tag = clazz.getSimpleName();
		d(tag, content);
	}

	public static void d(String tag, String content, Throwable tr) {
		if (!allowD)
			return;
		Log.d(tag, buildMessage(content), tr);
	}

	public static void d(Context context, String content, Throwable tr) {
		if (!allowD)
			return;
		String tag = context.getClass().getSimpleName();
		d(tag, content, tr);
	}

	public static void d(Class<?> clazz, String content, Throwable tr) {
		if (!allowD)
			return;
		String tag = clazz.getSimpleName();
		d(tag, content, tr);
	}

	// e
	public static void e(String tag, String content) {
		if (!allowE)
			return;
		Log.e(tag, buildMessage(content));
	}

	public static void e(Context context, String content) {
		if (!allowE)
			return;
		String tag = context.getClass().getSimpleName();
		e(tag, content);
	}

	public static void e(Class<?> clazz, String content) {
		if (!allowE)
			return;
		String tag = clazz.getSimpleName();
		e(tag, content);
	}

	public static void e(String tag, String content, Throwable tr) {
		if (!allowE)
			return;
		Log.e(tag, buildMessage(content), tr);
	}

	public static void e(Context context, String content, Throwable tr) {
		if (!allowE)
			return;
		String tag = context.getClass().getSimpleName();
		e(tag, content, tr);
	}

	public static void e(Class<?> clazz, String content, Throwable tr) {
		if (!allowE)
			return;
		String tag = clazz.getSimpleName();
		e(tag, content, tr);
	}

	// i
	public static void i(String tag, String content) {
		if (!allowI)
			return;
		Log.i(tag, buildMessage(content));
	}

	public static void i(Context context, String content) {
		if (!allowI)
			return;
		String tag = context.getClass().getSimpleName();
		i(tag, content);
	}

	public static void i(Class<?> clazz, String content) {
		if (!allowI)
			return;
		String tag = clazz.getSimpleName();
		i(tag, content);
	}

	public static void i(String tag, String content, Throwable tr) {
		if (!allowI)
			return;
		Log.i(tag, buildMessage(content), tr);
	}

	public static void i(Context context, String content, Throwable tr) {
		if (!allowI)
			return;
		String tag = context.getClass().getSimpleName();
		i(tag, content, tr);
	}

	public static void i(Class<?> clazz, String content, Throwable tr) {
		if (!allowI)
			return;
		String tag = clazz.getSimpleName();
		i(tag, content, tr);
	}

	// v
	public static void v(String tag, String content) {
		if (!allowV)
			return;
		Log.v(tag, buildMessage(content));
	}

	public static void v(Context context, String content) {
		if (!allowV)
			return;
		String tag = context.getClass().getSimpleName();
		v(tag, content);
	}

	public static void v(Class<?> clazz, String content) {
		if (!allowV)
			return;
		String tag = clazz.getSimpleName();
		v(tag, content);
	}

	public static void v(String tag, String content, Throwable tr) {
		if (!allowV)
			return;
		Log.v(tag, buildMessage(content), tr);
	}

	public static void v(Context context, String content, Throwable tr) {
		if (!allowV)
			return;
		String tag = context.getClass().getSimpleName();
		v(tag, content, tr);
	}

	public static void v(Class<?> clazz, String content, Throwable tr) {
		if (!allowV)
			return;
		String tag = clazz.getSimpleName();
		v(tag, content, tr);
	}

	// w
	public static void w(String tag, String content) {
		if (!allowW)
			return;
		Log.w(tag, buildMessage(content));
	}

	public static void w(Context context, String content) {
		if (!allowW)
			return;
		String tag = context.getClass().getSimpleName();
		w(tag, content);
	}

	public static void w(Class<?> clazz, String content) {
		if (!allowW)
			return;
		String tag = clazz.getSimpleName();
		w(tag, content);
	}

	public static void w(String tag, String content, Throwable tr) {
		if (!allowW)
			return;
		Log.w(tag, buildMessage(content), tr);
	}

	public static void w(Context context, String content, Throwable tr) {
		if (!allowW)
			return;
		String tag = context.getClass().getSimpleName();
		w(tag, content, tr);
	}

	public static void w(Class<?> clazz, String content, Throwable tr) {
		if (!allowW)
			return;
		String tag = clazz.getSimpleName();
		w(tag, content, tr);
	}

	// wtf
	public static void wtf(String tag, String content) {
		if (!allowWtf)
			return;
		Log.wtf(tag, buildMessage(content));
	}

	public static void wtf(Context context, String content) {
		if (!allowWtf)
			return;
		String tag = context.getClass().getSimpleName();
		wtf(tag, content);
	}

	public static void wtf(Class<?> clazz, String content) {
		if (!allowWtf)
			return;
		String tag = clazz.getSimpleName();
		wtf(tag, content);
	}

	public static void wtf(String tag, String content, Throwable tr) {
		if (!allowWtf)
			return;
		Log.wtf(tag, buildMessage(content), tr);
	}

	public static void wtf(Context context, String content, Throwable tr) {
		if (!allowWtf)
			return;
		String tag = context.getClass().getSimpleName();
		wtf(tag, content, tr);
	}

	public static void wtf(Class<?> clazz, String content, Throwable tr) {
		if (!allowWtf)
			return;
		String tag = clazz.getSimpleName();
		wtf(tag, content, tr);
	}

	// -----format
	public static void d(String tag, String format, Object... args) {
		if (!allowD)
			return;
		d(tag, buildMessage(format, args));
	}

	public static void d(Context context, String format, Object... args) {
		if (!allowD)
			return;
		String tag = context.getClass().getSimpleName();
		d(tag, buildMessage(format, args));
	}

	public static void d(Class<?> clazz, String format, Object... args) {
		if (!allowD)
			return;
		String tag = clazz.getSimpleName();
		d(tag, buildMessage(format, args));
	}

	public static void e(String tag, String format, Object... args) {
		if (!allowE)
			return;
		e(tag, buildMessage(format, args));
	}

	public static void e(Context context, String format, Object... args) {
		if (!allowE)
			return;
		String tag = context.getClass().getSimpleName();
		e(tag, buildMessage(format, args));
	}

	public static void e(Class<?> clazz, String format, Object... args) {
		if (!allowE)
			return;
		String tag = clazz.getSimpleName();
		e(tag, buildMessage(format, args));
	}

	public static void i(String tag, String format, Object... args) {
		if (!allowI)
			return;
		i(tag, buildMessage(format, args));
	}

	public static void i(Context context, String format, Object... args) {
		if (!allowI)
			return;
		String tag = context.getClass().getSimpleName();
		i(tag, buildMessage(format, args));
	}

	public static void i(Class<?> clazz, String format, Object... args) {
		if (!allowI)
			return;
		String tag = clazz.getSimpleName();
		i(tag, buildMessage(format, args));
	}

	public static void v(String tag, String format, Object... args) {
		if (!allowV)
			return;
		v(tag, buildMessage(format, args));
	}

	public static void v(Context context, String format, Object... args) {
		if (!allowV)
			return;
		String tag = context.getClass().getSimpleName();
		v(tag, buildMessage(format, args));
	}

	public static void v(Class<?> clazz, String format, Object... args) {
		if (!allowV)
			return;
		String tag = clazz.getSimpleName();
		v(tag, buildMessage(format, args));
	}

	public static void w(String tag, String format, Object... args) {
		if (!allowW)
			return;
		w(tag, buildMessage(format, args));
	}

	public static void w(Context context, String format, Object... args) {
		if (!allowW)
			return;
		String tag = context.getClass().getSimpleName();
		w(tag, buildMessage(format, args));
	}

	public static void w(Class<?> clazz, String format, Object... args) {
		if (!allowW)
			return;
		String tag = clazz.getSimpleName();
		w(tag, buildMessage(format, args));
	}

	public static void wtf(String tag, String format, Object... args) {
		if (!allowWtf)
			return;
		wtf(tag, buildMessage(format, args));
	}

	public static void wtf(Context context, String format, Object... args) {
		if (!allowWtf)
			return;
		String tag = context.getClass().getSimpleName();
		wtf(tag, buildMessage(format, args));
	}

	public static void wtf(Class<?> clazz, String format, Object... args) {
		if (!allowWtf)
			return;
		String tag = clazz.getSimpleName();
		wtf(tag, buildMessage(format, args));
	}

	private static String buildMessage(String format, Object... args) {
		String msg = (args == null) ? format : String.format(Locale.US, format,
				args);
		StackTraceElement[] trace = new Throwable().fillInStackTrace()
				.getStackTrace();

		StringBuilder strBuilder = new StringBuilder();
		for (int i = 2; i < trace.length; i++) {
			Class<?> clazz = trace[i].getClass();
			if (!clazz.equals(LogUtil.class)) {
				String callingClass = trace[i].getClassName();
				callingClass = callingClass.substring(callingClass
						.lastIndexOf('.') + 1);
				callingClass = callingClass.substring(callingClass
						.lastIndexOf('$') + 1);
				strBuilder.append(callingClass).append(".")
						.append(trace[i].getMethodName());
				break;
			}
		}
		String tempStr = strBuilder.toString();
		String caller = tempStr.equalsIgnoreCase("") ? tempStr : "<unknown>";
		return String.format(Locale.US, "[%d] %s: %s", Thread.currentThread()
				.getId(), caller, msg);
	}
}

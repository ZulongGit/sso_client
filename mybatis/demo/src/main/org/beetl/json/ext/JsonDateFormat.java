package org.beetl.json.ext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.beetl.json.Format;
public class JsonDateFormat implements Format
{
	private static final String DEFAULT_KEY = "default";

	private ThreadLocal<Map<String, SimpleDateFormat>> threadlocal = new ThreadLocal<Map<String, SimpleDateFormat>>();

	public Object format(Object data, String pattern)
	{
		if (data == null)
			return null;
		if (Date.class.isAssignableFrom(data.getClass()))
		{
			SimpleDateFormat sdf = null;
			if (pattern == null)
			{
				sdf = getDateFormat(DEFAULT_KEY);
			}
			else
			{
				sdf = getDateFormat(pattern);
			}
			return sdf.format((Date) data);

		}
		else if (data.getClass() == Long.class)
		{
			Date date = new Date((Long) data);
			SimpleDateFormat sdf = null;
			if (pattern == null)
			{
				sdf = getDateFormat(DEFAULT_KEY);
			}
			else
			{
				sdf = getDateFormat(pattern);
			}
			return sdf.format(date);

		}
		else
		{
			throw new RuntimeException("参数错误，输入为日期或者Long:" + data.getClass());
		}

	}

	private SimpleDateFormat getDateFormat(String pattern)
	{
		Map<String, SimpleDateFormat> map = null;
		if ((map = threadlocal.get()) == null)
		{
			/**
			 * 初始化2个空间
			 */
			map = new HashMap<String, SimpleDateFormat>(4, 0.65f);
			threadlocal.set(map);
		}
		SimpleDateFormat format = map.get(pattern);
		if (format == null)
		{
			if (DEFAULT_KEY.equals(pattern))
			{
				format = new SimpleDateFormat();
			}
			else
			{
				format = new SimpleDateFormat(pattern);
			}
			map.put(pattern, format);
		}
		return format;
	}
}


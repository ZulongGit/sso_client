package org.beetl.json.util;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/** 一个缓存的字节和字符数组，用于减少beetl渲染各个过程中渲染字符数组
 * @author joelli
 *
 */
public class ContextLocal
{
	/**
	 *  初始化的字符数组大小
	 */
	public static int charBufferSize = 256;
	
	public static int writeBufferSize = 1024;
	
	public static int scopeSize = 32;
	
	private boolean scope[] = new boolean[scopeSize];
	

	private char[] charBuffer = new char[charBufferSize];
	
	
	Map<String, SimpleDateFormat> formats = new HashMap<String, SimpleDateFormat>(); 
	private static final String DEFAULT_KEY = "default";
	
	private StringBuilder buffer = new StringBuilder(writeBufferSize);
	
	static ThreadLocal<ContextLocal> threadLocal = new ThreadLocal<ContextLocal>() {
		protected ContextLocal initialValue()
		{
			return new ContextLocal();
		}
	};

	public static ContextLocal get()
	{
		
		ContextLocal  ctxBuffer =  threadLocal.get();
		return ctxBuffer;
//		//TimeLog.key5Start();
//		ContextLocal ctxBuffer = re.get();
//		//TimeLog.key5End();
//		if (ctxBuffer == null)
//		{
//			
//			ctxBuffer = new ContextLocal();
//			threadLocal.set(new SoftReference(ctxBuffer));
//			
//		}
//	
//		return ctxBuffer;
	}

	public char[] getCharBuffer()
	{
		return this.charBuffer;
	}
	
	public  SimpleDateFormat getDateFormat(String pattern)
	{
		
		SimpleDateFormat format = formats.get(pattern);
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
			formats.put(pattern, format);
		}
		return format;
	}
	
	
	
	public StringBuilder getWriterBuffer()
	{
		buffer.setLength(0);
		return this.buffer;
	}
	


	public boolean[] getScope() {
		return scope;
	}

	public void setScope(boolean[] scope) {
		this.scope = scope;
	}

	/** 得到一个期望长度的buffer
	 * @param expected
	 * @return
	 */
	public char[] getCharBuffer(int expected)
	{
		if (this.charBuffer.length >= expected)
		{
			return charBuffer;
		}
		else
		{
			//?预先设置多一点
			this.charBuffer = new char[(int) (expected * 1.2)];
		}
		return this.charBuffer;
	}

	
//	public char[] getWriterBuffer(int expected)
//	{
//		if (this.writeBuffer.length >= expected)
//		{
//			return writeBuffer;
//		}
//		else
//		{
//			//?预先设置多一点
//			this.writeBuffer = new char[(int) (expected * 2)];
//		}
//		return this.writeBuffer;
//	}


}

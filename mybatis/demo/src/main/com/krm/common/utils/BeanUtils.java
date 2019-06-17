
package com.krm.common.utils;

import java.lang.reflect.Method;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.krm.common.base.CommonEntity;

public abstract class BeanUtils {
	private static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);

	public static final <T> Object getProperty(T bean, String name) {
		Assert.notNull((Object) bean, "bean不能为空！");
		Assert.notNull((Object) name, "name不能为空！");
		Object value = null;
		try {
			value = PropertyUtils.getProperty(bean, name);
		} catch (Exception e) {
			
		}
		return value;
	}
	
	
	public static final <T> T setProperty(T bean, String name, Object value, boolean isRequired) {
		Assert.notNull((Object) bean, "bean不能为空！");
		Assert.notNull((Object) name, "name不能为空！");
		try {
			PropertyUtils.setProperty(bean, name, value);
		} catch (Exception e) {
			if (isRequired) {
				String message = bean.getClass().getName() + "不能设置属性" + name + "的值为" + value;
				logger.warn(message, e);
				throw new IllegalArgumentException(message, e);
			}
		}
		return bean;
	}
	
	
	/**
	 * 
	 * @description 转换javabean ,将scr中的属性值赋值给des，如果class1属性有值，则不覆盖
	 *              ，前提条件是有相同的属性名
	 * @param des
	 *            基准类,被赋值对象
	 * @param scr
	 *            提供数据的对象
	 * @throws Exception
	 * @see
	 */
	public static void copyProperties(Object des, Object scr) {
		try {
			Class<?> clazz1 = des.getClass();
			Class<?> clazz2 = scr.getClass();
			// 得到method方法
			Method[] method1 = clazz1.getMethods();
			Method[] method2 = clazz2.getMethods();

			int length1 = method1.length;
			int length2 = method2.length;
			if (length1 != 0 && length2 != 0) {
				// 创建一个get方法数组，专门存放class2的get方法。
				Method[] get = new Method[length2];
				for (int i = 0, j = 0; i < length2; i++) {
					if (method2[i].getName().indexOf("get") == 0) {
						get[j] = method2[i];
						++j;
					}
				}
				for (int i = 0; i < get.length; i++) {
					if (get[i] == null)// 数组初始化的长度多于get方法，所以数组后面的部分是null
						continue;
					// 得到get方法的值，判断时候为null，如果为null则进行下一个循环
					Object value = get[i].invoke(scr, new Object[] {});
					if (null == value)
						continue;
					// 得到get方法的名称 例如：getXxxx
					String getName = get[i].getName();
					// 得到set方法的时候传入的参数类型，就是get方法的返回类型
					Class<?> paramType = get[i].getReturnType();
					Method getMethod = null;
					try {
						// 判断在class1中时候有class2中的get方法，如果没有则抛异常继续循环
						getMethod = clazz1.getMethod(getName, new Class[] {});
					} catch (NoSuchMethodException e) {
						continue;
					}
					// class1的get方法不为空并且class1中get方法得到的值为空，进行赋值，如果class1属性原来有值，则跳过
					if (null == getMethod || null != getMethod.invoke(des, new Object[] {}))
						continue;
					// 通过getName 例如getXxxx 截取后得到Xxxx，然后在前面加上set，就组装成set的方法名
					String setName = "set" + getName.substring(3);
					// 得到class1的set方法，并调用
					Method setMethod = clazz1.getMethod(setName, paramType);
					setMethod.invoke(des, value);
				}
			}
		} catch (Exception e) {
			logger.info(e.toString());
		}
	}
	
	
	public static CommonEntity beanToCommon(Object scr) {
		try {
			Class<?> clazz = scr.getClass();
			CommonEntity result = new CommonEntity();
			// 得到method方法
			Method[] method2 = clazz.getMethods();
			
			int length2 = method2.length;
			if (length2 != 0) {
				Method[] get = new Method[length2];
				for (int i = 0, j = 0; i < length2; i++) {
					if (method2[i].getName().indexOf("get") == 0) {
						get[j] = method2[i];
						++j;
					}
				}
				for (int i = 0; i < get.length; i++) {
					if (get[i] == null)// 数组初始化的长度多于get方法，所以数组后面的部分是null
						continue;
					// 得到get方法的值，判断时候为null，如果为null则进行下一个循环
					Object value = get[i].invoke(scr, new Object[] {});
					if (null == value)
						continue;
					// 得到get方法的名称 例如：getXxxx
					String getName = get[i].getName();
					result.put(StringUtil.firstCharToLower(getName.substring(3)), value);
				}
				return result;
			}
		} catch (Exception e) {
			logger.info(e.toString());
		}
		return null;
	}
}
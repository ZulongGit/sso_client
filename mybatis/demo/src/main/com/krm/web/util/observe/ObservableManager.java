
package com.krm.web.util.observe;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class ObservableManager {
	private final ConcurrentMap<Class<?>, GenericObservable> obsMap;
	private static Logger logger = LoggerFactory.getLogger(ObservableManager.class);

	private static final ObservableManager DEFAULT = new ObservableManager();

	public ObservableManager() {
		this.obsMap = new ConcurrentHashMap();
	}

	public static ObservableManager get() {
		return DEFAULT;
	}

	public <E extends ObservableTag> GenericObservable getObservable(Class<E> clazz) {
		Assert.notNull(clazz, "被观察者宿主类不能为空。");
		GenericObservable observable = (GenericObservable) this.obsMap.get(clazz);
		if (null != observable) {
			return observable;
		}
		synchronized (this.obsMap) {
			observable = (GenericObservable) this.obsMap.get(clazz);
			if (null != observable)
				return observable;
			try {
				Method method = clazz.getMethod("getObservable", new Class[0]);
				observable = (GenericObservable) method.invoke(null, new Object[0]);
				this.obsMap.put(clazz, observable);
			} catch (Exception e) {
				String message = "请检查类结构" + clazz.getName()
						+ "，ObservableTag类中应该包含公共静态方法： public static GenericObservable " + "getObservable" + "()。";

				logger.error(message, e);
				throw new IllegalArgumentException(message, e);
			}
		}
		return observable;
	}
}
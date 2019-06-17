
package com.krm.web.util.observe;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class GenericObservable extends Observable {
	private boolean changed = false;
	private final String name;
	private final Vector<Observer> obs = new Vector();

	private static Logger logger = LoggerFactory.getLogger(GenericObservable.class);

	public GenericObservable() {
		this.name = super.getClass().getName();
	}

	public GenericObservable(String name) {
		Assert.notNull(name, "name不能为空。");
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public synchronized void addObserver(Observer o) {
		if (o == null) {
			throw new NullPointerException("观察都不能为空。");
		}
		if (!(this.obs.contains(o))) {
			this.obs.addElement(o);
			logger.info("{}成功注册观察者：{}。", getName(), o.getClass());
		} else {
			logger.warn("{}重复注册观察者：{}，直接忽略。", getName(), o.getClass());
		}
	}

	public synchronized void deleteObserver(Observer o) {
		if (o == null) {
			throw new NullPointerException("观察都不能为空。");
		}
		if (this.obs.removeElement(o))
			logger.info("{}成功注销观察者：{}。", getName(), o.getClass());
		else
			logger.warn("{}重复注销观察者：{}，直接忽略。", getName(), o.getClass());
	}

	public void notifyObservers() {
		notifyObservers(null);
	}

	public void notifyObservers(Object arg) {
		Observer[] arrLocal;
		synchronized (this) {
			if (!(this.changed)) {
				logger.debug("{}被观察者状态未变更或已处理，忽略对观察者的通知。", getName());
				return;
			}
			arrLocal = (Observer[]) this.obs.toArray(new Observer[this.obs.size()]);
			clearChanged();
		}

		for (int i = arrLocal.length - 1; i >= 0; --i) {
			Observer o = arrLocal[i];
			try {
				o.update(this, arg);
			} catch (Exception e) {
				if (o instanceof InterruptObserver) {
					return;
				}
				if (logger.isWarnEnabled())
					logger.warn("观察者" + o.getClass() + "执行异常", e);
			}
		}
	}

	public synchronized void deleteObservers() {
		this.obs.removeAllElements();
		logger.info("{}成功注销所有观察者。", getName());
	}

	public synchronized void setChanged() {
		this.changed = true;
	}

	protected synchronized void clearChanged() {
		this.changed = false;
	}

	public synchronized boolean hasChanged() {
		return this.changed;
	}

	public synchronized int countObservers() {
		return this.obs.size();
	}
}
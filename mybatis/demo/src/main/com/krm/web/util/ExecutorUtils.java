
package com.krm.web.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

public final class ExecutorUtils {
	private static final ScheduledExecutorService systemDefualtScheduledExecutorService = createScheduledExecutorService();

	public static ScheduledExecutorService getSystemScheduledExecutorService() {
		return systemDefualtScheduledExecutorService;
	}

	public static ScheduledExecutorService createScheduledExecutorService() {
		return Executors.newSingleThreadScheduledExecutor(new DaemonThreadFactory());
	}

	public static class DaemonThreadFactory implements ThreadFactory {
		public Thread newThread(Runnable r) {
			Thread thread = new Thread(r);
			thread.setDaemon(true);
			return thread;
		}
	}
}
package com.clever.common.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtils {
	//创建一个可缓存、可变尺寸的线程池
	private static final ExecutorService THREAD_POOL = Executors.newCachedThreadPool();
	
	public static ExecutorService getCachedThreadPool() {
		return THREAD_POOL;
	}
}

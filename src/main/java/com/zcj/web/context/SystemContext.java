package com.zcj.web.context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 分页信息、线程池
 * 
 * @author ZCJ
 * @data 2013-5-9
 */
public class SystemContext {
	
	private static final int DEFAULT_OFFSET = 0;
	private static final int DEFAULT_PAGESIZE = 15;
	
	private static ThreadLocal<Integer> offset = new ThreadLocal<Integer>();// 分页偏移量
	private static ThreadLocal<Integer> pagesize = new ThreadLocal<Integer>();// 分页每页条数

	private static final ExecutorService executorService = Executors.newCachedThreadPool();// 线程池
	
	public static ExecutorService getExecutorService() {
		return executorService;
	}
	
	public static void setOffset(int _offset) {
		offset.set(_offset);
	}

	public static int getOffset() {
		Integer _offset = offset.get();
		if (_offset == null) {
			return DEFAULT_OFFSET;
		}
		return _offset;
	}

	public static void removeOffset() {
		offset.remove();
	}
	
	public static int getDefaultOffset() {
		return DEFAULT_OFFSET;
	}
	

	public static void setPagesize(int _pagesize) {
		pagesize.set(_pagesize);
	}

	public static int getPagesize() {
		Integer _pagesize = pagesize.get();
		if (_pagesize == null) {
			return DEFAULT_PAGESIZE;
		}
		return _pagesize;
	}

	public static void removePagesize() {
		pagesize.remove();
	}
	
	public static int getDefaultPagesize() {
		return DEFAULT_PAGESIZE;
	}

}

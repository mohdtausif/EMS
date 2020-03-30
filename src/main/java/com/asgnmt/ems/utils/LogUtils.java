package com.asgnmt.ems.utils;

import org.slf4j.Logger;

public class LogUtils {
	public static final String DEBUG_PREFIX="EMS::"; 
	public static void debug(Logger log, String msg) {
		log.debug(DEBUG_PREFIX+msg);
	}
	public static void error(Logger log, String msg) {
		log.error(DEBUG_PREFIX+msg);
	}
	public static void error(Logger log, String msg, Throwable e) {
		log.error(DEBUG_PREFIX+msg, e);
	}

}

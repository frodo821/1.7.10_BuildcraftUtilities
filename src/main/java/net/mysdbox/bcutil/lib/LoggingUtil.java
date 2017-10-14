package net.mysdbox.bcutil.lib;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class LoggingUtil {
	static Logger logger;
	
	public static void loggerInit(FMLPreInitializationEvent e){
		logger = e.getModLog();
	}
	
	public static void log(String log){
		LoggingUtil.log(log, Level.INFO);
	}
	
	public static void log(String log, Level l){
		LoggingUtil.log(log, l, new Object() {});
	}
	
	public static void log(String log, Level l, Object... params){
		logger.log(l, log, params);
	}
	public static void log(Object log){
		LoggingUtil.log(log, Level.INFO);
	}
	
	public static void log(Object log, Level l){
		logger.log(l, log);
	}
}

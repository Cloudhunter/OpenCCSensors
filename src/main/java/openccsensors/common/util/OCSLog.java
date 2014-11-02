package openccsensors.common.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.FMLLog;

public class OCSLog {

	private static Logger logger;

	public static void info(String format, Object... data) {
		log(Level.INFO, format, data);
	}

	public static void init() {
		logger = FMLLog.getLogger();
	}

	public static void log(Level level, String format, Object... data) {
		logger.log(level, String.format(format, data));
	}

	public static void severe(String format, Object... data) {
		log(Level.ERROR, format, data);
	}

	public static void warn(String format, Object... data) {
		log(Level.WARN, format, data);
	}

}
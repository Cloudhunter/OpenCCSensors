package openccsensors.common.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import cpw.mods.fml.common.FMLLog;

public class OCSLog {

	private static Logger logger;

	public static void info(String format, Object... data) {
		log(Level.INFO, format, data);
	}

	public static void init() {
		logger = Logger.getLogger("OpenCCSensors");
		logger.setParent(FMLLog.getLogger());
	}

	public static void log(Level level, String format, Object... data) {
		logger.log(level, String.format(format, data));
	}

	public static void severe(String format, Object... data) {
		log(Level.SEVERE, format, data);
	}

	public static void warn(String format, Object... data) {
		log(Level.WARNING, format, data);
	}

}
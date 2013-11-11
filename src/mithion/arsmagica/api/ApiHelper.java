package mithion.arsmagica.api;

import java.lang.reflect.Method;

import cpw.mods.fml.common.FMLLog;

class ApiHelper {
	public static Method initializeStaticMethodLookup(String className, String methodName, Class...parameters){
		Method located = null;
		try {
			Class c = Class.forName(className);
			located = c.getMethod(methodName, parameters);
		} catch (NoSuchMethodException e) {
			FMLLog.warning("Ars Magica API >> No such method as " + className + "." + methodName);
		} catch (SecurityException e) {
			FMLLog.warning("Ars Magica API >> Access denied when trying to resolve " + className + "." + methodName);
		} catch (ClassNotFoundException e) {
			FMLLog.warning("Ars Magica API >> No such class as " + className);
		}
		return located;
	}
}

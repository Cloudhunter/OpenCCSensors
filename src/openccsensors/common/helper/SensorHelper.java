package openccsensors.common.helper;

import cpw.mods.fml.common.registry.LanguageRegistry;


public class SensorHelper
{
	
	public static String getType ( String rawType )
	{
		String translated = LanguageRegistry.instance().getStringLocalization( rawType, "en_OCS" );
		return translated == "" ? rawType : translated;
	}
	
	
}

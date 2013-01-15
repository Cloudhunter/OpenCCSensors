package openccsensors.common.helper;

import cpw.mods.fml.common.registry.LanguageRegistry;


public class SensorHelper
{
	
	public static String getType ( String rawType )
	{
		String translated = LanguageRegistry.instance().getStringLocalization( rawType, "en_OCS" );
		return translated == "" ? rawType : translated;
	}
	
	public static int getSensorInterfaceId(int dmgValue)
	{
		return (int)(16 + (Math.floor( ( dmgValue - 16) /  64 ) * 64) + (dmgValue % 16));
	}
	
	public static int getMark(int dmgValue)
	{
		return 1 + (int)((Math.floor((dmgValue-16) / 16) + 16) % 4);
	}
	
}

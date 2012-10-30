package openccsensors;

import openccsensors.common.CommonProxy;
import openccsensors.common.core.OCSLog;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;

@Mod( modid = "OpenCCSensors", name = "OpenCCSensors", version = "0.01", dependencies = "after:ComputerCraft" )
public class OpenCCSensors 
{
	@Instance( value = "OpenCCSensors" )
	public static OpenCCSensors instance;
	
	@SidedProxy( clientSide = "openccsensors.client.ClientProxy", serverSide = "openccsensors.client.CommonProxy" )
	public static CommonProxy proxy;

	@Mod.Init
	public void init( FMLInitializationEvent evt )
	{
		// init logger
		OCSLog.init();
		
		// we are starting!
		OCSLog.info( "OpenCCSensors version %s starting", FMLCommonHandler.instance().findContainerFor(instance).getVersion() );
		
		// init our proxy
		proxy.init();
	}
	
}

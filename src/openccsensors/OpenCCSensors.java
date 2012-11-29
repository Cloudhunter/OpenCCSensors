package openccsensors;

import java.util.ArrayList;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import openccsensors.common.CommonProxy;
import openccsensors.common.core.OCSLog;
import openccsensors.common.sensorperipheral.BlockSensor;
import openccsensors.common.sensors.SensorCard;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import net.minecraft.src.Item;

@Mod( modid = "OCS", name = "OpenCCSensors", version = "0.03", dependencies = "required-after:ComputerCraft;after:CCTurtle")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class OpenCCSensors 
{
	@Instance( value = "OCS" )
	public static OpenCCSensors instance;
	
	@SidedProxy( clientSide = "openccsensors.client.ClientProxy", serverSide = "openccsensors.common.CommonProxy" )
	public static CommonProxy proxy;
	
	// config class to keep all the config data in
	public static class Config
	{
		public static int sensorBlockID;
		public static int sensorBlockRenderID;
		public static boolean turtlePeripheralEnabled;
	}
	
	// blocks class to keep all the blocks in
	public static class Blocks
	{
		public static BlockSensor sensorBlock;
	}

	public static Item sensorCard;
	
	@Mod.PreInit
	public void preInit( FMLPreInitializationEvent evt )
	{
		Configuration configFile = new Configuration(evt.getSuggestedConfigurationFile());
		
		Property prop = configFile.getBlock("sensorBlockID", 186);
		prop.comment = "The block ID for the sensor block";
		Config.sensorBlockID = prop.getInt();
		
		prop = configFile.getBlock("sensorBlockRenderID", 84);
		prop.comment = "The render ID for the sensor block";
		Config.sensorBlockRenderID = prop.getInt();
		
		prop = configFile.get("general", "turtlePeripheralEnabled", true);
		prop.comment = "Turtle Peripheral Enabled";
		Config.turtlePeripheralEnabled = prop.getBoolean(true);
		
		configFile.save();
	}

	@Mod.Init
	public void init( FMLInitializationEvent evt )
	{
		// init logger
		OCSLog.init();
		
		// we are starting!
		OCSLog.info( "OpenCCSensors version %s starting", FMLCommonHandler.instance().findContainerFor(instance).getVersion() );
		
		// init our proxy
		proxy.init();
		
		loadSensorPack("buildcraft");
		loadSensorPack("vanilla");
		loadSensorPack("industrialcraft");

		sensorCard = new SensorCard(12345);
		
		proxy.registerRenderInformation();
	}
	
	private static void loadSensorPack(String sensor)
	{
		try
		{
			Class c = OpenCCSensors.class.getClassLoader().loadClass("openccsensors.common.sensors." + sensor + ".SensorPack");
			c.getMethod("init", new Class[0]).invoke(null, new Object[0]);
		}
		catch (Throwable throwable)
		{
			OCSLog.info(sensor + " sensor not loaded");
			return;
		}
		OCSLog.info(sensor + " sensor loaded");
	}
}

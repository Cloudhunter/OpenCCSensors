package openccsensors;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import openccsensors.common.CommonProxy;
import openccsensors.common.core.OCSLog;
import openccsensors.common.sensorperipheral.BlockSensor;
import openccsensors.common.sensors.buildcraft.BuildCraftSensorCard;
import openccsensors.common.sensors.industrialcraft.IC2SensorCard;
import openccsensors.common.sensors.vanilla.InventorySensorCard;
import openccsensors.common.sensors.vanilla.ProximitySensorCard;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod( modid = "OCS", name = "OpenCCSensors", version = "0.01", dependencies = "after:ComputerCraft" )
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
		public static boolean turtlePeripheralEnabled;
	}
	
	// blocks class to keep all the blocks in
	public static class Blocks
	{
		public static BlockSensor sensorBlock;
	}
	
	// items class to keep all the items in
	public static class Items
	{
		public static InventorySensorCard inventorySensor;
		public static ProximitySensorCard proximitySensor;
		public static IC2SensorCard ic2Sensor;
		public static BuildCraftSensorCard buildcraftSensor;
	}
	
	@Mod.PreInit
	public void preInit( FMLPreInitializationEvent evt )
	{
		Configuration configFile = new Configuration(evt.getSuggestedConfigurationFile());
		
		Property prop = configFile.getBlock("sensorBlockID", 186);
		prop.comment = "The block ID for the sensor block";
		Config.sensorBlockID = prop.getInt();
		
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
	}
	
}

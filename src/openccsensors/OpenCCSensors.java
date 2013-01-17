package openccsensors;

import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import openccsensors.common.CommonProxy;
import openccsensors.common.blocks.BlockGauge;
import openccsensors.common.blocks.BlockSensor;
import openccsensors.common.core.OCSLog;
import openccsensors.common.items.ItemSensorCard;
import openccsensors.common.items.ItemGeneric;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod( modid = "OCS", name = "OpenCCSensors", version = "0.1.1", dependencies = "required-after:ComputerCraft;after:CCTurtle")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class OpenCCSensors 
{
	public static class Blocks
	{
		public static BlockSensor sensorBlock;
		public static BlockGauge gaugeBlock;
	}
	
	public static class Config
	{
		public static int sensorBlockID;
		public static int gaugeBlockID;
		public static int sensorBlockRenderID;
		public static int sensorCardID;
		public static int genericItemID;
		public static boolean turtlePeripheralEnabled;
	}
	
	public static class Items
	{
		public static ItemGeneric genericItem;
		public static ItemSensorCard sensorCard;
	}
	
	@Instance( value = "OCS" )
	public static OpenCCSensors instance;
	
	@SidedProxy( clientSide = "openccsensors.client.ClientProxy", serverSide = "openccsensors.common.CommonProxy" )
	public static CommonProxy proxy;


	@Mod.Init
	public void init( FMLInitializationEvent evt )
	{
		OCSLog.init();
		
		OCSLog.info( "OpenCCSensors version %s starting", FMLCommonHandler.instance().findContainerFor(instance).getVersion() );
		
		proxy.init();
		
		proxy.registerRenderInformation();
	}
	
	@Mod.PreInit
	public void preInit( FMLPreInitializationEvent evt )
	{
		Configuration configFile = new Configuration(evt.getSuggestedConfigurationFile());
		
		Property prop = configFile.getBlock("sensorBlockID", 186);
		prop.comment = "The block ID for the sensor block";
		Config.sensorBlockID = prop.getInt();
		
		prop = configFile.getBlock("gaugeBlockID", 187);
		prop.comment = "The block ID for the gauge block";
		Config.gaugeBlockID = prop.getInt();
		
		prop = configFile.getBlock("sensorBlockRenderID", 84);
		prop.comment = "The render ID for the sensor block";
		Config.sensorBlockRenderID = prop.getInt();
		
		prop = configFile.get("general", "turtlePeripheralEnabled", true);
		prop.comment = "Turtle Peripheral Enabled";
		Config.turtlePeripheralEnabled = prop.getBoolean(true);

		prop = configFile.getItem("sensorCardID", 7486);
		prop.comment = "The block ID for the sensor card";
		Config.sensorCardID = prop.getInt();
		
		prop = configFile.getItem("sensorUpgradeID", 7487);
		prop.comment = "The block ID for the generic item";
		Config.genericItemID = prop.getInt();
		
		configFile.save();
	}
}

package openccsensors;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import openccsensors.api.IItemMeta;
import openccsensors.common.CommonProxy;
import openccsensors.common.SensorTier;
import openccsensors.common.block.BlockBasicSensor;
import openccsensors.common.block.BlockGauge;
import openccsensors.common.block.BlockSensor;
import openccsensors.common.item.ItemGeneric;
import openccsensors.common.item.ItemSensorCard;
import openccsensors.common.sensor.DroppedItemSensor;
import openccsensors.common.sensor.InventorySensor;
import openccsensors.common.sensor.MachineSensor;
import openccsensors.common.sensor.MagicSensor;
import openccsensors.common.sensor.MinecartSensor;
import openccsensors.common.sensor.PowerSensor;
import openccsensors.common.sensor.ProximitySensor;
import openccsensors.common.sensor.SignSensor;
import openccsensors.common.sensor.SonicSensor;
import openccsensors.common.sensor.TankSensor;
import openccsensors.common.sensor.WorldSensor;
import openccsensors.common.util.OCSLog;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

@Mod( modid = "OCS", name = "OpenCCSensors", version = "0.1.5", dependencies = "required-after:ComputerCraft;after:CCTurtle;after:BuildCraft|Core;after:IC2;after:Thaumcraft;after:AppliedEnergistics")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class OpenCCSensors {

	public static class Blocks
	{
		public static BlockSensor sensorBlock;
		public static BlockGauge gaugeBlock;
		public static BlockBasicSensor basicSensorBlock;
	}
	
	public static class Config
	{
		public static int sensorBlockID;
		public static int basicSensorBlockID;
		public static int gaugeBlockID;
		public static int sensorCardID;
		public static int genericItemID;
		public static boolean turtlePeripheralEnabled;
	}
	
	public static class Tiers {
		public static SensorTier tier1;
		public static SensorTier tier2;
		public static SensorTier tier3;
		public static SensorTier tier4;
	}
	

	public static class Items
	{
		public static ItemGeneric genericItem;
		public static ItemSensorCard sensorCard;
		
		public static IItemMeta rangeExtensionAntenna;
		public static IItemMeta signalAmplifier;
		public static IItemMeta advancedAmplifier;
	}

	public static int renderId;
	
	public static class Sensors {
		
		public static ProximitySensor proximitySensor;
		public static MinecartSensor minecartSensor;
		public static DroppedItemSensor droppedItemSensor;
		public static InventorySensor inventorySensor;
		public static SignSensor signSensor;
		public static SonicSensor sonicSensor;
		public static TankSensor tankSensor;
		public static WorldSensor worldSensor;
		public static MagicSensor magicSensor;
		public static MachineSensor machineSensor;
		public static PowerSensor powerSensor;
		
	}
	
	public static String RESOURCE_PATH;
	public static String LANGUAGE_PATH;
	public static String LUA_PATH;
	public static String TEXTURE_PATH;

	public static CreativeTabs tabOpenCCSensors = new CreativeTabs("tabOpenCCSensors") {
                public ItemStack getIconItemStack() {
                        return new ItemStack(Blocks.sensorBlock, 1, 0);
                }
  };

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

		RESOURCE_PATH = "/mods/openccsensors";
		LUA_PATH = String.format("%s/lua", RESOURCE_PATH);
		LANGUAGE_PATH = String.format("%s/languages", RESOURCE_PATH);
		TEXTURE_PATH = String.format("%s/textures", RESOURCE_PATH);
		
		Configuration configFile = new Configuration(evt.getSuggestedConfigurationFile());

		Property prop = configFile.getBlock("sensorBlockID", 341);
		prop.comment = "The block ID for the sensor block";
		Config.sensorBlockID = prop.getInt();

		prop = configFile.getBlock("gaugeBlockID", 342);
		prop.comment = "The block ID for the gauge block";
		Config.gaugeBlockID = prop.getInt();
		
		prop = configFile.getBlock("basicSensorBlockID", 343);
		prop.comment = "The block ID for the basic sensor block";
		Config.basicSensorBlockID = prop.getInt();

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
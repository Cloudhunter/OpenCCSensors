package openccsensors.common;

import java.io.File;
import java.io.IOException;

import openccsensors.OpenCCSensors;
import openccsensors.common.block.BlockBasicSensor;
import openccsensors.common.block.BlockGauge;
import openccsensors.common.block.BlockSensor;
import openccsensors.common.item.ItemGeneric;
import openccsensors.common.item.ItemSensorCard;
import openccsensors.common.item.meta.ItemMetaAdvancedAmplifier;
import openccsensors.common.item.meta.ItemMetaRangeExtensionAntenna;
import openccsensors.common.item.meta.ItemMetaSignalAmplifier;
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
import openccsensors.common.tileentity.TileEntityGauge;
import openccsensors.common.turtle.TurtleUpgradeSensor;
import openccsensors.common.util.LanguageUtils;
import openccsensors.common.util.RecipeUtils;
import openccsensors.common.util.ResourceExtractingUtils;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.network.NetworkRegistry;
import dan200.turtle.api.TurtleAPI;

public class CommonProxy {
	
	public void init() {

		initSensors();
		initBlocks();
		initItems();
			
		if (OpenCCSensors.Config.turtlePeripheralEnabled) {
			TurtleAPI.registerUpgrade(new TurtleUpgradeSensor());
		}
		
		NetworkRegistry.instance().registerGuiHandler(OpenCCSensors.instance, new GuiHandler());

		TileEntityGauge.addGaugeSensor(OpenCCSensors.Sensors.machineSensor);
		TileEntityGauge.addGaugeSensor(OpenCCSensors.Sensors.powerSensor);
		TileEntityGauge.addGaugeSensor(OpenCCSensors.Sensors.inventorySensor);
		
		RecipeUtils.addGaugeRecipe();
		RecipeUtils.addSensorRecipe();
		
		setupLuaFiles();
		setupLanguages();
	}
	
	private void initSensors() {
		OpenCCSensors.Sensors.proximitySensor = new ProximitySensor();
		OpenCCSensors.Sensors.droppedItemSensor = new DroppedItemSensor();
		OpenCCSensors.Sensors.signSensor = new SignSensor();
		OpenCCSensors.Sensors.minecartSensor = new MinecartSensor();
		OpenCCSensors.Sensors.sonicSensor = new SonicSensor();
		OpenCCSensors.Sensors.tankSensor = new TankSensor();
		OpenCCSensors.Sensors.inventorySensor = new InventorySensor();
		OpenCCSensors.Sensors.worldSensor = new WorldSensor();
		OpenCCSensors.Sensors.powerSensor = new PowerSensor();
		OpenCCSensors.Sensors.machineSensor = new MachineSensor();
		OpenCCSensors.Sensors.magicSensor = new MagicSensor();
	}
	
	private void initBlocks() {
		OpenCCSensors.Blocks.sensorBlock = new BlockSensor();
		OpenCCSensors.Blocks.gaugeBlock = new BlockGauge();
		OpenCCSensors.Blocks.basicSensorBlock = new BlockBasicSensor();
	}
	
	private void initItems() {
		
		// metas
		OpenCCSensors.Items.genericItem = new ItemGeneric();
		OpenCCSensors.Items.rangeExtensionAntenna = new ItemMetaRangeExtensionAntenna(1);
		OpenCCSensors.Items.signalAmplifier = new ItemMetaSignalAmplifier(2);
		OpenCCSensors.Items.advancedAmplifier = new ItemMetaAdvancedAmplifier(3);
		
		OpenCCSensors.Items.sensorCard = new ItemSensorCard();
		OpenCCSensors.Items.sensorCard.registerSensors();
		
	}
	
	
	public void registerRenderInformation() {

	}

	public File getBase() {
		return FMLCommonHandler.instance().getMinecraftServerInstance().getFile(".");
	}

	private void setupLuaFiles() {
		ModContainer container = FMLCommonHandler.instance().findContainerFor(OpenCCSensors.instance);
		File modFile = container.getSource();
		File baseFile = getBase();
		String destFolder = String.format("mods\\OCSLua\\%s\\lua", container.getVersion());
		if (modFile.isDirectory()) {
			File srcFile = new File(modFile, OpenCCSensors.LUA_PATH);
			File destFile = new File(baseFile, destFolder);
			try {
				ResourceExtractingUtils.copy(srcFile, destFile);
			} catch (IOException e) {
			}
		} else {
			ResourceExtractingUtils.extractZipToLocation(modFile, OpenCCSensors.LUA_PATH, destFolder);
		}

	}
	
	private void setupLanguages() {
		LanguageUtils.setupLanguages();
	}
}
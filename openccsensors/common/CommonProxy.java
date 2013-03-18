package openccsensors.common;

import java.util.Map.Entry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import dan200.turtle.api.TurtleAPI;
import openccsensors.OpenCCSensors;
import openccsensors.api.EnumItemRarity;
import openccsensors.api.ISensor;
import openccsensors.api.SensorCard;
import openccsensors.common.block.BlockGauge;
import openccsensors.common.block.BlockSensor;
import openccsensors.common.item.ItemGeneric;
import openccsensors.common.item.ItemSensorCard;
import openccsensors.common.item.meta.ItemMetaAdvancedAmplifier;
import openccsensors.common.item.meta.ItemMetaRangeExtensionAntenna;
import openccsensors.common.item.meta.ItemMetaSignalAmplifier;
import openccsensors.common.sensor.PowerSensor;
import openccsensors.common.sensor.DroppedItemSensor;
import openccsensors.common.sensor.MachineSensor;
import openccsensors.common.sensor.InventorySensor;
import openccsensors.common.sensor.MinecartSensor;
import openccsensors.common.sensor.ProximitySensor;
import openccsensors.common.sensor.SignSensor;
import openccsensors.common.sensor.SonicSensor;
import openccsensors.common.sensor.TankSensor;
import openccsensors.common.sensor.MagicSensor;
import openccsensors.common.sensor.WorldSensor;
import openccsensors.common.tileentity.TileEntityGauge;
import openccsensors.common.tileentity.TileEntitySensor;
import openccsensors.common.turtle.TurtleUpgradeSensor;

public class CommonProxy {
	
	public void init() {

		initSensors();
		initBlocks();
		initItems();
			
		if (OpenCCSensors.Config.turtlePeripheralEnabled) {
			TurtleUpgradeSensor turtleSensor = new TurtleUpgradeSensor();
			OpenCCSensors.Items.sensorCard.addIconsForLoading(turtleSensor);
			TurtleAPI.registerUpgrade(turtleSensor);
		}
		
		NetworkRegistry.instance().registerGuiHandler(OpenCCSensors.instance, new GuiHandler());
		
		TileEntityGauge.addGaugeSensor(OpenCCSensors.Sensors.powerSensor);
		
		// gauge.registerSensor(sensor);
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
}
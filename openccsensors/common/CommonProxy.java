package openccsensors.common;

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
import openccsensors.common.block.BlockSensor;
import openccsensors.common.item.ItemSensorCard;
import openccsensors.common.sensor.AppliedEnergisticsSensor;
import openccsensors.common.sensor.BuildcraftSensor;
import openccsensors.common.sensor.DroppedItemSensor;
import openccsensors.common.sensor.IndustrialcraftSensor;
import openccsensors.common.sensor.InventorySensor;
import openccsensors.common.sensor.MinecartSensor;
import openccsensors.common.sensor.ProximitySensor;
import openccsensors.common.sensor.SignSensor;
import openccsensors.common.sensor.SonicSensor;
import openccsensors.common.sensor.TankSensor;
import openccsensors.common.sensor.ThaumcraftSensor;
import openccsensors.common.sensor.WorldSensor;
import openccsensors.common.tileentity.TileEntitySensor;
import openccsensors.common.turtle.TurtleUpgradeSensor;

public class CommonProxy {
	
	public void init() {

		OpenCCSensors.Sensors.proximitySensor = new ProximitySensor();
		OpenCCSensors.Sensors.droppedItemSensor = new DroppedItemSensor();
		OpenCCSensors.Sensors.signSensor = new SignSensor();
		OpenCCSensors.Sensors.minecartSensor = new MinecartSensor();
		OpenCCSensors.Sensors.sonicSensor = new SonicSensor();
		OpenCCSensors.Sensors.tankSensor = new TankSensor();
		OpenCCSensors.Sensors.inventorySensor = new InventorySensor();
		OpenCCSensors.Sensors.worldSensor = new WorldSensor();
		
		OpenCCSensors.Sensors.appliedEnergisticsSensor = new AppliedEnergisticsSensor();
		OpenCCSensors.Sensors.buildcraftSensor = new BuildcraftSensor();
		OpenCCSensors.Sensors.industrialcraftSensor = new IndustrialcraftSensor();
		OpenCCSensors.Sensors.thaumcraftSensor = new ThaumcraftSensor();
		
		NetworkRegistry.instance().registerGuiHandler(OpenCCSensors.instance, new GuiHandler());
		
		OpenCCSensors.Blocks.sensorBlock = new BlockSensor();
		
		ItemSensorCard sensorCard = new ItemSensorCard();

		if (OpenCCSensors.Config.turtlePeripheralEnabled) {
			TurtleUpgradeSensor turtleSensor = new TurtleUpgradeSensor();
			sensorCard.addIconsForLoading(turtleSensor);
			TurtleAPI.registerUpgrade(turtleSensor);
		}

		
		// gauge.registerSensor(sensor);
	}
	
	
	public void registerRenderInformation() {

	}
}
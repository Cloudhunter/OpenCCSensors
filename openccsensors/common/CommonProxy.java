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
import openccsensors.common.sensor.ProximitySensor;
import openccsensors.common.tileentity.TileEntitySensor;
import openccsensors.common.turtle.TurtleUpgradeSensor;

public class CommonProxy {
	
	public void init() {

		NetworkRegistry.instance().registerGuiHandler(OpenCCSensors.instance, new GuiHandler());
		
		OpenCCSensors.Blocks.sensorBlock = new BlockSensor();
		
		ItemSensorCard sensorCard = new ItemSensorCard();

		if (OpenCCSensors.Config.turtlePeripheralEnabled) {
			TurtleUpgradeSensor turtleSensor = new TurtleUpgradeSensor();
			sensorCard.addIconsForLoading(turtleSensor);
			TurtleAPI.registerUpgrade(turtleSensor);
		}
		
		SensorTier tier1 = new SensorTier("Mk. I", EnumItemRarity.COMMON, 2, "OpenCCSensors:tier1");
		SensorTier tier2 = new SensorTier("Mk. II", EnumItemRarity.UNCOMMON, 4, "OpenCCSensors:tier2");
		SensorTier tier3 = new SensorTier("Mk. III", EnumItemRarity.RARE, 6, "OpenCCSensors:tier3");
		SensorTier tier4 = new SensorTier("Mk. IV", EnumItemRarity.EPIC, 8, "OpenCCSensors:tier4");
		
		sensorCard.addIconsForLoading(tier1, tier2, tier3, tier4);
		
		ProximitySensor sensor = new ProximitySensor();
		sensorCard.addSensorCard(1, new SensorCard(sensor, tier1));
		sensorCard.addSensorCard(2, new SensorCard(sensor, tier2));
		sensorCard.addSensorCard(3, new SensorCard(sensor, tier3));
		sensorCard.addSensorCard(4, new SensorCard(sensor, tier4));
		sensorCard.addIconsForLoading(sensor);
		
		// gauge.registerSensor(sensor);
	}
	
	
	public void registerRenderInformation() {

	}
}
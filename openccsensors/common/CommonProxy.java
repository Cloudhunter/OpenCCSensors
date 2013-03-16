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
import openccsensors.common.sensor.DroppedItemSensor;
import openccsensors.common.sensor.InventorySensor;
import openccsensors.common.sensor.MinecartSensor;
import openccsensors.common.sensor.ProximitySensor;
import openccsensors.common.sensor.SignSensor;
import openccsensors.common.sensor.SonicSensor;
import openccsensors.common.sensor.TankSensor;
import openccsensors.common.sensor.WorldSensor;
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
		
		ProximitySensor proximitySensor = new ProximitySensor();
		sensorCard.addSensorCard(1, new SensorCard(proximitySensor, tier1));
		sensorCard.addSensorCard(2, new SensorCard(proximitySensor, tier2));
		sensorCard.addSensorCard(3, new SensorCard(proximitySensor, tier3));
		sensorCard.addSensorCard(4, new SensorCard(proximitySensor, tier4));
		sensorCard.addIconsForLoading(proximitySensor);
		
		DroppedItemSensor droppedItemSensor = new DroppedItemSensor();
		sensorCard.addSensorCard(5, new SensorCard(droppedItemSensor, tier1));
		sensorCard.addSensorCard(6, new SensorCard(droppedItemSensor, tier2));
		sensorCard.addSensorCard(7, new SensorCard(droppedItemSensor, tier3));
		sensorCard.addSensorCard(8, new SensorCard(droppedItemSensor, tier4));
		sensorCard.addIconsForLoading(droppedItemSensor);
		
		SignSensor signSensor = new SignSensor();
		sensorCard.addSensorCard(9, new SensorCard(signSensor, tier1));
		sensorCard.addSensorCard(10, new SensorCard(signSensor, tier2));
		sensorCard.addSensorCard(11, new SensorCard(signSensor, tier3));
		sensorCard.addSensorCard(12, new SensorCard(signSensor, tier4));
		sensorCard.addIconsForLoading(signSensor);
		
		MinecartSensor minecartSensor = new MinecartSensor();
		sensorCard.addSensorCard(13, new SensorCard(minecartSensor, tier1));
		sensorCard.addSensorCard(14, new SensorCard(minecartSensor, tier2));
		sensorCard.addSensorCard(15, new SensorCard(minecartSensor, tier3));
		sensorCard.addSensorCard(16, new SensorCard(minecartSensor, tier4));
		sensorCard.addIconsForLoading(minecartSensor);
		
		SonicSensor sonicSensor = new SonicSensor();
		sensorCard.addSensorCard(17, new SensorCard(sonicSensor, tier1));
		sensorCard.addSensorCard(18, new SensorCard(sonicSensor, tier2));
		sensorCard.addSensorCard(19, new SensorCard(sonicSensor, tier3));
		sensorCard.addSensorCard(20, new SensorCard(sonicSensor, tier4));
		sensorCard.addIconsForLoading(sonicSensor);

		TankSensor tankSensor = new TankSensor();
		sensorCard.addSensorCard(18, new SensorCard(tankSensor, tier1));
		sensorCard.addSensorCard(19, new SensorCard(tankSensor, tier2));
		sensorCard.addSensorCard(20, new SensorCard(tankSensor, tier3));
		sensorCard.addSensorCard(21, new SensorCard(tankSensor, tier4));
		sensorCard.addIconsForLoading(tankSensor);
		
		InventorySensor inventorySensor = new InventorySensor();
		sensorCard.addSensorCard(22, new SensorCard(inventorySensor, tier1));
		sensorCard.addSensorCard(23, new SensorCard(inventorySensor, tier2));
		sensorCard.addSensorCard(24, new SensorCard(inventorySensor, tier3));
		sensorCard.addSensorCard(25, new SensorCard(inventorySensor, tier4));
		sensorCard.addIconsForLoading(inventorySensor);

		WorldSensor worldSensor = new WorldSensor();
		sensorCard.addSensorCard(26, new SensorCard(worldSensor, tier1));
		sensorCard.addIconsForLoading(worldSensor);
		
		// gauge.registerSensor(sensor);
	}
	
	
	public void registerRenderInformation() {

	}
}
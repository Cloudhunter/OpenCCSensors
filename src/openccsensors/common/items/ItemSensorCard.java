package openccsensors.common.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import openccsensors.OpenCCSensors.Items;
import openccsensors.common.api.ISensor;
import openccsensors.common.api.SensorCardInterface;
import openccsensors.common.api.SensorUpgradeTier;
import openccsensors.common.core.OCSLog;
import openccsensors.common.helper.RecipeHelper;
import openccsensors.common.sensors.BuildCraftSensor;
import openccsensors.common.sensors.DevSensor;
import openccsensors.common.sensors.DroppedItemSensor;
import openccsensors.common.sensors.IndustrialCraftSensor;
import openccsensors.common.sensors.InventorySensor;
import openccsensors.common.sensors.MinecartSensor;
import openccsensors.common.sensors.ProximitySensor;
import openccsensors.common.sensors.SignSensor;
import openccsensors.common.sensors.TankSensor;
import openccsensors.common.sensors.ThaumCraftSensor;
import openccsensors.common.sensors.WorldSensor;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dan200.computer.api.ComputerCraftAPI;

public class ItemSensorCard extends Item {

	private static HashMap<Integer, SensorCardInterface> interfaces = new HashMap<Integer, SensorCardInterface>();

	public static SensorCardInterface PROXIMITY_TIER_1 = null;
	public static SensorCardInterface PROXIMITY_TIER_2 = 	null;
	public static SensorCardInterface PROXIMITY_TIER_3 =	null;
	public static SensorCardInterface PROXIMITY_TIER_4 = 	null;
	
	public static SensorCardInterface DROPPED_TIER_1 = 	null;
	public static SensorCardInterface DROPPED_TIER_2 = 	null;
	public static SensorCardInterface DROPPED_TIER_3 = 	null;
	public static SensorCardInterface DROPPED_TIER_4 = 	null;
	
	public static SensorCardInterface DEV_SENSOR = 		null;
	
	public static SensorCardInterface INVENTORY_TIER_1 = 	null;
	public static SensorCardInterface INVENTORY_TIER_2 = 	null;
	public static SensorCardInterface INVENTORY_TIER_3 = 	null;
	public static SensorCardInterface INVENTORY_TIER_4 = 	null;
	
	public static SensorCardInterface SIGN_TIER_1 = 		null;
	public static SensorCardInterface SIGN_TIER_2 = 		null;
	public static SensorCardInterface SIGN_TIER_3 = 		null;
	public static SensorCardInterface SIGN_TIER_4 = 		null;
	
	public static SensorCardInterface TANK_TIER_1 = 		null;
	public static SensorCardInterface TANK_TIER_2 = 		null;
	public static SensorCardInterface TANK_TIER_3 = 		null;
	public static SensorCardInterface TANK_TIER_4 = 		null;
	
	public static SensorCardInterface MINECART_TIER_1 = 	null;
	public static SensorCardInterface MINECART_TIER_2 = 	null;
	public static SensorCardInterface MINECART_TIER_3 = 	null;
	public static SensorCardInterface MINECART_TIER_4 = 	null;
	
	public static SensorCardInterface WORLD_TIER_1 = 		null;
	
	public static SensorCardInterface BUILDCRAFT_TIER_1 = null;
	public static SensorCardInterface BUILDCRAFT_TIER_2 = null;
	public static SensorCardInterface BUILDCRAFT_TIER_3 = null;
	public static SensorCardInterface BUILDCRAFT_TIER_4 = null;
	
	public static SensorCardInterface INDUSTRIALCRAFT_TIER_1 = null;
	public static SensorCardInterface INDUSTRIALCRAFT_TIER_2 = null;
	public static SensorCardInterface INDUSTRIALCRAFT_TIER_3 = null;
	public static SensorCardInterface INDUSTRIALCRAFT_TIER_4 = null;
	
	public static SensorCardInterface THAUMCRAFT_TIER_1 = null;
	public static SensorCardInterface THAUMCRAFT_TIER_2 = null;
	public static SensorCardInterface THAUMCRAFT_TIER_3 = null;
	public static SensorCardInterface THAUMCRAFT_TIER_4 = null;
	
	public ItemSensorCard(int par1) {
		super(par1);
		setTextureFile("/openccsensors/resources/images/terrain.png");
		setHasSubtypes(true);
		this.setMaxDamage(0);
		setCreativeTab(ComputerCraftAPI.getCreativeTab());
		
	}

	@Override
	public int getIconFromDamage(int par1) {
		return par1;
	}

	
	@Override
	public String getItemNameIS(ItemStack itemstack) {
		return getInterfaceForStack(itemstack).getName();
	}

	public static void registerInterface(SensorCardInterface sensorInterface) {
		if (sensorInterface.getSensor() != null) {
			interfaces.put(sensorInterface.getId(), sensorInterface);
		}
	}
	
	public static HashMap<Integer, SensorCardInterface> getAllInterfaces() {
		return interfaces;
	}
	
	public static void init() {

		PROXIMITY_TIER_1 = 	new SensorCardInterface(17, "openccsensors.item.proximitysensor", SensorUpgradeTier.TIER1, ProximitySensor.class);
		PROXIMITY_TIER_2 = 	new SensorCardInterface(33, "openccsensors.item.proximitysensor", SensorUpgradeTier.TIER2, ProximitySensor.class);
		PROXIMITY_TIER_3 =	new SensorCardInterface(49, "openccsensors.item.proximitysensor", SensorUpgradeTier.TIER3, ProximitySensor.class);
		PROXIMITY_TIER_4 = 	new SensorCardInterface(65, "openccsensors.item.proximitysensor", SensorUpgradeTier.TIER4, ProximitySensor.class);
		
		DROPPED_TIER_1 = 	new SensorCardInterface(22, "openccsensors.item.droppeditemsensor", SensorUpgradeTier.TIER1, DroppedItemSensor.class);
		DROPPED_TIER_2 = 	new SensorCardInterface(38, "openccsensors.item.droppeditemsensor", SensorUpgradeTier.TIER2, DroppedItemSensor.class);
		DROPPED_TIER_3 = 	new SensorCardInterface(54, "openccsensors.item.droppeditemsensor", SensorUpgradeTier.TIER3, DroppedItemSensor.class);
		DROPPED_TIER_4 = 	new SensorCardInterface(70, "openccsensors.item.droppeditemsensor", SensorUpgradeTier.TIER4, DroppedItemSensor.class);
		
		DEV_SENSOR = 		new SensorCardInterface(99, "openccsensors.item.devsensor", SensorUpgradeTier.TIER4, DevSensor.class);
		
		INVENTORY_TIER_1 = 	new SensorCardInterface(16, "openccsensors.item.inventorysensor", SensorUpgradeTier.TIER1, InventorySensor.class);
		INVENTORY_TIER_2 = 	new SensorCardInterface(32, "openccsensors.item.inventorysensor", SensorUpgradeTier.TIER2, InventorySensor.class);
		INVENTORY_TIER_3 = 	new SensorCardInterface(48, "openccsensors.item.inventorysensor", SensorUpgradeTier.TIER3, InventorySensor.class);
		INVENTORY_TIER_4 = 	new SensorCardInterface(64, "openccsensors.item.inventorysensor", SensorUpgradeTier.TIER4, InventorySensor.class);
		
		SIGN_TIER_1 = 		new SensorCardInterface(23, "openccsensors.item.signsensor", SensorUpgradeTier.TIER1, SignSensor.class);
		SIGN_TIER_2 = 		new SensorCardInterface(39, "openccsensors.item.signsensor", SensorUpgradeTier.TIER2, SignSensor.class);
		SIGN_TIER_3 = 		new SensorCardInterface(55, "openccsensors.item.signsensor", SensorUpgradeTier.TIER3, SignSensor.class);
		SIGN_TIER_4 = 		new SensorCardInterface(71, "openccsensors.item.signsensor", SensorUpgradeTier.TIER4, SignSensor.class);
		
		TANK_TIER_1 = 		new SensorCardInterface(20, "openccsensors.item.tanksensor", SensorUpgradeTier.TIER1, TankSensor.class);
		TANK_TIER_2 = 		new SensorCardInterface(36, "openccsensors.item.tanksensor", SensorUpgradeTier.TIER2, TankSensor.class);
		TANK_TIER_3 = 		new SensorCardInterface(52, "openccsensors.item.tanksensor", SensorUpgradeTier.TIER3, TankSensor.class);
		TANK_TIER_4 = 		new SensorCardInterface(68, "openccsensors.item.tanksensor", SensorUpgradeTier.TIER4, TankSensor.class);
		
		MINECART_TIER_1 = 	new SensorCardInterface(24, "openccsensors.item.minecartsensor", SensorUpgradeTier.TIER1, MinecartSensor.class);
		MINECART_TIER_2 = 	new SensorCardInterface(40, "openccsensors.item.minecartsensor", SensorUpgradeTier.TIER2, MinecartSensor.class);
		MINECART_TIER_3 = 	new SensorCardInterface(56, "openccsensors.item.minecartsensor", SensorUpgradeTier.TIER3, MinecartSensor.class);
		MINECART_TIER_4 = 	new SensorCardInterface(72, "openccsensors.item.minecartsensor", SensorUpgradeTier.TIER4, MinecartSensor.class);
		
		WORLD_TIER_1 = 		new SensorCardInterface(21, "openccsensors.item.worldsensor", SensorUpgradeTier.TIER1, WorldSensor.class);
		
		if (Loader.isModLoaded("BuildCraft|Core")) {
			BUILDCRAFT_TIER_1 = new SensorCardInterface(19, "openccsensors.item.buildcraftsensor", SensorUpgradeTier.TIER1, BuildCraftSensor.class);
			BUILDCRAFT_TIER_2 = new SensorCardInterface(35, "openccsensors.item.buildcraftsensor", SensorUpgradeTier.TIER2, BuildCraftSensor.class);
			BUILDCRAFT_TIER_3 = new SensorCardInterface(51, "openccsensors.item.buildcraftsensor", SensorUpgradeTier.TIER3, BuildCraftSensor.class);
			BUILDCRAFT_TIER_4 = new SensorCardInterface(67, "openccsensors.item.buildcraftsensor", SensorUpgradeTier.TIER4, BuildCraftSensor.class);
		}
		if (Loader.isModLoaded("IC2")) {
			INDUSTRIALCRAFT_TIER_1 = new SensorCardInterface(18, "openccsensors.item.industrialcraftsensor", SensorUpgradeTier.TIER1, IndustrialCraftSensor.class);
			INDUSTRIALCRAFT_TIER_2 = new SensorCardInterface(34, "openccsensors.item.industrialcraftsensor", SensorUpgradeTier.TIER2, IndustrialCraftSensor.class);
			INDUSTRIALCRAFT_TIER_3 = new SensorCardInterface(50, "openccsensors.item.industrialcraftsensor", SensorUpgradeTier.TIER3, IndustrialCraftSensor.class);
			INDUSTRIALCRAFT_TIER_4 = new SensorCardInterface(66, "openccsensors.item.industrialcraftsensor", SensorUpgradeTier.TIER4, IndustrialCraftSensor.class);
		}
		if (Loader.isModLoaded("Thaumcraft")) {
			THAUMCRAFT_TIER_1 = new SensorCardInterface(25, "openccsensors.item.thaumcraftsensor", SensorUpgradeTier.TIER1, ThaumCraftSensor.class);
			THAUMCRAFT_TIER_2 = new SensorCardInterface(41, "openccsensors.item.thaumcraftsensor", SensorUpgradeTier.TIER2, ThaumCraftSensor.class);
			THAUMCRAFT_TIER_3 = new SensorCardInterface(57, "openccsensors.item.thaumcraftsensor", SensorUpgradeTier.TIER3, ThaumCraftSensor.class);
			THAUMCRAFT_TIER_4 = new SensorCardInterface(73, "openccsensors.item.thaumcraftsensor", SensorUpgradeTier.TIER4, ThaumCraftSensor.class);
		}
		
		registerInterfaces(new SensorCardInterface[] {
				PROXIMITY_TIER_1, PROXIMITY_TIER_2, PROXIMITY_TIER_3, PROXIMITY_TIER_4,
				DROPPED_TIER_1, DROPPED_TIER_2, DROPPED_TIER_3, DROPPED_TIER_4,
				DEV_SENSOR,
				INVENTORY_TIER_1, INVENTORY_TIER_2, INVENTORY_TIER_3, INVENTORY_TIER_4, 
				SIGN_TIER_1, SIGN_TIER_2, SIGN_TIER_3, SIGN_TIER_4,
				TANK_TIER_1, TANK_TIER_2, TANK_TIER_3, TANK_TIER_4, 
				MINECART_TIER_1, MINECART_TIER_2, MINECART_TIER_3, MINECART_TIER_4, 
				WORLD_TIER_1,
				BUILDCRAFT_TIER_1, BUILDCRAFT_TIER_2, BUILDCRAFT_TIER_3, BUILDCRAFT_TIER_4,
				INDUSTRIALCRAFT_TIER_1, INDUSTRIALCRAFT_TIER_2, INDUSTRIALCRAFT_TIER_3, INDUSTRIALCRAFT_TIER_4,
				THAUMCRAFT_TIER_1, THAUMCRAFT_TIER_2, THAUMCRAFT_TIER_3, THAUMCRAFT_TIER_4
		});
		OCSLog.info("init init iniiiitt");
	}
	
	public static void registerInterfaces(SensorCardInterface[] sensorInterfaces) {
		for (SensorCardInterface iface : sensorInterfaces) {
			if (iface != null) {
				registerInterface(iface);
			}
		}
	}

	public static SensorCardInterface getInterfaceForDamageValue(int dmgValue) {
		return interfaces.get(dmgValue);
	}
	
	public static SensorCardInterface getInterfaceForStack(ItemStack stack) {
		return getInterfaceForDamageValue(stack.getItemDamage());
	}
	
	public static SensorCardInterface getInterfaceForSensorAndUpgrade(Class sensorClass, SensorUpgradeTier tier) {
		for (Entry<Integer, SensorCardInterface> entry : interfaces.entrySet()) {
			SensorCardInterface iface = entry.getValue();
			if (sensorClass.isAssignableFrom(iface.getSensor().getClass()) && tier == iface.getSensorUpgrade()) {
				return iface;
			}
		}
		return null;
	}
	
	@Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
		list.add("Mk" + getInterfaceForStack(itemStack).getSensorUpgrade().getLevel());
	}
	
	
    @SideOnly(Side.CLIENT)
    @Override
    public EnumRarity getRarity(ItemStack itemStack)
    {
        return getInterfaceForStack(itemStack).getSensorUpgrade().getRarity();
    }

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs tab, List subItems) {
		for (Entry<Integer, SensorCardInterface> entry : interfaces.entrySet()) {
			subItems.add(new ItemStack(par1, 1, entry.getValue().getId()));
		}
	}
}

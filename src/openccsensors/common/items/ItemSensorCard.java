package openccsensors.common.items;

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
import openccsensors.common.api.SensorCardInterface;
import openccsensors.common.api.SensorUpgradeTier;
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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dan200.computer.api.ComputerCraftAPI;

public class ItemSensorCard extends Item {

	private HashMap<Integer, SensorCardInterface> interfaces = new HashMap<Integer, SensorCardInterface>();

	public ItemSensorCard(int par1) {
		super(par1);
		setTextureFile("/openccsensors/resources/images/terrain.png");
		setHasSubtypes(true);
		this.setMaxDamage(0);
		setCreativeTab(ComputerCraftAPI.getCreativeTab());
		
		registerInterface(new SensorCardInterface(17, "openccsensors.item.proximitysensor", SensorUpgradeTier.TIER1, ProximitySensor.class));	
		registerInterface(new SensorCardInterface(33, "openccsensors.item.proximitysensor", SensorUpgradeTier.TIER2, ProximitySensor.class));
		registerInterface(new SensorCardInterface(49, "openccsensors.item.proximitysensor", SensorUpgradeTier.TIER3, ProximitySensor.class));
		registerInterface(new SensorCardInterface(65, "openccsensors.item.proximitysensor", SensorUpgradeTier.TIER4, ProximitySensor.class));
		
		
		registerInterface(new SensorCardInterface(22, "openccsensors.item.droppeditemsensor", SensorUpgradeTier.TIER1, DroppedItemSensor.class));
		registerInterface(new SensorCardInterface(38, "openccsensors.item.droppeditemsensor", SensorUpgradeTier.TIER2, DroppedItemSensor.class));
		registerInterface(new SensorCardInterface(54, "openccsensors.item.droppeditemsensor", SensorUpgradeTier.TIER3, DroppedItemSensor.class));
		registerInterface(new SensorCardInterface(70, "openccsensors.item.droppeditemsensor", SensorUpgradeTier.TIER4, DroppedItemSensor.class));
		
		registerInterface(new SensorCardInterface(99, "openccsensors.item.devsensor", SensorUpgradeTier.TIER4, DevSensor.class));
		
		registerInterface(new SensorCardInterface(16, "openccsensors.item.inventorysensor", SensorUpgradeTier.TIER1, InventorySensor.class));
		registerInterface(new SensorCardInterface(32, "openccsensors.item.inventorysensor", SensorUpgradeTier.TIER2, InventorySensor.class));
		registerInterface(new SensorCardInterface(48, "openccsensors.item.inventorysensor", SensorUpgradeTier.TIER3, InventorySensor.class));
		registerInterface(new SensorCardInterface(64, "openccsensors.item.inventorysensor", SensorUpgradeTier.TIER4, InventorySensor.class));
		
		registerInterface(new SensorCardInterface(23, "openccsensors.item.signsensor", SensorUpgradeTier.TIER1, SignSensor.class));
		registerInterface(new SensorCardInterface(39, "openccsensors.item.signsensor", SensorUpgradeTier.TIER2, SignSensor.class));
		registerInterface(new SensorCardInterface(55, "openccsensors.item.signsensor", SensorUpgradeTier.TIER3, SignSensor.class));
		registerInterface(new SensorCardInterface(71, "openccsensors.item.signsensor", SensorUpgradeTier.TIER4, SignSensor.class));
		
		registerInterface(new SensorCardInterface(20, "openccsensors.item.tanksensor", SensorUpgradeTier.TIER1, TankSensor.class));
		registerInterface(new SensorCardInterface(36, "openccsensors.item.tanksensor", SensorUpgradeTier.TIER2, TankSensor.class));
		registerInterface(new SensorCardInterface(52, "openccsensors.item.tanksensor", SensorUpgradeTier.TIER3, TankSensor.class));
		registerInterface(new SensorCardInterface(68, "openccsensors.item.tanksensor", SensorUpgradeTier.TIER4, TankSensor.class));
		
		registerInterface(new SensorCardInterface(25, "openccsensors.item.minecartsensor", SensorUpgradeTier.TIER1, MinecartSensor.class));
		registerInterface(new SensorCardInterface(41, "openccsensors.item.minecartsensor", SensorUpgradeTier.TIER2, MinecartSensor.class));
		registerInterface(new SensorCardInterface(57, "openccsensors.item.minecartsensor", SensorUpgradeTier.TIER3, MinecartSensor.class));
		registerInterface(new SensorCardInterface(73, "openccsensors.item.minecartsensor", SensorUpgradeTier.TIER4, MinecartSensor.class));
		
		registerInterface(new SensorCardInterface(21, "openccsensors.item.worldsensor", SensorUpgradeTier.TIER1, WorldSensor.class));
		
		registerInterface(new SensorCardInterface(19, "openccsensors.item.buildcraftsensor", SensorUpgradeTier.TIER1, BuildCraftSensor.class));
		registerInterface(new SensorCardInterface(35, "openccsensors.item.buildcraftsensor", SensorUpgradeTier.TIER2, BuildCraftSensor.class));
		registerInterface(new SensorCardInterface(51, "openccsensors.item.buildcraftsensor", SensorUpgradeTier.TIER3, BuildCraftSensor.class));
		registerInterface(new SensorCardInterface(67, "openccsensors.item.buildcraftsensor", SensorUpgradeTier.TIER4, BuildCraftSensor.class));
		
		registerInterface(new SensorCardInterface(18, "openccsensors.item.industrialcraftsensor", SensorUpgradeTier.TIER1, IndustrialCraftSensor.class));
		registerInterface(new SensorCardInterface(34, "openccsensors.item.industrialcraftsensor", SensorUpgradeTier.TIER2, IndustrialCraftSensor.class));
		registerInterface(new SensorCardInterface(50, "openccsensors.item.industrialcraftsensor", SensorUpgradeTier.TIER3, IndustrialCraftSensor.class));
		registerInterface(new SensorCardInterface(66, "openccsensors.item.industrialcraftsensor", SensorUpgradeTier.TIER4, IndustrialCraftSensor.class));
		
		registerInterface(new SensorCardInterface(24, "openccsensors.item.thaumcraftsensor", SensorUpgradeTier.TIER1, ThaumCraftSensor.class));
		registerInterface(new SensorCardInterface(40, "openccsensors.item.thaumcraftsensor", SensorUpgradeTier.TIER2, ThaumCraftSensor.class));
		registerInterface(new SensorCardInterface(56, "openccsensors.item.thaumcraftsensor", SensorUpgradeTier.TIER3, ThaumCraftSensor.class));
		registerInterface(new SensorCardInterface(72, "openccsensors.item.thaumcraftsensor", SensorUpgradeTier.TIER4, ThaumCraftSensor.class));
		
	}

	@Override
	public int getIconFromDamage(int par1) {
		return par1;
	}

	
	@Override
	public String getItemNameIS(ItemStack itemstack) {
		return getInterfaceForStack(itemstack).getName();
	}

	public void registerInterface(SensorCardInterface sensorInterface) {
		if (sensorInterface.getSensor() != null) {
			interfaces.put(sensorInterface.getId(), sensorInterface);
		}
	}

	public SensorCardInterface getInterfaceForDamageValue(int dmgValue) {
		return interfaces.get(dmgValue);
	}
	
	private SensorCardInterface getInterfaceForStack(ItemStack stack) {
		return getInterfaceForDamageValue(stack.getItemDamage());
	}
	
	@Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
		list.add("Tier " + getInterfaceForStack(itemStack).getSensorUpgrade().getLevel());
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

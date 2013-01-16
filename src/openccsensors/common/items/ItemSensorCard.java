package openccsensors.common.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import openccsensors.common.api.SensorCardInterface;
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
	}

	@Override
	public int getIconFromDamage(int par1) {
		return par1;
	}

	@Override
	public String getItemNameIS(ItemStack itemstack) {
		return getInterfaceForDamageValue(itemstack.getItemDamage()).getName();
	}

	public void registerInterface(SensorCardInterface sensorInterface) {
		if (sensorInterface.getSensor() != null) {
			interfaces.put(sensorInterface.getId(), sensorInterface);
		}
	}

	public SensorCardInterface getInterfaceForDamageValue(int dmgValue) {
		return interfaces.get(dmgValue);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs tab, List subItems) {
		for (Entry<Integer, SensorCardInterface> entry : interfaces.entrySet()) {
			subItems.add(new ItemStack(par1, 1, entry.getValue().getId()));
		}
	}
}

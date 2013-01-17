package openccsensors.common.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dan200.computer.api.ComputerCraftAPI;

import openccsensors.common.api.SensorCardInterface;
import openccsensors.common.api.SensorUpgradeTier;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemGeneric extends Item {

	
	private static HashMap<Integer, ItemMetaData> metadata = new HashMap<Integer, ItemMetaData>();
	
	public static void register(ItemMetaData md) {
		metadata.put(md.getId(), md);
	}

	public static ItemMetaData getMetaDataForItemStack(ItemStack stack) {
		return getMetaDataForDamageValue(stack.getItemDamage());
	}
	
	public static ItemMetaData getMetaDataForDamageValue(int damageValue) {
		return metadata.get(damageValue);
	}	

	public ItemGeneric(int par1) {
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
		return getMetaDataForItemStack(itemstack).getName();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs tab, List subItems) {
		for (Entry<Integer, ItemMetaData> entry : metadata.entrySet()) {
			subItems.add(new ItemStack(par1, 1, entry.getValue().getId()));
		}
	}
	
}

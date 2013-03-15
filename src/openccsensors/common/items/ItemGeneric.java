package openccsensors.common.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dan200.computer.api.ComputerCraftAPI;

public class ItemGeneric extends Item {
	
	/**
	 * STATIC
	 */
	
	public static final ItemMetaData RANGE_EXTENSION_ANTENNA = 	new ItemMetaData(240, "openccsensors.item.rangeextensionantenna", "openccsensors:rangeExtensionAntenna");
	public static final ItemMetaData SIGNAL_AMPLIFIER = 		new ItemMetaData(241, "openccsensors.item.signalamplifier", "openccsensors:signalAmplifier");
	public static final ItemMetaData ADVANCED_AMPLIFIER = 		new ItemMetaData(242, "openccsensors.item.advancedamplifier",  "openccsensors:advancedAmplifier");
	
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
	
	/**
	 * ItemGeneric
	 */

	public ItemGeneric(int par1) {
		super(par1);
		setHasSubtypes(true);
		this.setMaxDamage(0);
		setCreativeTab(ComputerCraftAPI.getCreativeTab());
		
		register(RANGE_EXTENSION_ANTENNA);
		register(SIGNAL_AMPLIFIER);
		register(ADVANCED_AMPLIFIER);
	}
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs tab, List subItems) {
		for (Entry<Integer, ItemMetaData> entry : metadata.entrySet()) {
			subItems.add(new ItemStack(par1, 1, entry.getValue().getId()));
		}
	}
	
	public Icon getIconFromDamage(int i) {
	    return getMetaDataForDamageValue(i).getIcon();
	}
	
	public void func_94581_a(IconRegister iconRegister)
	{
		for (Entry<Integer, ItemMetaData> entry : metadata.entrySet()) {
			entry.getValue().registerIcon(iconRegister);
		}
	}
	
}

package openccsensors.common.items;

import java.util.List;
import java.util.Map.Entry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import openccsensors.common.SensorInterfaceManager;
import openccsensors.common.api.ISensorInterface;
import openccsensors.common.core.OCSLog;
import dan200.computer.api.ComputerCraftAPI;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemSensorUpgrade extends Item {

	public enum Upgrades {
		
		EXTENSION_ANTENNA	("openccsensors.item.extension_antenna"),
		SIGNAL_AMPLIFIER	("openccsensors.item.signal_amplifier"),
		ADVANCED_AMPLIFIER	("openccsensors.item.advanced_amplifier");
		
		private final String name;
		
		Upgrades(String name) {
			this.name = name;
		}
	    
	    public String getName()
	    {
	    	return this.name;
	    }
	}
	
	public ItemSensorUpgrade(int par1) {
		super(par1);
		setTextureFile("/openccsensors/resources/images/terrain.png");
		setHasSubtypes(true);
        this.setMaxDamage(0);
		setCreativeTab(ComputerCraftAPI.getCreativeTab());
	}

	@Override
	public int getIconFromDamage(int par1)
	{
	    return 240  + par1;
	}

	@Override
	public String getItemNameIS(ItemStack itemstack)
	{
		return Upgrades.values()[itemstack.getItemDamage()].getName();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs tab, List subItems)
	{
		for (Upgrades v : Upgrades.values()) 
		{
	    	subItems.add(new ItemStack(par1, 1, v.ordinal()));
		}
	}
}

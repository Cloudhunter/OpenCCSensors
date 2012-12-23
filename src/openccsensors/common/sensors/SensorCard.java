package openccsensors.common.sensors;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import openccsensors.common.api.ISensorCard;
import openccsensors.common.api.ISensorInterface;

public class SensorCard extends Item implements ISensorCard
{
	private static HashMap<Integer, ISensorInterface> interfaces = new HashMap<Integer, ISensorInterface>();
	
	public SensorCard(int par1) {
		super(par1);
		setTextureFile("/openccsensors/resources/images/terrain.png");
		setHasSubtypes(true);
        this.setMaxDamage(0);
		setCreativeTab(CreativeTabs.tabRedstone);
		for (Entry<Integer, ISensorInterface> entry : interfaces.entrySet())
		{
			entry.getValue().initRecipes(this);
		}
	}

	@Override
	public ISensorInterface getSensorInterface(ItemStack itemstack, boolean turtle) 
	{
		return interfaces.get(itemstack.getItemDamage());
	}

	@Override
	public String getItemNameIS(ItemStack itemstack)
	{
		int dmgValue = itemstack.getItemDamage();
		ISensorInterface iface = interfaces.get(dmgValue);
		if (iface != null)
		{
			return iface.getName();
		}
		
		return "Sensor Card";
	}

	@Override
	public int getIconFromDamage(int par1)
	{
	    return par1;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs tab, List subItems)
	{
		for (Entry<Integer, ISensorInterface> entry : interfaces.entrySet())
		{
	    	subItems.add(new ItemStack(par1, 1, entry.getKey()));
		}
	}

	public static void registerInterface(ISensorInterface iface)
	{
		interfaces.put(iface.getId(), iface);
	}
	

}

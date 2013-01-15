package openccsensors.common.sensors;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import openccsensors.common.SensorInterfaceManager;
import openccsensors.common.api.ISensorCard;
import openccsensors.common.api.ISensorInterface;
import openccsensors.common.helper.SensorHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dan200.computer.api.ComputerCraftAPI;

public class SensorCard extends Item implements ISensorCard
{
	public SensorCard(int par1) {
		super(par1);
		setTextureFile("/openccsensors/resources/images/terrain.png");
		setHasSubtypes(true);
        this.setMaxDamage(0);
		setCreativeTab(ComputerCraftAPI.getCreativeTab());
		for (Entry<Integer, ISensorInterface> entry : SensorInterfaceManager.Interfaces.entrySet())
		{
			entry.getValue().initRecipes(this);
		}
	}

	@Override
	public int getIconFromDamage(int par1)
	{
	    return par1;
	}
	

	@Override
	public String getItemNameIS(ItemStack itemstack)
	{
		int dmgValue = itemstack.getItemDamage();
		
		ISensorInterface iface = SensorInterfaceManager.Interfaces.get(
				SensorHelper.getSensorInterfaceId(itemstack.getItemDamage()
		));
		
		if (iface != null)
		{
			return iface.getName();
		}
		
		return "Sensor Card";
	}
	
	@Override
	public ISensorInterface getSensorInterface(ItemStack itemstack, boolean turtle) 
	{
		return SensorInterfaceManager.Interfaces.get(
					SensorHelper.getSensorInterfaceId(itemstack.getItemDamage()
			));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs tab, List subItems)
	{
		for (Entry<Integer, ISensorInterface> entry : SensorInterfaceManager.Interfaces.entrySet())
		{
			for (int i = 0; i < 4; i++)
			{
				subItems.add(new ItemStack(par1, 1, entry.getKey() + (i * 16)));
			}
		}
	}

}

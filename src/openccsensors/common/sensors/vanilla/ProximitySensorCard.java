package openccsensors.common.sensors.vanilla;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import openccsensors.common.api.ISensorCard;
import openccsensors.common.api.ISensorInterface;


/**
 * Item which allows sensing of living entities within the defined radius
 */
public class ProximitySensorCard extends Item implements ISensorCard
{

	public ProximitySensorCard(int par1)
	{
		super(par1);
		setCreativeTab(CreativeTabs.tabRedstone);
		
		GameRegistry.addRecipe(new ItemStack(this),
				"rpr",
				"rrr",
				"rrr",
				'r', new ItemStack(Item.redstone),
				'p', new ItemStack(Block.pressurePlateStone));
	}
	
	@Override
	public int getIconFromDamage(int par1) {
		return 1;
	}
	
	
	@Override
	public String getItemNameIS(ItemStack is)
	{
		return "openccsensors.item.proximitysensor";
	}

	@Override
	public ISensorInterface getSensorInterface(ItemStack itemstack,
			boolean turtle) {
		return new ProximitySensorInterface();
	}
	
}

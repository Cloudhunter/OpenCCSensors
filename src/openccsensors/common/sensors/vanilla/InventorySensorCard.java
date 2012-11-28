package openccsensors.common.sensors.vanilla;

import cpw.mods.fml.common.registry.GameRegistry;
import openccsensors.common.api.ISensorCard;
import openccsensors.common.api.ISensorInterface;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;


public class InventorySensorCard extends Item implements ISensorCard {

	public InventorySensorCard(int par1) {
		super(par1);
		setCreativeTab(CreativeTabs.tabRedstone);
		addRecipeToGameRegistry();
	}

	@Override
	public ISensorInterface getSensorInterface(ItemStack itemstack,
			boolean turtle) {
		return new InventorySensorInterface();
	}

	@Override
	public String getItemNameIS(ItemStack is) {
		return "openccsensors.item.inventorysensor";
	}
	
	public void addRecipeToGameRegistry()
	{
		
		GameRegistry.addRecipe(new ItemStack(this),
				"wwr",
				"wrr",
				"rrp",
				'w', new ItemStack(Block.planks),
				'r', new ItemStack(Item.redstone),
				'p', new ItemStack(Item.paper));
	}

}

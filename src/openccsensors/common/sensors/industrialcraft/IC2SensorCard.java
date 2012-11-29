package openccsensors.common.sensors.industrialcraft;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import openccsensors.common.api.ISensorCard;
import openccsensors.common.api.ISensorInterface;


public class IC2SensorCard extends Item implements ISensorCard {

	public IC2SensorCard(int par1) {
		super(par1);
		setCreativeTab(CreativeTabs.tabRedstone);
		GameRegistry.addRecipe(new ItemStack(this),
				"uur",
				"urr",
				"rrp",
				'r', new ItemStack(Item.redstone),
				'u', ic2.api.Items.getItem("uraniumIngot"),
				'p', new ItemStack(Item.paper));
	}

	@Override
	public ISensorInterface getSensorInterface(ItemStack itemstack,
			boolean turtle) {
		return new IC2SensorInterface();
	}

	@Override
	public String getItemNameIS(ItemStack is) {
		return "openccsensors.item.ic2sensor";
	}

}

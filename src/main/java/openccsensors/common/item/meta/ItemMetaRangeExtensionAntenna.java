package openccsensors.common.item.meta;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.ShapedOreRecipe;
import openccsensors.OpenCCSensors;
import openccsensors.api.IItemMeta;
import openccsensors.api.IRequiresIconLoading;

public class ItemMetaRangeExtensionAntenna implements IItemMeta, IRequiresIconLoading {
	
	private int id;
	private IIcon icon;
	
	public ItemMetaRangeExtensionAntenna(int id) {
		this.id = id;
		
		OpenCCSensors.Items.genericItem.addMeta(this);
		
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(
			newItemStack(1),
			new Object[] {
				" t ",
				"srs",
				"sis",
				Character.valueOf('t'), new ItemStack((Block)Block.blockRegistry.getObject("redstone_torch")),
				Character.valueOf('s'), new ItemStack((Item)Item.itemRegistry.getObject("stone")),
				Character.valueOf('r'), new ItemStack((Item)Item.itemRegistry.getObject("redstone")),
				Character.valueOf('i'), new ItemStack((Item)Item.itemRegistry.getObject("iron_ingot")),				
			}
		));
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public boolean displayInCreative() {
		return true;
	}

	@Override
	public IIcon getIcon() {
		return icon;
	}

	@Override
	public void loadIcon(IIconRegister iconRegistry) {
		icon = iconRegistry.registerIcon("openccsensors:rangeExtensionAntenna");
	}
	
	@Override
	public ItemStack newItemStack(int size) {
		return new ItemStack(OpenCCSensors.Items.genericItem, size, getId());
	}

	@Override
	public String getName() {
		return "rangeExtensionAntenna";
	}
}

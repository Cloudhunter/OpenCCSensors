package openccsensors.common.item.meta;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.Icon;
import net.minecraftforge.oredict.ShapedOreRecipe;
import openccsensors.OpenCCSensors;
import openccsensors.api.IItemMeta;
import openccsensors.api.IRequiresIconLoading;

public class ItemMetaRangeExtensionAntenna implements IItemMeta, IRequiresIconLoading {
	
	private int id;
	private Icon icon;
	
	public ItemMetaRangeExtensionAntenna(int id) {
		this.id = id;
		
		OpenCCSensors.Items.genericItem.addMeta(this);
		
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(
			newItemStack(1),
			new Object[] {
				" t ",
				"srs",
				"sis",
				Character.valueOf('t'), new ItemStack(Block.torchRedstoneActive),
				Character.valueOf('s'), new ItemStack(Block.stone),
				Character.valueOf('r'), new ItemStack(Item.redstone),
				Character.valueOf('i'), new ItemStack(Item.ingotIron),				
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
	public Icon getIcon() {
		return icon;
	}

	@Override
	public void loadIcon(IconRegister iconRegistry) {
		icon = iconRegistry.func_94245_a("openccsensors:rangeExtensionAntenna");
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

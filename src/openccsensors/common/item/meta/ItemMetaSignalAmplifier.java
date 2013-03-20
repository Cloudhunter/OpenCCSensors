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

public class ItemMetaSignalAmplifier implements IItemMeta, IRequiresIconLoading {

	private int id;
	private Icon icon;
	
	public ItemMetaSignalAmplifier(int id) {
		this.id = id;

		OpenCCSensors.Items.genericItem.addMeta(this);
		
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(
			newItemStack(1),
			new Object[] {
				"sgs",
				"rrr",
				"sgs",
				Character.valueOf('s'), new ItemStack(Block.stone),
				Character.valueOf('r'), new ItemStack(Item.redstone),
				Character.valueOf('g'), new ItemStack(Item.ingotGold),			
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
	public void loadIcon(IconRegister iconRegistry) {
		icon = iconRegistry.func_94245_a("openccsensors:signalAmplifier");
	}

	@Override
	public Icon getIcon() {
		return icon;
	}

	@Override
	public ItemStack newItemStack(int size) {
		return new ItemStack(OpenCCSensors.Items.genericItem, size, getId());
	}
	
	@Override
	public String getName() {
		return "signalAmplifier";
	}
}

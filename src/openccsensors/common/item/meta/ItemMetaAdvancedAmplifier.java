package openccsensors.common.item.meta;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.Icon;
import net.minecraftforge.oredict.ShapedOreRecipe;
import openccsensors.OpenCCSensors;
import openccsensors.api.IItemMeta;
import openccsensors.api.IRequiresIconLoading;

public class ItemMetaAdvancedAmplifier implements IItemMeta, IRequiresIconLoading {

	private int id;
	private Icon icon;
	
	public ItemMetaAdvancedAmplifier(int id) {
		this.id = id;
		
		OpenCCSensors.Items.genericItem.addMeta(this);
		
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(
			newItemStack(1),
			new Object[] {
				"igi",
				"rdr",
				"igi",
				Character.valueOf('i'), new ItemStack(Item.ingotIron),
				Character.valueOf('g'), new ItemStack(Item.ingotGold),
				Character.valueOf('r'), new ItemStack(Item.redstone),
				Character.valueOf('d'), new ItemStack(Item.diamond),			
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
		icon = iconRegistry.registerIcon("openccsensors:advancedAmplifier");
	}

	@Override
	public ItemStack newItemStack(int size) {
		return new ItemStack(OpenCCSensors.Items.genericItem, size, getId());
	}

	@Override
	public String getName() {
		return "advancedAmplifier";
	}
}

package openccsensors.common.item.meta;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.ShapedOreRecipe;
import openccsensors.OpenCCSensors;
import openccsensors.api.IItemMeta;
import openccsensors.api.IRequiresIconLoading;

public class ItemMetaAdvancedAmplifier implements IItemMeta, IRequiresIconLoading {

	private int id;
	private IIcon icon;
	
	public ItemMetaAdvancedAmplifier(int id) {
		this.id = id;
		
		OpenCCSensors.Items.genericItem.addMeta(this);
		
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(
			newItemStack(1),
			new Object[] {
				"igi",
				"rdr",
				"igi",
				Character.valueOf('i'), new ItemStack((Item)Item.itemRegistry.getObject("iron_ingot")),
				Character.valueOf('g'), new ItemStack((Item)Item.itemRegistry.getObject("gold_ingot")),
				Character.valueOf('r'), new ItemStack((Item)Item.itemRegistry.getObject("redstone")),
				Character.valueOf('d'), new ItemStack((Item)Item.itemRegistry.getObject("diamond")),			
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

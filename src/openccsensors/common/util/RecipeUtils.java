package openccsensors.common.util;

import openccsensors.OpenCCSensors;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipeUtils {

	public static void addTier1Recipe(ItemStack input, ItemStack output) {
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(
				output,
				new Object[] {
					"rpr",
					"rrr",
					"aaa",
					Character.valueOf('r'), new ItemStack(Item.redstone),
					Character.valueOf('a'), new ItemStack(Item.paper),
					Character.valueOf('p'), input				
				}
			));
		
	}
	
	public static void addTier2Recipe(ItemStack input, ItemStack output) {
		CraftingManager.getInstance().getRecipeList().add(new ShapelessOreRecipe(
				output,
				input,
				OpenCCSensors.Items.rangeExtensionAntenna.newItemStack(1)
			));
	}

	public static void addTier3Recipe(ItemStack input, ItemStack output) {
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(
				output,
				new Object[] {
					"aca",
					" m ",
					Character.valueOf('a'), OpenCCSensors.Items.rangeExtensionAntenna.newItemStack(1),
					Character.valueOf('c'), input,
					Character.valueOf('m'), OpenCCSensors.Items.signalAmplifier.newItemStack(1)			
				}
			));
	}
	
	public static void addTier4Recipe(ItemStack input, ItemStack output) {
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(
			output,
			new Object[] {
				" a ",
				"aca",
				"mpm",
				Character.valueOf('a'), OpenCCSensors.Items.rangeExtensionAntenna.newItemStack(1),
				Character.valueOf('c'), input,
				Character.valueOf('m'), OpenCCSensors.Items.signalAmplifier.newItemStack(1),	
				Character.valueOf('p'), OpenCCSensors.Items.advancedAmplifier.newItemStack(1)			
			}
		));
	}
	
}

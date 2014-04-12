package openccsensors.common.util;

import java.lang.reflect.Field;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import openccsensors.OpenCCSensors;
import openccsensors.api.SensorCard;

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
	
	public static void addSensorRecipe() {
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(
				new ItemStack(OpenCCSensors.Blocks.sensorBlock),
				new Object[] {
					"ooo",
					"ror",
					"sss",
					Character.valueOf('o'), new ItemStack(Block.obsidian),
					Character.valueOf('r'), new ItemStack(Item.redstone),
					Character.valueOf('s'), new ItemStack(Block.stone)
				}
			));	
	}
	
	public static void addGaugeRecipe() {
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(
				new ItemStack(OpenCCSensors.Blocks.gaugeBlock),
				new Object[] {
					"grm",
					Character.valueOf('g'), new ItemStack(Block.thinGlass),
					Character.valueOf('r'), new ItemStack(Item.redstone),
					Character.valueOf('m'), new ItemStack(getMonitor(), 1, 2)
				}
			));	
	}
	private static Block getMonitor() {
		Block monitor = null;
		try {
			Class cc = Class.forName("dan200.computercraft.ComputerCraft$Blocks");
			if (cc != null) {
				Field peripheralField = cc.getDeclaredField("peripheral");
				if (peripheralField != null) {
					monitor = (Block) peripheralField.get(cc);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return monitor;
	}
	
	public static void addProxSensorBlockRecipe() {

		Entry<Integer, SensorCard> entry = OpenCCSensors.Items.sensorCard.getEntryForSensorAndTier(
												OpenCCSensors.Sensors.proximitySensor,
												OpenCCSensors.Tiers.tier4
											);
		ItemStack proxCard = new ItemStack(OpenCCSensors.Items.sensorCard, 1, entry.getKey());
		
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(
			new ItemStack(OpenCCSensors.Blocks.basicSensorBlock),
			new Object[] {
				"   ",
				"cpc",
				"rir",
				Character.valueOf('c'), new ItemStack(Item.comparator),
				Character.valueOf('p'), proxCard,
				Character.valueOf('r'), new ItemStack(Item.redstone),
				Character.valueOf('i'), new ItemStack(Block.blockIron),				
			}
		));
	}
}

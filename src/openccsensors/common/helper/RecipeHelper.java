package openccsensors.common.helper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map.Entry;

import openccsensors.OpenCCSensors;
import openccsensors.common.api.SensorCardInterface;
import openccsensors.common.api.SensorUpgradeTier;
import openccsensors.common.core.OCSLog;
import openccsensors.common.items.ItemGeneric;
import openccsensors.common.items.ItemSensorCard;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipeHelper {
	
	public static void addTierUpgradeRecipes() {
		HashMap<Integer, SensorCardInterface> allInterfaces = ItemSensorCard.getAllInterfaces();
		SensorCardInterface iface;
		int id;
		for (Entry<Integer, SensorCardInterface> entry : allInterfaces.entrySet()) {
			iface = entry.getValue();
			id = entry.getKey();
			Class sensorClass = iface.getSensor().getClass();
			SensorCardInterface previousTier;
			switch(iface.getSensorUpgrade().getLevel())
			{
			case 2:
				previousTier = ItemSensorCard.getInterfaceForSensorAndUpgrade(sensorClass, SensorUpgradeTier.TIER1);
				CraftingManager.getInstance().getRecipeList().add(new ShapelessOreRecipe(
						iface.newItemStack(1),
						previousTier.newItemStack(1),
						ItemGeneric.RANGE_EXTENSION_ANTENNA.newItemStack(1)
					));
				break;
			case 3:
				previousTier = ItemSensorCard.getInterfaceForSensorAndUpgrade(sensorClass, SensorUpgradeTier.TIER2);
				CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(
						iface.newItemStack(1),
						new Object[] {
							"aca",
							" m ",
							Character.valueOf('a'), ItemGeneric.RANGE_EXTENSION_ANTENNA.newItemStack(1),
							Character.valueOf('c'), previousTier.newItemStack(1),
							Character.valueOf('m'), ItemGeneric.SIGNAL_AMPLIFIER.newItemStack(1)			
						}
					));
				break;
			case 4:
				previousTier = ItemSensorCard.getInterfaceForSensorAndUpgrade(sensorClass, SensorUpgradeTier.TIER3);
				if (previousTier != null) {
					CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(
							iface.newItemStack(1),
							new Object[] {
								" a ",
								"aca",
								"mpm",
								Character.valueOf('a'), ItemGeneric.RANGE_EXTENSION_ANTENNA.newItemStack(1),
								Character.valueOf('c'), previousTier.newItemStack(1),
								Character.valueOf('m'), ItemGeneric.SIGNAL_AMPLIFIER.newItemStack(1),	
								Character.valueOf('p'), ItemGeneric.ADVANCED_AMPLIFIER.newItemStack(1)			
							}
						));
				}
				break;
			}
		}
	}
	
	public static void addUpgradeItemRecipes() {
		
		/* range extension antenna */
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(
				ItemGeneric.RANGE_EXTENSION_ANTENNA.newItemStack(1),
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
		
		/* signal amplifier */
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(
				ItemGeneric.SIGNAL_AMPLIFIER.newItemStack(1),
				new Object[] {
					"sgs",
					"rrr",
					"sgs",
					Character.valueOf('s'), new ItemStack(Block.stone),
					Character.valueOf('r'), new ItemStack(Item.redstone),
					Character.valueOf('g'), new ItemStack(Item.ingotGold),			
				}
			));
		
		/* advanced amplifier */
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(
				ItemGeneric.ADVANCED_AMPLIFIER.newItemStack(1),
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
	
	public static void addTier1CardRecipe(SensorCardInterface iface, Object uniqueItem) {
		
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(
				iface.newItemStack(1),
				new Object[] {
					"rpr",
					"rrr",
					"aaa",
					Character.valueOf('r'), new ItemStack(Item.redstone),
					Character.valueOf('a'), new ItemStack(Item.paper),
					Character.valueOf('p'), uniqueItem				
				}
			));
	}
	
	public static void addGaugeRecipe()
	{
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
			Class cc = Class.forName("dan200.ComputerCraft$Blocks");
			if (cc != null) {
				Field peripheralField = cc.getDeclaredField("peripheral");
				if (peripheralField != null) {
					monitor = (Block) peripheralField.get(cc);
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return monitor;
	}
}

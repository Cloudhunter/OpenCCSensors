package universalelectricity.components.common;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import universalelectricity.components.common.block.BlockBCOre;
import universalelectricity.components.common.block.BlockBasicMachine;
import universalelectricity.components.common.block.BlockCopperWire;
import universalelectricity.components.common.item.ItemBasic;
import universalelectricity.components.common.item.ItemBattery;
import universalelectricity.components.common.item.ItemBlockBCOre;
import universalelectricity.components.common.item.ItemBlockBasicMachine;
import universalelectricity.components.common.item.ItemBlockCopperWire;
import universalelectricity.components.common.item.ItemCircuit;
import universalelectricity.components.common.item.ItemInfiniteBattery;
import universalelectricity.components.common.item.ItemIngot;
import universalelectricity.components.common.item.ItemPlate;
import universalelectricity.components.common.item.ItemWrench;
import universalelectricity.core.UniversalElectricity;
import universalelectricity.core.item.ElectricItemHelper;
import universalelectricity.core.item.ItemElectric;
import universalelectricity.prefab.RecipeHelper;
import universalelectricity.prefab.TranslationHelper;
import universalelectricity.prefab.ore.OreGenBase;
import universalelectricity.prefab.ore.OreGenReplaceStone;
import universalelectricity.prefab.ore.OreGenerator;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * The main class for managing Basic Component items and blocks. Reference objects from this class
 * to add them to your recipes and such.
 * 
 * @author Calclavia
 */

public class BasicComponents
{
	public static final String NAME = "Basic Components";
	public static final String CHANNEL = "BasicComponents";

	public static final String RESOURCE_PATH = "/mods/basiccomponents/";

	public static final CreativeTabs TAB = new TabBC(CreativeTabs.getNextID(), CHANNEL);

	public static final String TEXTURE_DIRECTORY = RESOURCE_PATH + "textures/";
	public static final String GUI_DIRECTORY = TEXTURE_DIRECTORY + "gui/";
	public static final String BLOCK_TEXTURE_DIRECTORY = TEXTURE_DIRECTORY + "blocks/";
	public static final String ITEM_TEXTURE_DIRECTORY = TEXTURE_DIRECTORY + "items/";
	public static final String MODEL_TEXTURE_DIRECTORY = TEXTURE_DIRECTORY + "models/";

	public static final String TEXTURE_NAME_PREFIX = "basiccomponents:";

	public static final String LANGUAGE_PATH = RESOURCE_PATH + "languages/";
	private static final String[] LANGUAGES_SUPPORTED = new String[] { "en_US", "zh_CN", "es_ES", "it_IT", "nl_NL", "de_DE" };

	public static int BLOCK_ID_PREFIX = 3970;

	public static Block blockBasicOre;
	public static Block blockCopperWire;
	public static Block blockMachine;

	public static final int ITEM_ID_PREFIX = 13970;
	public static ItemElectric itemBattery;
	public static Item itemInfiniteBattery;
	public static Item itemWrench;
	public static Item itemCircuit;
	public static Item itemBronzeDust;
	public static Item itemMotor;
	public static Item itemPlate;
	public static Item itemIngot;
	public static Item itemSteelDust;

	public static OreGenBase copperOreGeneration;
	public static OreGenBase tinOreGeneration;

	public static boolean INITIALIZED = false;

	/**
	 * Call this function in your mod init stage.
	 */
	public static void register()
	{
		if (!INITIALIZED)
		{
			System.out.println("Basic Components Loaded: " + TranslationHelper.loadLanguages(BasicComponents.LANGUAGE_PATH, LANGUAGES_SUPPORTED) + " Languages.");

			/**
			 * Register Recipes
			 */
			// Recipe Registry
			// Motor
			if (itemMotor != null)
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BasicComponents.itemMotor), new Object[] { "@!@", "!#!", "@!@", '!', "ingotSteel", '#', Item.ingotIron, '@', "copperWire" }));
			}
			// Wrench
			if (itemWrench != null)
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BasicComponents.itemWrench), new Object[] { " S ", " DS", "S  ", 'S', "ingotSteel", 'D', Item.diamond }));
			}
			// Battery Box
			GameRegistry.addRecipe(new ShapedOreRecipe(OreDictionary.getOres("batteryBox").get(0), new Object[] { "SSS", "BBB", "SSS", 'B', ElectricItemHelper.getUncharged(BasicComponents.itemBattery), 'S', "ingotSteel" }));
			// Coal Generator
			GameRegistry.addRecipe(new ShapedOreRecipe(OreDictionary.getOres("coalGenerator").get(0), new Object[] { "MMM", "MOM", "MCM", 'M', "ingotSteel", 'C', BasicComponents.itemMotor, 'O', Block.furnaceIdle }));
			GameRegistry.addRecipe(new ShapedOreRecipe(OreDictionary.getOres("coalGenerator").get(0), new Object[] { "MMM", "MOM", "MCM", 'M', "ingotBronze", 'C', BasicComponents.itemMotor, 'O', Block.furnaceIdle }));
			// Electric Furnace
			GameRegistry.addRecipe(new ShapedOreRecipe(OreDictionary.getOres("electricFurnace").get(0), new Object[] { "SSS", "SCS", "SMS", 'S', "ingotSteel", 'C', BasicComponents.itemCircuit, 'M', BasicComponents.itemMotor }));
			
			// Copper
			if (blockBasicOre != null)
			{
				FurnaceRecipes.smelting().addSmelting(BasicComponents.blockBasicOre.blockID, 0, OreDictionary.getOres("ingotCopper").get(0), 0.7f);
			}

			// Tin
			if (blockBasicOre != null)
			{
				FurnaceRecipes.smelting().addSmelting(BasicComponents.blockBasicOre.blockID, 1, OreDictionary.getOres("ingotTin").get(0), 0.7f);
			}
			// Copper Wire
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BasicComponents.blockCopperWire, 6), new Object[] { "!!!", "@@@", "!!!", '!', Block.cloth, '@', "ingotCopper" }));

			// Battery
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BasicComponents.itemBattery), new Object[] { " T ", "TRT", "TCT", 'T', "ingotTin", 'R', Item.redstone, 'C', Item.coal }));
			// Circuit
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BasicComponents.itemCircuit, 1, 0), new Object[] { "!#!", "#@#", "!#!", '@', "plateBronze", '#', Item.redstone, '!', "copperWire" }));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BasicComponents.itemCircuit, 1, 0), new Object[] { "!#!", "#@#", "!#!", '@', "plateSteel", '#', Item.redstone, '!', "copperWire" }));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BasicComponents.itemCircuit, 1, 1), new Object[] { "@@@", "#?#", "@@@", '@', Item.redstone, '?', Item.diamond, '#', BasicComponents.itemCircuit }));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BasicComponents.itemCircuit, 1, 2), new Object[] { "@@@", "?#?", "@@@", '@', Item.ingotGold, '?', new ItemStack(BasicComponents.itemCircuit, 1, 1), '#', Block.blockLapis }));
		}

		INITIALIZED = true;
	}

	public static ItemStack registerOres(int id, boolean require)
	{
		UniversalElectricity.CONFIGURATION.load();

		if (blockBasicOre == null)
		{
			blockBasicOre = new BlockBCOre(UniversalElectricity.CONFIGURATION.getBlock("Ore", BasicComponents.BLOCK_ID_PREFIX + 0).getInt());
			GameRegistry.registerBlock(BasicComponents.blockBasicOre, ItemBlockBCOre.class, "Ore");
		}

		if (copperOreGeneration == null)
		{
			copperOreGeneration = new OreGenReplaceStone("Copper Ore", "oreCopper", new ItemStack(BasicComponents.blockBasicOre, 1, 0), 60, 26, 4).enable(UniversalElectricity.CONFIGURATION);
			OreGenerator.addOre(BasicComponents.copperOreGeneration);
		}

		if (tinOreGeneration == null)
		{
			tinOreGeneration = new OreGenReplaceStone("Tin Ore", "oreTin", new ItemStack(BasicComponents.blockBasicOre, 1, 1), 60, 23, 4).enable(UniversalElectricity.CONFIGURATION);
			OreGenerator.addOre(BasicComponents.tinOreGeneration);
		}

		UniversalElectricity.CONFIGURATION.save();

		return new ItemStack(blockBasicOre);
	}

	public static ItemStack registerCopperWire(int id)
	{
		if (blockCopperWire == null)
		{
			UniversalElectricity.CONFIGURATION.load();
			BasicComponents.blockCopperWire = new BlockCopperWire(UniversalElectricity.CONFIGURATION.getBlock("Copper_Wire", BasicComponents.BLOCK_ID_PREFIX + 1).getInt());
			GameRegistry.registerBlock(BasicComponents.blockCopperWire, ItemBlockCopperWire.class, "Copper Wire");
			OreDictionary.registerOre("copperWire", blockCopperWire);

			UniversalElectricity.CONFIGURATION.save();
		}

		return new ItemStack(blockCopperWire);
	}

	public static ItemStack registerMachines(int id)
	{
		if (blockMachine == null)
		{
			UniversalElectricity.CONFIGURATION.load();
			BasicComponents.blockMachine = new BlockBasicMachine(UniversalElectricity.CONFIGURATION.getBlock("Basic Machine", BasicComponents.BLOCK_ID_PREFIX + 4).getInt(), 0);
			GameRegistry.registerBlock(BasicComponents.blockMachine, ItemBlockBasicMachine.class, "Basic Machine");
			OreDictionary.registerOre("coalGenerator", ((BlockBasicMachine) BasicComponents.blockMachine).getCoalGenerator());
			OreDictionary.registerOre("batteryBox", ((BlockBasicMachine) BasicComponents.blockMachine).getBatteryBox());
			OreDictionary.registerOre("electricFurnace", ((BlockBasicMachine) BasicComponents.blockMachine).getElectricFurnace());
			UniversalElectricity.CONFIGURATION.save();
		}

		return new ItemStack(blockMachine);
	}

	public static ItemStack registerCircuits(int id)
	{
		if (itemCircuit == null)
		{
			UniversalElectricity.CONFIGURATION.load();
			itemCircuit = new ItemCircuit(UniversalElectricity.CONFIGURATION.getItem("Circuit", BasicComponents.ITEM_ID_PREFIX + 3).getInt(), 16);
			OreDictionary.registerOre("basicCircuit", new ItemStack(BasicComponents.itemCircuit, 1, 0));
			OreDictionary.registerOre("advancedCircuit", new ItemStack(BasicComponents.itemCircuit, 1, 1));
			OreDictionary.registerOre("eliteCircuit", new ItemStack(BasicComponents.itemCircuit, 1, 2));
			UniversalElectricity.CONFIGURATION.save();
		}

		return new ItemStack(itemCircuit);
	}

	public static ItemStack registerBattery(int id)
	{
		if (itemBattery == null)
		{
			UniversalElectricity.CONFIGURATION.load();
			itemBattery = new ItemBattery(UniversalElectricity.CONFIGURATION.getItem("Battery", BasicComponents.ITEM_ID_PREFIX + 1).getInt());
			OreDictionary.registerOre("battery", BasicComponents.itemBattery);
			UniversalElectricity.CONFIGURATION.save();
		}

		return new ItemStack(itemBattery);
	}

	public static ItemStack registerInfiniteBattery(int id)
	{
		if (itemInfiniteBattery == null)
		{
			UniversalElectricity.CONFIGURATION.load();
			itemInfiniteBattery = new ItemInfiniteBattery(UniversalElectricity.CONFIGURATION.getItem("Infinite Battery", BasicComponents.ITEM_ID_PREFIX + 0).getInt());
			OreDictionary.registerOre("batteryInfinite", itemInfiniteBattery);
			UniversalElectricity.CONFIGURATION.save();
		}

		return new ItemStack(itemInfiniteBattery);
	}

	public static ItemStack registerWrench(int id)
	{
		if (itemWrench == null)
		{
			UniversalElectricity.CONFIGURATION.load();
			itemWrench = new ItemWrench(UniversalElectricity.CONFIGURATION.getItem("Universal Wrench", BasicComponents.ITEM_ID_PREFIX + 2).getInt(), 20);
			OreDictionary.registerOre("wrench", itemWrench);
			UniversalElectricity.CONFIGURATION.save();
		}

		return new ItemStack(itemWrench);
	}

	public static ItemStack registerMotor(int id)
	{
		if (itemMotor == null)
		{
			UniversalElectricity.CONFIGURATION.load();
			itemMotor = new ItemBasic("motor", UniversalElectricity.CONFIGURATION.getItem("Motor", BasicComponents.ITEM_ID_PREFIX + 14).getInt());
			OreDictionary.registerOre("motor", itemMotor);
			UniversalElectricity.CONFIGURATION.save();
		}

		return new ItemStack(itemMotor);
	}

	/**
	 * 
	 * @param itemName: Steel, Bronze Copper, Tin
	 * @return
	 */
	public static ItemStack registerPlates(int id, boolean require)
	{
		if (itemPlate == null)
		{
			UniversalElectricity.CONFIGURATION.load();
			itemPlate = new ItemPlate(UniversalElectricity.CONFIGURATION.getItem("Plates", BasicComponents.ITEM_ID_PREFIX + 13).getInt());
			OreDictionary.registerOre("ingotIron", Item.ingotIron);
			OreDictionary.registerOre("ingotGold", Item.ingotGold);

			for (int i = 0; i < ItemPlate.TYPES.length; i++)
			{
				String itemName = ItemPlate.TYPES[i];

				if (OreDictionary.getOres(itemName).size() <= 0 || require)
				{
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itemPlate, 1, i), new Object[] { "!!", "!!", '!', itemName.replaceAll("plate", "ingot") }));

					if (itemName.equals("ingotIron"))
					{
						GameRegistry.addRecipe(new ShapelessOreRecipe(Item.ingotIron, new Object[] { new ItemStack(itemPlate, 1, i) }));
					}
					else if (itemName.equals("ingotGold"))
					{
						GameRegistry.addRecipe(new ShapelessOreRecipe(Item.ingotGold, new Object[] { new ItemStack(itemPlate, 1, i) }));
					}

					OreDictionary.registerOre(itemName, new ItemStack(itemPlate, 1, i));
				}
			}

			UniversalElectricity.CONFIGURATION.save();
		}

		return new ItemStack(itemPlate);
	}

	public static ItemStack registerIngots(int id, boolean require)
	{
		if (BasicComponents.itemIngot == null)
		{
			UniversalElectricity.CONFIGURATION.load();
			BasicComponents.itemIngot = new ItemIngot(UniversalElectricity.CONFIGURATION.getItem("Ingots", BasicComponents.ITEM_ID_PREFIX + 4).getInt());

			for (int i = 0; i < ItemIngot.TYPES.length; i++)
			{
				String itemName = ItemIngot.TYPES[i];

				if (OreDictionary.getOres(itemName).size() <= 0 || require)
				{
					GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(itemIngot, 1, i), new Object[] { itemName.replaceAll("ingot", "plate") }));
					OreDictionary.registerOre(itemName, new ItemStack(itemIngot, 1, i));
				}
			}

			UniversalElectricity.CONFIGURATION.save();
		}

		return new ItemStack(itemIngot);
	}

	/**
	 * Call this after the corresponding ingot is registered.
	 * 
	 * @return
	 */
	public static ItemStack registerBronzeDust(int id, boolean require)
	{
		if (itemBronzeDust == null)
		{
			String itemName = "dustBronze";

			if (OreDictionary.getOres(itemName).size() <= 0 || require)
			{
				UniversalElectricity.CONFIGURATION.load();
				itemBronzeDust = new ItemBasic(itemName, UniversalElectricity.CONFIGURATION.getItem("Bronze Dust", BasicComponents.ITEM_ID_PREFIX + 8).getInt());
				OreDictionary.registerOre(itemName, itemBronzeDust);

				RecipeHelper.addRecipe(new ShapedOreRecipe(new ItemStack(BasicComponents.itemBronzeDust), new Object[] { "!#!", '!', "ingotCopper", '#', "ingotTin" }), "Bronze Dust", UniversalElectricity.CONFIGURATION, true);

				if (OreDictionary.getOres("ingotBronze").size() > 0)
				{
					// Bronze
					GameRegistry.addSmelting(BasicComponents.itemBronzeDust.itemID, OreDictionary.getOres("ingotBronze").get(0), 0.6f);
				}

				UniversalElectricity.CONFIGURATION.save();
			}

		}

		return new ItemStack(itemBronzeDust);
	}

	/**
	 * Call this after the corresponding ingot is registered.
	 * 
	 * @return
	 */
	public static ItemStack registerSteelDust(int id, boolean require)
	{
		if (itemSteelDust == null)
		{
			String itemName = "dustSteel";

			if (OreDictionary.getOres(itemName).size() <= 0 || require)
			{
				UniversalElectricity.CONFIGURATION.load();

				itemSteelDust = new ItemBasic(itemName, UniversalElectricity.CONFIGURATION.getItem("Steel Dust", BasicComponents.ITEM_ID_PREFIX + 9).getInt());
				OreDictionary.registerOre(itemName, itemSteelDust);
				RecipeHelper.addRecipe(new ShapedOreRecipe(new ItemStack(BasicComponents.itemSteelDust), new Object[] { " C ", "CIC", " C ", 'C', new ItemStack(Item.coal, 1, 1), 'I', Item.ingotIron }), "Steel Dust", UniversalElectricity.CONFIGURATION, true);
				RecipeHelper.addRecipe(new ShapedOreRecipe(new ItemStack(BasicComponents.itemSteelDust), new Object[] { " C ", "CIC", " C ", 'C', new ItemStack(Item.coal, 1, 0), 'I', Item.ingotIron }), "Steel Dust", UniversalElectricity.CONFIGURATION, true);

				if (OreDictionary.getOres("ingotSteel").size() > 0)
				{
					GameRegistry.addSmelting(BasicComponents.itemSteelDust.itemID, OreDictionary.getOres("ingotSteel").get(0), 0.8f);
				}

				UniversalElectricity.CONFIGURATION.save();
			}

		}

		return new ItemStack(itemBronzeDust);
	}
}

package openccsensors.common.helper;

import java.lang.reflect.Field;

import openccsensors.common.items.ItemSensorCard;
import cpw.mods.fml.common.Loader;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BCHelper {
	public static Item getStoneGear() {
		Item gear = null;
		try {
			Class buildcraftCore = Class.forName("buildcraft.BuildCraftCore");
			if (buildcraftCore != null) {
				Field gearField = buildcraftCore.getDeclaredField("stoneGearItem");
				gear = (Item) gearField.get(buildcraftCore);
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
		return gear;
	}

	public static void addTier1CardRecipe() {

		Item bcItem = BCHelper.getStoneGear();
		if (bcItem == null) {
			bcItem = Item.coal;
		}
		RecipeHelper.addTier1CardRecipe(ItemSensorCard.BUILDCRAFT_TIER_1, new ItemStack(bcItem));
	
	}
}

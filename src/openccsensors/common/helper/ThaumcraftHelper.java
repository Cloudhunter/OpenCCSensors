package openccsensors.common.helper;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import openccsensors.common.items.ItemSensorCard;
import cpw.mods.fml.common.Loader;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ThaumcraftHelper {
	
	public static Item getGoggles() {

		Item goggles = null;
		try {
			Class thaumcraftConfig = Class.forName("thaumcraft.common.Config");
			if (thaumcraftConfig != null) {
				Field gogglesField = thaumcraftConfig
						.getDeclaredField("itemGoggles");
				goggles = (Item) gogglesField.get(thaumcraftConfig);
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
		return goggles;
	}

	public static void addTier1CardRecipe() {
		Item tcItem = ThaumcraftHelper.getGoggles();
		if (tcItem == null) {
			tcItem = Item.eyeOfEnder;
		}
		RecipeHelper.addTier1CardRecipe(ItemSensorCard.THAUMCRAFT_TIER_1, new ItemStack(tcItem));
	
	}

}

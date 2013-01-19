package openccsensors.common.helper;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import net.minecraft.item.Item;
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

}

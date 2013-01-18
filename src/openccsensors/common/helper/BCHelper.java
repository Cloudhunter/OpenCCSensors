package openccsensors.common.helper;

import java.lang.reflect.Field;

import net.minecraft.item.Item;

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
}

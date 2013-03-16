package openccsensors.common.helper;

import net.minecraft.item.ItemStack;
import openccsensors.common.items.ItemSensorCard;

public class AppliedEnergisticsHelper {
	public static void addTier1CardRecipe() {	
		try {
			ItemStack terminal = appeng.api.Materials.matQuartz.copy();
			
			
			if(terminal != null) {
				RecipeHelper.addTier1CardRecipe(ItemSensorCard.APPLIEDENERGISTICS_TIER_1, terminal);
			}
		}catch(Exception e) {
			
		}
	}
}

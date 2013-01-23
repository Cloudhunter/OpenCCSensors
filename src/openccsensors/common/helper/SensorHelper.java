package openccsensors.common.helper;

import openccsensors.common.core.OCSLog;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class SensorHelper {

	public static String getType(String rawType) {
		String translated = LanguageRegistry.instance().getStringLocalization(
				rawType, "en_OCS");
		return translated == "" ? rawType : translated;
	}
	
	public static String getRawType(TileEntity entity) {
		String rawType = entity.getClass().getName();
		Block b = entity.getBlockType();
		if (b != null) {
			rawType = b.getBlockName();
		}
		return rawType.toLowerCase().trim();
	}
	
	public static String getDisplayType(TileEntity entity) {
		ItemStack stack = new ItemStack(entity.getBlockType());
		return InventoryHelper.getNameForItemStack(stack);
	}
	

}

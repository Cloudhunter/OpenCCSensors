package openccsensors.common.helper;

import openccsensors.common.core.OCSLog;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class SensorHelper {

	public static String getType(String rawType) {
		String translated = LanguageRegistry.instance().getStringLocalization(rawType);
		return translated == "" ? rawType : translated;
	}
	
	public static String getRawType(TileEntity entity) {
		ItemStack stack = new ItemStack(entity.getBlockType(), 1, entity.getBlockMetadata());
		
		String rawName = entity.getClass().getName().toLowerCase();
		try {
			rawName = stack.getItemName().toLowerCase();
		}catch(Exception e) {			
		}
		String packageName = entity.getClass().getName().toLowerCase();
		String[] packageLevels = packageName.split("\\.");
		if (!rawName.startsWith(packageLevels[0])) {
			rawName = packageLevels[0] + "." + rawName;
		}
		
		return rawName;
	}
	
	public static String getDisplayType(TileEntity entity) {
		ItemStack stack = new ItemStack(entity.getBlockType(), 1, entity.getBlockMetadata());
		return InventoryHelper.getNameForItemStack(stack);
	}
	

}

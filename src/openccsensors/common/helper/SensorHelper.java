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
		return InventoryHelper.getRawNameForStack(stack);
	}
	
	public static String getDisplayType(TileEntity entity) {
		ItemStack stack = new ItemStack(entity.getBlockType(), 1, entity.getBlockMetadata());
		return InventoryHelper.getNameForItemStack(stack);
	}
	

}

package forestry.api.farming;

import java.util.Collection;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;

public interface IFarmLogic {

	int getFertilizerConsumption();
	int getWaterConsumption(float hydrationModifier);
	boolean isAcceptedResource(ItemStack itemstack);
	boolean isAcceptedGermling(ItemStack itemstack);

	Collection<ItemStack> collect();

	boolean cultivate(int x, int y, int z, ForgeDirection direction, int extent);

	Collection<ICrop> harvest(int x, int y, int z, ForgeDirection direction, int extent);
	
	int getIconIndex();
	String getTextureFile();

	String getName();
}

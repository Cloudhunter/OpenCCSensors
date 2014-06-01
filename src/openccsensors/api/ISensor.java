package openccsensors.api;

import java.util.HashMap;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public interface ISensor {
	HashMap getDetails(World world, Object obj, ChunkCoordinates location, boolean additional);
	HashMap getTargets(World world, ChunkCoordinates location, ISensorTier tier);
	String[] getCustomMethods(ISensorTier tier);
	Object callCustomMethod(World world, ChunkCoordinates location, int methodID, Object[] args, ISensorTier tier) throws Exception;
	String getName();
	Icon getIcon();
	ItemStack getUniqueRecipeItem();
}

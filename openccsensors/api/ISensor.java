package openccsensors.api;

import java.util.HashMap;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public interface ISensor {
	HashMap getDetails(World world, Object obj, boolean additional);
	HashMap getTargets(World world, Vec3 location, ISensorTier tier);
	String[] getCustomMethods(ISensorTier tier);
	Object callCustomMethod(World world, Vec3 location, int methodID, Object[] args, ISensorTier tier);
	String getName();
	Icon getIcon();
	ItemStack getUniqueRecipeItem();
}

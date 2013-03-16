package forestry.api.arboriculture;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IToolGrafter {
	float getSaplingModifier(ItemStack stack, World world, int x, int y, int z);
}

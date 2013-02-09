package forestry.api.apiculture;

import java.util.HashMap;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import forestry.api.genetics.IAlleleSpecies;

public interface IAlleleBeeSpecies extends IAlleleSpecies {

	// / Products, Chance
	HashMap<ItemStack, Integer> getProducts();

	// / Specialty, Chance
	HashMap<ItemStack, Integer> getSpecialty();

	// / Only jubilant bees give their specialty product
	boolean isJubilant(World world, int biomeid, int x, int y, int z);

}

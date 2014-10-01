/**
 * Copyright (c) 2011-2014, SpaceToad and the BuildCraft Team
 * http://www.mod-buildcraft.com
 *
 * BuildCraft is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package buildcraft.api.recipes;

import java.util.Collection;

import net.minecraft.item.ItemStack;

public interface IAssemblyRecipeManager {

	/**
	 * Add an Assembly Table recipe.
	 *
	 * @param input
	 *            Object... containing either an ItemStack, or a paired string
	 *            and integer(ex: "dyeBlue", 1)
	 * @param energyCost
	 *            RF cost to produce
	 * @param output
	 *            resulting ItemStack
	 */
	void addRecipe(String id, int energyCost, ItemStack output, Object... input);

	void addRecipe(IFlexibleRecipe<ItemStack> recipe);

	Collection<IFlexibleRecipe<ItemStack>> getRecipes();
}

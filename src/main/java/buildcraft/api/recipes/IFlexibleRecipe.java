/**
 * Copyright (c) 2011-2014, SpaceToad and the BuildCraft Team
 * http://www.mod-buildcraft.com
 *
 * BuildCraft is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package buildcraft.api.recipes;

import net.minecraft.item.ItemStack;

public interface IFlexibleRecipe<T> {

	boolean canBeCrafted(IFlexibleCrafter crafter);

	CraftingResult<T> craft(IFlexibleCrafter crafter, boolean preview);

	CraftingResult<T> canCraft(ItemStack expectedOutput);

	String getId();
}

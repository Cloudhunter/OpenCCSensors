/**
 * Copyright (c) 2011-2014, SpaceToad and the BuildCraft Team
 * http://www.mod-buildcraft.com
 *
 * BuildCraft is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package buildcraft.api.gates;

import java.util.Collection;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

import buildcraft.api.transport.IPipeTile;

public interface ITriggerProvider {

	/**
	 * Returns the list of triggers that are available from the pipe holding the gate.
	 */
	Collection<ITrigger> getPipeTriggers(IPipeTile pipe);

	/**
	 * Returns the list of triggers available to a gate next to the given block.
	 */
	Collection<ITrigger> getNeighborTriggers(Block block, TileEntity tile);

}

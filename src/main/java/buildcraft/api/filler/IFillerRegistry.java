/**
 * Copyright (c) 2011-2014, SpaceToad and the BuildCraft Team
 * http://www.mod-buildcraft.com
 *
 * BuildCraft is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package buildcraft.api.filler;

import java.util.Set;

import buildcraft.api.gates.IAction;

public interface IFillerRegistry {

	void addPattern(IFillerPattern pattern);

	IFillerPattern getPattern(String patternName);

	IFillerPattern getNextPattern(IFillerPattern currentPattern);

	IFillerPattern getPreviousPattern(IFillerPattern currentPattern);

	Set<? extends IAction> getActions();
}

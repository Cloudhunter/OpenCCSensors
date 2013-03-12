package openccsensors.common.sensors.targets.appliedenergistics;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import appeng.api.me.tiles.IGridMachine;
import appeng.api.me.util.IGridInterface;
import appeng.api.me.util.IMEInventoryHandler;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.sensors.targets.TileSensorTarget;

public class WirelessTarget extends TileSensorTarget implements ISensorTarget {

	public WirelessTarget(TileEntity targetEntity, int relativeX, int relativeY,
			int relativeZ) {
		super(targetEntity, relativeX, relativeY, relativeZ);
	}	
	
	@Override
	public HashMap getExtendedDetails(World world) {
		HashMap retMap = getBasicDetails(world);

		HashMap<String, Integer> stacks = new HashMap<String, Integer>();
				
		IGridMachine entity = (IGridMachine)world.getBlockTileEntity(xCoord, yCoord, zCoord);
		IGridInterface gi = entity.getGrid();
		IMEInventoryHandler imivh = gi.getFullCellArray();
		
		int totalCount = 0;
		List<ItemStack> list = imivh.getAvailableItems(new ArrayList());
		for (ItemStack l : list) {
			String name = l.getDisplayName();
			Integer count = l.stackSize;
			totalCount += count;
			stacks.put(name, count);
		}

		retMap.put("GridIndex", gi.getGridIndex());
		retMap.put("FreeTypes", (int)imivh.remainingItemTypes());
		retMap.put("FreeCount", (int)imivh.remainingItemCount());
		retMap.put("FreeBytes", (int)imivh.freeBytes());
		retMap.put("UsedTypes", list.size());
		retMap.put("UsedCount", totalCount);
		retMap.put("UsedBytes", (int)imivh.usedBytes());
		
		retMap.put("Items", stacks);
		return retMap;
	}

	@Override
	public String[] getTrackablePropertyNames() {
		return null;
	}

}

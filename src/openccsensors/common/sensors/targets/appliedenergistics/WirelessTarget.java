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
import openccsensors.common.helper.InventoryHelper;
import openccsensors.common.sensors.targets.TileSensorTarget;

public class WirelessTarget extends TileSensorTarget implements ISensorTarget {

	public WirelessTarget(TileEntity targetEntity, int relativeX, int relativeY,
			int relativeZ) {
		super(targetEntity, relativeX, relativeY, relativeZ);
	}	
	
	@Override
	public HashMap getExtendedDetails(World world) {
		HashMap retMap = getBasicDetails(world);
		
		IGridMachine entity = (IGridMachine)world.getBlockTileEntity(xCoord, yCoord, zCoord);
		retMap.put("Powered", false);
		
		if(!entity.isValid()) {
			return retMap;
		}
		
		IGridInterface gi = entity.getGrid();
		if(gi == null) {			
			return retMap;
		}
		
		IMEInventoryHandler imivh = gi.getFullCellArray();		
		retMap.put("GridIndex", gi.getGridIndex());
		
		if(entity.isPowered()) {
			retMap.put("Powered", true);
			
			retMap.put("FreeTypes", (int)imivh.remainingItemTypes());
			retMap.put("FreeCount", (int)imivh.remainingItemCount());
			retMap.put("FreeBytes", (int)imivh.freeBytes());
			retMap.put("UsedBytes", (int)imivh.usedBytes());
						
			List<ItemStack> list = imivh.getAvailableItems(new ArrayList());
			
			int totalCount = 0;
			HashMap stacks = new HashMap();
			for (int i = 0; i < list.size(); i++) {
				ItemStack items = list.get(i);
				stacks.put(i + 1, InventoryHelper.itemstackToMap(items));
				
				Integer count = items.stackSize;
				totalCount += count;				
			}
			
			retMap.put("UsedTypes", list.size());
			retMap.put("UsedCount", totalCount);			
			
			retMap.put("Items", stacks);
		}
		
		return retMap;
	}

	@Override
	public String[] getTrackablePropertyNames() {
		return null;
	}

}

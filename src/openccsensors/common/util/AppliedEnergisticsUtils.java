package openccsensors.common.util;

import java.util.HashMap;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import appeng.api.me.tiles.IGridMachine;
import appeng.api.me.tiles.IGridTileEntity;
import appeng.api.me.util.IGridInterface;
import appeng.api.me.util.IMEInventory;
import appeng.api.me.util.IMEInventoryHandler;

public class AppliedEnergisticsUtils {

	private static final String ME_WIRELESS_CLASS = "appeng.me.tile.TileWireless";
	
	public static boolean isValidTarget(Object target) {
		return target != null && target.getClass().getName() == ME_WIRELESS_CLASS;
	}
	
	public static HashMap getTileDetails(Object obj, boolean additional) {
		HashMap response = new HashMap();
		
		if (!(obj instanceof TileEntity)) {
			return response;
		}

		TileEntity aeWirelessAPtileEntity = (TileEntity) obj;
		response.put("Powered", false);

		IGridTileEntity aeMachine = (IGridTileEntity) aeWirelessAPtileEntity;
		IGridInterface gi = aeMachine.getGrid();
		if (gi != null && gi.isValid() && aeMachine.isPowered()) {

			IMEInventoryHandler imivh = gi.getFullCellArray();
			IMEInventory imiv = (IMEInventory) imivh;
			response.put("Powered",true);
			
			//user IMEInventory for remaining item types and count.
			response.put("FreeTypes", (int) imiv.remainingItemTypes());
			response.put("FreeCount", (int) imiv.remainingItemCount());
			response.put("FreeBytes", (int) imivh.freeBytes());
			response.put("UsedBytes", (int) imivh.usedBytes());
			response.put("TotalBytes", (int) imivh.totalBytes());
			
			long totalBytes = imivh.totalBytes();
			
			double percent = (double)100 / totalBytes * imivh.usedBytes();
			percent = Math.max(Math.min(percent, 100), 0);
			response.put("InventoryPercentFull", percent);
			
			if (additional) {
			
				List<ItemStack> list = imiv.getAvailableItems().getItems();
				int totalCount = 0;
				HashMap stacks = new HashMap();
				for (int i = 0; i < list.size(); i++) {
					ItemStack items = list.get(i);
					stacks.put(i + 1, InventoryUtils.itemstackToMap(items));
					Integer count = items.stackSize;
					totalCount += count;				
				}
				
				response.put("UsedTypes", list.size());
				response.put("UsedCount", totalCount);
				response.put("Slots", stacks);
				
				response.put("CanHoldNewItems", imivh.canHoldNewItem());
				response.put("Priority", imivh.getPriority());
				response.put("SystemPower", gi.getPowerUsageAvg());
			}
			gi.triggerPowerUpdate();

		}
		
		return response;
	}
}

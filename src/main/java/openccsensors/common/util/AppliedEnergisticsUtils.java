package openccsensors.common.util;

import java.util.HashMap;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import appeng.api.AEApi;
import appeng.api.networking.IGridBlock;
import appeng.api.networking.IGridNode;
import appeng.api.networking.energy.IEnergyGrid;
import appeng.api.storage.ICellInventory;
import appeng.api.storage.IMEInventory;
import appeng.api.storage.IMEInventoryHandler;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IAEStack;
import appeng.api.storage.data.IItemList;

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

		IGridBlock aeMachine = (IGridBlock) aeWirelessAPtileEntity;
		IGridNode gi = aeMachine.getMachine().getGridNode(ForgeDirection.UNKNOWN);
		if (aeMachine instanceof IEnergyGrid && ((IEnergyGrid)aeMachine).isNetworkPowered() && aeMachine instanceof IMEInventoryHandler) {

			IMEInventoryHandler imivh = (IMEInventoryHandler) aeMachine;
			IMEInventory imiv = (IMEInventory) imivh;
			IEnergyGrid eGrid = (IEnergyGrid) aeMachine;
			response.put("Powered",true);
			
			if (imiv instanceof ICellInventory) {
				//uses ICellInventory for remaining item types and count.
				ICellInventory cells = (ICellInventory) imiv;
				response.put("FreeTypes", (int) cells.getRemainingItemTypes());
				response.put("FreeCount", (int) cells.getRemainingItemCount());
				response.put("FreeBytes", (int) cells.getFreeBytes());
				long usedBytes = cells.getUsedBytes();
				long totalBytes = cells.getTotalBytes();
				response.put("UsedBytes", (int) usedBytes);
				response.put("TotalBytes", (int) totalBytes);
			
				
			
				double percent = (double)100 / totalBytes * usedBytes;
				percent = Math.max(Math.min(percent, 100), 0);
				response.put("InventoryPercentFull", percent);
				response.put("CanHoldNewItems", cells.canHoldNewItem());
			}
			
			if (additional) {
				IItemList list = AEApi.instance().storage().createItemList();
				imiv.getAvailableItems(list);
				int totalCount = 0;
				int i = 0;
				HashMap stacks = new HashMap();
				Iterable<IAEStack> iterator = (Iterable<IAEStack>)list.iterator();
				for (IAEStack stack : iterator) {
					if (stack.isItem()) {
						IAEItemStack itemStack = (IAEItemStack) stack;
						ItemStack items = itemStack.getItemStack();
						stacks.put(i++, InventoryUtils.itemstackToMap(items));
						totalCount += items.stackSize;
					}
				};
				
				response.put("UsedTypes", list.size());
				response.put("UsedCount", totalCount);
				response.put("Slots", stacks);
				
				
				response.put("Priority", imivh.getPriority());
				response.put("SystemPower", eGrid.getAvgPowerUsage());
			}

		}
		
		return response;
	}
}

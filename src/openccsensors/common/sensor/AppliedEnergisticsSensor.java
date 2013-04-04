package openccsensors.common.sensor;

import java.util.HashMap;
import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import openccsensors.api.IRequiresIconLoading;
import openccsensors.api.ISensor;
import openccsensors.api.ISensorTier;
import openccsensors.common.util.InventoryUtils;
import openccsensors.common.util.OCSLog;
import appeng.api.me.tiles.IGridMachine;
import appeng.api.me.util.IGridInterface;
import appeng.api.me.util.IMEInventoryHandler;

public class AppliedEnergisticsSensor extends TileSensor implements ISensor, IRequiresIconLoading {

	private Icon icon;
	private static final String ME_WIRELESS_CLASS = "appeng.me.tile.TileWireless";
	
	@Override
	public boolean isValidTarget(Object target) {
		if (!(target instanceof TileEntity)) return false;
		if (((TileEntity)target).getClass().getName() != ME_WIRELESS_CLASS) return false;
		return true;
	}
	
	@Override
	public void loadIcon(IconRegister iconRegistry) {
		icon = iconRegistry.registerIcon("openccsensors:appliedEnergistics");
	}

	@Override
	public HashMap getDetails(World world, Object obj, boolean additional) {
		
		TileEntity aeWirelessAPtileEntity = (TileEntity) obj;

		HashMap response = super.getDetails(aeWirelessAPtileEntity);
		
		if (additional) {
			response.put("Powered", false);
			
			IGridMachine aeMachine = (IGridMachine) aeWirelessAPtileEntity;
			IGridInterface gi = aeMachine.getGrid();
			if (gi == null || !gi.isValid()) {
				return response;
			}
			
			if (aeMachine.isPowered()) {
			
				response.put("Powered",true);
				
				IMEInventoryHandler imivh = gi.getFullCellArray();
			
				response.put("FreeTypes", (int) imivh.remainingItemTypes());
				response.put("FreeCount", (int) imivh.remainingItemCount());
				response.put("FreeBytes", (int) imivh.freeBytes());
				response.put("UsedBytes", (int) imivh.usedBytes());
	
				List<ItemStack> list = imivh.getAvailableItems().getItems();
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
				response.put("Items", stacks);
				
				response.put("CanHoldNewItems", imivh.canHoldNewItem());
				response.put("Priority", imivh.getPriority());
				response.put("SystemPower", gi.getPowerUsageAvg());
				gi.triggerPowerUpdate();
			}
			
		}
		
		return response;
	}

	@Override
	public String[] getCustomMethods(ISensorTier tier) {
		return null;
	}

	@Override
	public Object callCustomMethod(World world, Vec3 location, int methodID,
			Object[] args, ISensorTier tier) throws Exception {
		return null;
	}

	@Override
	public String getName() {
		return "appliedEnergisticsCard";
	}

	@Override
	public Icon getIcon() {
		return icon;
	}

	@Override
	public ItemStack getUniqueRecipeItem() {
		return new ItemStack(Item.slimeBall);
	}

}

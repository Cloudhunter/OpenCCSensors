package openccsensors.common.util;

import java.util.HashMap;

import net.minecraft.item.ItemStack;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidStack;

import openccsensors.common.util.InventoryUtils;

public class TankUtils {

public HashMap tankContainerToMap(ITankContainer container) {
		
		ILiquidTank[] tanks = container.getTanks(ForgeDirection.UNKNOWN);
		
		HashMap allTanks = new HashMap();
		int i = 0;
		try {
			if (tanks != null) {
				for (ILiquidTank tank : tanks) {
					
					HashMap tankMap = new HashMap();
					tankMap.put("Capacity", tank.getCapacity());
					tankMap.put("Amount", 0);
					
					LiquidStack stack = tank.getLiquid();

					if (stack != null) {
						ItemStack istack = stack.asItemStack();
						if (istack != null) {
							if (istack.getItem() != null) {
								tankMap.put("Name", InventoryUtils.getNameForItemStack(istack));
								tankMap.put("RawName", InventoryUtils.getRawNameForStack(istack));
								tankMap.put("Amount", stack.amount);
							}
						}
					}
					allTanks.put(++i, tankMap);
				}
			}
		}catch(Exception e) {}
		return allTanks;
	}

}
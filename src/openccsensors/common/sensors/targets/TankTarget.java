package openccsensors.common.sensors.targets;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidStack;
import openccsensors.common.api.ISensorTarget;

public class TankTarget implements ISensorTarget {

	private ILiquidTank[] tanks = null;
	private int relativeX;
	private int relativeY;
	private int relativeZ;
	
	public TankTarget(ILiquidTank[] tanks, int relativeX, int relativeY,
			int relativeZ) {
		this.tanks = tanks;
		this.relativeX = relativeX;
		this.relativeY = relativeY;
		this.relativeZ = relativeZ;
	}

	@Override
	public HashMap getExtendedDetails(World world) {
		HashMap retMap = getBasicDetails(world);
		LiquidStack stack;
		ItemStack istack;
		Item item;
		Map tankProperties;
		int i = 0;
		if (tanks != null) {
			for (ILiquidTank tank : tanks) {
				tankProperties = new HashMap();
				tankProperties.put("Capacity", tank.getCapacity());
	
				stack = tank.getLiquid();
	
				if (stack != null) {
					istack = stack.asItemStack();
					if (istack != null) {
						if (istack.getItem() != null) {
							tankProperties.put("Name", istack.getDisplayName());
							tankProperties.put("Amount", stack.amount);
	
						}
					}
				}
				if (!tankProperties.containsKey("Amount")) {
					tankProperties.put("Amount", 0);
				}
	
				retMap.put(i, tankProperties);
				i++;
			}
		}
		return retMap;
	}

	@Override
	public String[] getTrackablePropertyNames() {
		return null;
	}

	@Override
	public HashMap getBasicDetails(World world) {
		return new HashMap();
	}


}

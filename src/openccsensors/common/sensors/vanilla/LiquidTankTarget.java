package openccsensors.common.sensors.vanilla;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidStack;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.core.OCSLog;
import openccsensors.common.sensors.TileSensorTarget;

public class LiquidTankTarget extends TileSensorTarget implements ISensorTarget{

	protected LiquidTankTarget(TileEntity targetEntity) {
		super(targetEntity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Map getDetailInformation(World world) {
		ITankContainer tankContainer = (ITankContainer) world.getBlockTileEntity(xCoord,
				yCoord, zCoord);

		HashMap retMap = new HashMap();
		
		ILiquidTank[] tanks = tankContainer.getTanks(ForgeDirection.UNKNOWN);
		
		int capacity = 0;
		int amount = 0;
		LiquidStack stack = null;
		
		for (ILiquidTank tank : tanks)
		{
			capacity += tank.getCapacity();
			amount += tank.getLiquid().amount;
			if (stack == null)
			{
				stack = tank.getLiquid();
			}
		}
		ItemStack istack = stack.asItemStack();
		retMap.put("Amount",  amount);
		retMap.put("Capacity",  capacity);
		retMap.put("Name",  istack.getItem().getItemNameIS(istack));
		
		
		return retMap;
	}

}

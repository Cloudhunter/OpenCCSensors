package openccsensors.common.sensors.vanilla;

import java.util.ArrayList;
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
		LiquidStack stack;
		ItemStack istack;
		Item item;
		Map tankProperties;
		int i = 0;
		for (ILiquidTank tank : tanks)
		{
			tankProperties = new HashMap();
			tankProperties.put("Capacity",  tank.getCapacity());
			
			stack = tank.getLiquid();
			
			if (stack != null)
			{
				istack = stack.asItemStack();
				if (istack != null)
				{
					if (istack.getItem() != null)
					{
						tankProperties.put("Name",  istack.getDisplayName());
						tankProperties.put("Amount",  stack.amount);
				
					}
				}
			}
			if (!tankProperties.containsKey("Amount"))
			{
				tankProperties.put("Amount", 0);
			}
			
			retMap.put(i, tankProperties);
			i++;
		}

		return retMap;
	}

}

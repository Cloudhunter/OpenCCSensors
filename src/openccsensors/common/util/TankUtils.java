package openccsensors.common.util;

import java.util.HashMap;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidStack;

import openccsensors.common.util.InventoryUtils;

public class TankUtils {

	public static HashMap fluidHandlerToMap(IFluidHandler container) {
		
		FluidTankInfo[] tanks = container.getTankInfo(ForgeDirection.UNKNOWN);
		
		HashMap allTanks = new HashMap();
		int i = 0;
		try {
			if (tanks != null) {
				for (FluidTankInfo tank : tanks) {
					
					HashMap tankMap = new HashMap();
					tankMap.put("Capacity", tank.capacity);
					tankMap.put("Amount", 0);
					
					FluidStack stack = tank.fluid;

					if (stack != null) {
						Fluid fluid = stack.getFluid();
						tankMap.put("Name", FluidRegistry.getFluidName(stack));
						tankMap.put("RawName", fluid.getUnlocalizedName());
						tankMap.put("Amount", stack.amount);
						tankMap.put("Luminousity", fluid.getLuminosity());
						tankMap.put("Temperature", fluid.getTemperature());
						tankMap.put("Viscosity", fluid.getViscosity());
						tankMap.put("IsGaseous", fluid.isGaseous());
					}
					allTanks.put(++i, tankMap);
				}
			}
		}catch(Exception e) {}
		return allTanks;
	}

}
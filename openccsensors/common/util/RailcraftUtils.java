package openccsensors.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.liquids.ILiquidTank;

public class RailcraftUtils {

	public static String ITANKTILE_NAME = "railcraft.common.blocks.machine.ITankTile";

	public static Class tankTile = null;
	
	public static boolean isTankTile(TileEntity tile) {
		return getTankIfTankTile(tile) != null;
	}


	public static ILiquidTank getTankIfTankTile(TileEntity entity)
	{
		if (tankTile == null) {
			try {
				tankTile = Class.forName(ITANKTILE_NAME);
			} catch (ClassNotFoundException e) {
			}
		}

		ILiquidTank tank = null;
		if (tankTile != null) {
			if (tankTile.isAssignableFrom(entity.getClass())) {
				Method tankMethod = null;
				try {
					tankMethod = tankTile.getMethod("getTank");
					if (tankMethod != null) {
						tank = (ILiquidTank)tankMethod.invoke(entity);
					}
				} catch (Exception e) {
				}
			}
		}

		return tank;
	}
}

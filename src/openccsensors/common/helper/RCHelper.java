package openccsensors.common.helper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.liquids.ILiquidTank;

public class RCHelper {

	public static String ITANKTILE_NAME = "railcraft.common.blocks.machine.ITankTile";
	
	public static Class tankTile = null;
	
	public static ILiquidTank getTankIfTankTile(TileEntity entity)
	{
		if (tankTile == null) {
			try {
				tankTile = Class.forName(ITANKTILE_NAME);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		ILiquidTank tank = null;
		if (tankTile != null) {
			if (tankTile.isAssignableFrom(entity.getClass())) {
				Item goggles = null;
				Method tankMethod = null;
				try {
					tankMethod = tankTile.getMethod("getTank");
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
				if (tankMethod != null) {
					try {
						tank = (ILiquidTank)tankMethod.invoke(entity);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return tank;
	}
}

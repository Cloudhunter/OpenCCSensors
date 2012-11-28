package openccsensors.common.sensors.vanilla;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.IInventory;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import openccsensors.common.core.ISensorTarget;
import openccsensors.common.helper.InventoryHelper;
import openccsensors.common.sensors.TileSensorTarget;



public class InventoryTarget extends TileSensorTarget implements
		ISensorTarget {

	protected InventoryTarget(TileEntity targetEntity) {
		super(targetEntity);
	}

	@Override
	public Map getDetailInformation(World world) {
		HashMap retMap = new HashMap();
		TileEntity tile = world.getBlockTileEntity(xCoord, yCoord, zCoord);
		if (tile == null || !(tile instanceof IInventory)) {
			return null;
		}

		return InventoryHelper.invToMap((IInventory) tile);
	}

}
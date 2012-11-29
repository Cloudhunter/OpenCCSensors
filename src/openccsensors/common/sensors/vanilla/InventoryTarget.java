package openccsensors.common.sensors.vanilla;

import java.util.Map;

import net.minecraft.src.IInventory;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.helper.InventoryHelper;
import openccsensors.common.sensors.TileSensorTarget;

public class InventoryTarget extends TileSensorTarget implements ISensorTarget {

	protected InventoryTarget(TileEntity targetEntity) {
		super(targetEntity);
	}

	@Override
	public Map getDetailInformation(World world) {

		IInventory inventory = (IInventory) world.getBlockTileEntity(xCoord,
				yCoord, zCoord);

		return InventoryHelper.invToMap(inventory);

	}

}
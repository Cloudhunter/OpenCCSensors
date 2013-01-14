package openccsensors.common.sensors.vanilla;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.helper.InventoryHelper;
import openccsensors.common.sensors.TileSensorTarget;

public class InventoryTarget extends TileSensorTarget implements ISensorTarget {

	protected InventoryTarget(TileEntity targetEntity, int relativeX, int relativeY, int relativeZ) {
		super(targetEntity, relativeX, relativeY, relativeZ);
	}

	@Override
	public Map getDetailInformation(World world) {

		IInventory inventory = (IInventory) world.getBlockTileEntity(xCoord,
				yCoord, zCoord);
		HashMap items = new HashMap();
		items.put("Items", InventoryHelper.invToMap(inventory));
		return items;

	}

	@Override
	public boolean hasGaugePercentage() {
		return false;
	}

	@Override
	public double getGaugePercentage(World world) {
		return 0;
	}
}
package openccsensors.common.sensors.vanilla;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.src.IInventory;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import openccsensors.common.core.ISensorInterface;
import openccsensors.common.core.ISensorTarget;
import openccsensors.common.helper.BlockTileHelper;
import openccsensors.common.sensors.GenericSensorInterface;

public class InventorySensorInterface extends GenericSensorInterface implements
		ISensorInterface {

	Class[] relevantClassTypes = { IInventory.class };

	@Override
	public String getName() {
		return "inventory";
	}

	@Override
	public HashMap<String, ISensorTarget> getAvailableTargets(World world,
			int x, int y, int z) {
		HashMap<String, ISensorTarget> targets = new HashMap<String, ISensorTarget>();

		HashMap<String, TileEntity> entities = BlockTileHelper.getAdjacentTile(
				world, x, y, z, relevantClassTypes);

		BlockTileHelper.addToHashMap(world.getBlockTileEntity(x, y, z),
				entities, relevantClassTypes, "SELF");

		Iterator it = entities.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry<String, TileEntity> pairs = (Entry<String, TileEntity>) it
					.next();
			targets.put(pairs.getKey(), new InventoryTarget(pairs.getValue()));
		}

		return targets;
	}

	@Override
	public String[] getMethods() {
		return null;
	}

	@Override
	public Object[] callMethod(int methodID, Object[] args) throws Exception {
		return null;
	}

}
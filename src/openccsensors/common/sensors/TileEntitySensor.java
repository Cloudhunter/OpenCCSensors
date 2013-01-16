package openccsensors.common.sensors;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import openccsensors.common.api.ITileEntityValidatorCallback;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.api.SensorUpgrade;

public abstract class TileEntitySensor {
ArrayList<ITileEntityValidatorCallback> validatorCallbacks = new ArrayList<ITileEntityValidatorCallback>();
	
	public void registerCallback(ITileEntityValidatorCallback wrapper) {
		validatorCallbacks.add(wrapper);
	}
	
	public HashMap<String, ArrayList<ISensorTarget>> getSurroundingTargets(
			World world, int sx, int sy, int sz, SensorUpgrade upgrade) {
		
		HashMap<String, ArrayList<ISensorTarget>> map = new HashMap<String, ArrayList<ISensorTarget>>();
		int distance = 5;
		for (int x = -distance; x <= distance; x++) {
			for (int y = -distance; y <= distance; y++) {
				for (int z = -distance; z <= distance; z++) {

					int tileX = x + sx;
					int tileY = y + sy;
					int tileZ = z + sz;

					TileEntity entity = world.getBlockTileEntity(tileX, tileY, tileZ);

					if (entity != null) {

						for (ITileEntityValidatorCallback wrapper : validatorCallbacks) {

							ISensorTarget target = wrapper.getTargetIfValid(entity, x, y, z);

							if (target != null)
							{
								String name = String.format("%s,%s,%s", x, y, z);
								ArrayList<ISensorTarget> arr = map.get(name);

								if (arr == null) {

									arr = new ArrayList<ISensorTarget>();
									map.put(name, arr);

								}

								arr.add(target);
							}
						}
					}
				}
			}
		}
		return map;
	}
}

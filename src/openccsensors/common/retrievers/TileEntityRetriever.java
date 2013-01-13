package openccsensors.common.retrievers;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorTarget;

public class TileEntityRetriever {

	ArrayList<ITileEntityValidatorCallback> targets = new ArrayList<ITileEntityValidatorCallback>();

	public HashMap<String, ArrayList<ISensorTarget>> getCube(World world,
			int sensorX, int sensorY, int sensorZ)
	{
		return getCube(world, sensorX, sensorY, sensorZ, 4);	
	}
	
	public HashMap<String, ArrayList<ISensorTarget>> getCube(World world,
			int sensorX, int sensorY, int sensorZ, int distance) {
		HashMap<String, ArrayList<ISensorTarget>> map = new HashMap<String, ArrayList<ISensorTarget>>();

		for (int x = -distance; x <= distance; x++) {
			for (int y = -distance; y <= distance; y++) {
				for (int z = -distance; z <= distance; z++) {
					
					int tileX = x + sensorX;
					int tileY = y + sensorY;
					int tileZ = z + sensorZ;

					TileEntity entity = world.getBlockTileEntity(tileX, tileY, tileZ);
					
					if (entity != null) {
						
						for (ITileEntityValidatorCallback wrapper : targets) {
							
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
	
	public void registerCallback(ITileEntityValidatorCallback wrapper) {
		targets.add(wrapper);
	}
}

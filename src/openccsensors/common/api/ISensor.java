package openccsensors.common.api;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.world.World;

public interface ISensor {
	public String[] getCustomMethods(SensorUpgrade upgrade);

	public Object callCustomMethod(ISensorAccess sensor, World world, int x, int y, int z, int methodID, Object[] args, SensorUpgrade upgrade) throws Exception;

	public HashMap<String, ArrayList<ISensorTarget>> getSurroundingTargets(
			World world, int sx, int sy, int sz, SensorUpgrade upgrade);
}

package openccsensors.common.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.world.World;

public interface ISensor {
	public String[] getCustomMethods();
	public Object[] callCustomMethod();
	public HashMap<String, ArrayList<ISensorTarget>> getSurroundingTargets(World world, int sx, int sy, int sz, SensorUpgrade upgrade);
}

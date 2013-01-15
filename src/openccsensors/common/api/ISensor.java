package openccsensors.common.api;

import java.util.List;
import net.minecraft.world.World;

public interface ISensor {
	public String[] getCustomMethods();
	public Object[] callCustomMethod();
	public List<ISensorTarget> getSurroundingTargets(World world, int x, int y, int z, SensorUpgrade upgrade);
}

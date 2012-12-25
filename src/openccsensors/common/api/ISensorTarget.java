package openccsensors.common.api;

import java.util.Map;
import net.minecraft.world.World;

public interface ISensorTarget {
	public Map getBasicInformation(World world);
	public Map getDetailInformation(World world);
}



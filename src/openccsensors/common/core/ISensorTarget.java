package openccsensors.common.core;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.World;

public interface ISensorTarget {
	public Map getBasicInformation(World world);
	public Map getDetailInformation(World world);
}



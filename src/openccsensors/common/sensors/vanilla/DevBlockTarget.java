package openccsensors.common.sensors.vanilla;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.world.World;
import openccsensors.common.api.ISensorTarget;

public class DevBlockTarget implements ISensorTarget {

	private int blockId;
	private int metadata;
	
	public DevBlockTarget(int[] obj)
	{
		this.blockId = obj[0];
		this.metadata = obj[1];
	}
	
	@Override
	public Map getBasicInformation(World world) {
		HashMap map = new HashMap();
		map.put("BlockId", this.blockId);
		return map;
	}

	@Override
	public Map getDetailInformation(World world) {
		HashMap map = new HashMap();
		map.put("Metadata", this.metadata);
		return map;
	}

}

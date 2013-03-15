package openccsensors.common.sensors.targets;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorTarget;

public class SonicTarget implements ISensorTarget {

	private static enum Type {
		AIR, LIQUID, SOLID, UNKNOWN,
	};

	private Type type = Type.UNKNOWN;
	int x;
	int y;
	int z;

	public SonicTarget(Block block, int x, int y, int z) {
		try {
			if(block==null){
				type = Type.AIR;
			}else if (block.blockMaterial.isLiquid()) {
				type = Type.LIQUID;
			} else if (block.blockMaterial.isSolid()) {
				type = Type.SOLID;
			}
		} catch (Exception e) {
		}
		this.x = x;
		this.y = y;
		this.z = z;

	}

	@Override
	public String[] getTrackablePropertyNames() {
		return null;
	}

	@Override
	public HashMap getBasicDetails(World world) {
		HashMap ret = new HashMap();
		ret.put("type", type.toString());
		HashMap position = new HashMap();
		position.put("x", x);
		position.put("y", y);
		position.put("z", z);
		ret.put("Position", position);
		return ret;
	}

	@Override
	public HashMap getExtendedDetails(World world) {
		return getBasicDetails(world);
	}

}
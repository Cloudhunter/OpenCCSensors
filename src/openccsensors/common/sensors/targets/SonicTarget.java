package openccsensors.common.sensors.targets;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorTarget;

public class SonicTarget implements ISensorTarget {

	private static enum Type{
		LIQUID,
		SOLID,
		NON_RECOGNISED,
	}
	private Type type = null;
	int x;
	int y;
	int z;
	
	public SonicTarget(Block block, int x, int y, int z){
		if (block.blockMaterial.isLiquid()){
			type=Type.LIQUID;
		}else if (block.blockMaterial.isSolid()){
			type=Type.SOLID;
		}
		this.x=x;
		this.y=y;
		this.z=z;
		
	}
	
	private static final String[] trackable={};
	@Override
	public String[] getTrackablePropertyNames() {
		return trackable;
	}

	@Override
	public HashMap getBasicDetails(World world) {
		HashMap ret = new HashMap();
		ret.put("type", type.toString());
		HashMap position= new HashMap();
		position.put("x", x);
		position.put("y", y);
		position.put("z", z);
		ret.put("Position", position);
		return ret;
	}
	
	
	@Override
	public HashMap getExtendedDetails(World world) {
		return  getBasicDetails(world);
	}

}
package openccsensors.common.sensors;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import openccsensors.common.api.ISensor;
import openccsensors.common.api.ISensorAccess;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.api.ITileEntityValidatorCallback;
import openccsensors.common.api.SensorUpgradeTier;
import openccsensors.common.core.OCSLog;
import openccsensors.common.sensors.targets.SonicTarget;

public class SonicSensor implements ISensor {
	
	@Override
	public String[] getCustomMethods(SensorUpgradeTier upgrade) {
		return null;
	}

	@Override
	public Object callCustomMethod(ISensorAccess sensor, World world, int x, int y, int z, int methodID, Object[] args, SensorUpgradeTier upgrade) {
		return null;
	}
	private static final int BASE_RANGE=5;
	@Override
	public HashMap<String, ArrayList<ISensorTarget>> getSurroundingTargets(
			World world, int sx, int sy, int sz, SensorUpgradeTier upgrade) {
		HashMap ret = new HashMap();
		int RANGE=(new Double(upgrade.getMultiplier())).intValue()*BASE_RANGE;
		for (int x=-RANGE;x<=RANGE;x++){
			for (int y=-RANGE;y<=RANGE;y++){
				for (int z=-RANGE;z<=RANGE;z++){
					if (!(x==0&&y==0&&z==0)){
						int id = world.getBlockId(sx+x,sy+y,sz+z);
						Block block = Block.blocksList[id];
						if (!(id == 0 || block == null)){
							MovingObjectPosition hit = world.rayTraceBlocks(Vec3.createVectorHelper(sx+(x==0?0.5:(x>0?1.5:-0.5)), sy+(y==0?0.5:(y>0?1.5:-0.5)), sz+(z==0?0.5:(z>0?1.5:-0.5))), Vec3.createVectorHelper(sx+x+0.5, sy+y+0.5, sz+z+0.5));	
							if (hit==null || (hit.blockX==sx+x && hit.blockY==sy+y && hit.blockZ==sz+z)){
								ArrayList<ISensorTarget> arr = new ArrayList<ISensorTarget>();
								arr.add(new SonicTarget(block, x,y,z));
								ret.put(x+","+y+","+z, arr);
							}
						}
					}
				}
			}
		}
		return ret;
	}
}

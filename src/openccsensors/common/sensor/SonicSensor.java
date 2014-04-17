package openccsensors.common.sensor;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import openccsensors.api.IRequiresIconLoading;
import openccsensors.api.ISensor;
import openccsensors.api.ISensorTier;
import openccsensors.common.util.OCSLog;

public class SonicSensor implements ISensor, IRequiresIconLoading {
	
	private Icon icon;
	private static final int BASE_RANGE = 3;
	
	@Override
	public HashMap getDetails(World world, Object obj, ChunkCoordinates sensorPos, boolean additional) {

		Vec3 target = (Vec3) obj;

		int x = (int) target.xCoord;
		int y = (int) target.yCoord;
		int z = (int) target.zCoord;
		
		int id = world.getBlockId(x, y, z);
		
		Block block = Block.blocksList[id];
		
		HashMap response = new HashMap();
		
		String type = "UNKNOWN";

		if (block != null && block.blockMaterial != null) {
			if (block.blockMaterial.isLiquid()) {
				type = "LIQUID";
			} else if (block.blockMaterial.isSolid()) {
				type = "SOLID";
			}
		}
		
		response.put("Type", type);
		HashMap position = new HashMap();
		position.put("X", x - sensorPos.posX);
		position.put("Y", y - sensorPos.posY);
		position.put("Z", z - sensorPos.posZ);
		response.put("Position", position);
		
		return response;
	}

	@Override
	public HashMap getTargets(World world, ChunkCoordinates location, ISensorTier tier) {
		
		HashMap targets = new HashMap();

		int range = (new Double(tier.getMultiplier())).intValue() + BASE_RANGE;

		int sx = (int) location.posX;
		int sy = (int) location.posY;
		int sz = (int) location.posZ;
		
		for (int x = -range; x <= range; x++) {
			for (int y = -range; y <= range; y++) {
				for (int z = -range; z <= range; z++) {

					if (!(x == 0 && y == 0 && z == 0) && world.blockExists(sx + x, sy + y, sz + z)) {

						int bX = sx + x;
						int bY = sy + y;
						int bZ = sz + z;

						int id = world.getBlockId(bX, bY, bZ);

						Block block = Block.blocksList[id];

						if (!(id == 0 || block == null)) {
							Vec3 targetPos = Vec3.createVectorHelper(
									bX,
									bY,
									bZ
							);
							if ((Vec3.createVectorHelper((double)location.posX, (double)location.posY, (double)location.posZ)).distanceTo(targetPos) <= range) {
								targets.put(String.format("%s,%s,%s", x, y, z), targetPos);
							}
							
						}
					}
				}
			}
		}
		return targets;
	}

	@Override
	public String[] getCustomMethods(ISensorTier tier) {
		return null;
	}

	@Override
	public Object callCustomMethod(World world, ChunkCoordinates location, int methodID,
			Object[] args, ISensorTier tier) {
		return null;
	}

	@Override
	public String getName() {
		return "sonicCard";
	}

	@Override
	public Icon getIcon() {
		return icon;
	}

	@Override
	public void loadIcon(IconRegister iconRegistry) {
		icon = iconRegistry.registerIcon("openccsensors:sonic");
	}

	@Override
	public ItemStack getUniqueRecipeItem() {
		return new ItemStack(Block.jukebox);
	}

}

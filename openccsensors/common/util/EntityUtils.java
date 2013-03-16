package openccsensors.common.util;

import java.util.HashMap;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class EntityUtils {

	public static HashMap<String, Entity> getEntities(World world, Vec3 sensorPos, double radius, Class filter) {
		HashMap<String, Entity> map = new HashMap<String, Entity>();
		
		int dChunk = (int) Math.ceil(radius / 16.0F);
		
		int x = (int)sensorPos.xCoord;
		int y = (int)sensorPos.yCoord;
		int z = (int)sensorPos.zCoord;

		for (int dx = -dChunk; dx <= dChunk; dx++) {
			for (int dz = -dChunk; dz <= dChunk; dz++) {
				Chunk chunk = world.getChunkFromBlockCoords(x+16*dx, z+16*dz);
				for (List<Entity> subchunk : chunk.entityLists) {
					for (Entity entity : subchunk) {
						Vec3 livingPos = Vec3.createVectorHelper(
								entity.posX + 0.5,
								entity.posY + 0.5,
								entity.posZ + 0.5
						);
						if (sensorPos.distanceTo(livingPos) <= radius && filter.isAssignableFrom(entity.getClass())) {
							String targetName = (entity instanceof EntityPlayer) ? entity
									.getEntityName() : entity
									.getEntityName() + entity.entityId;
							targetName = targetName.replaceAll("\\s", "");
							map.put(targetName, entity);
						}
					}
				}
			}
		}

		return map;
	}
}
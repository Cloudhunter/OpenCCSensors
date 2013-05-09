package openccsensors.common.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
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

	
	public static HashMap livingToMap(EntityLiving living, boolean additional) {
		HashMap map = new HashMap();
		
		HashMap position = new HashMap();
		position.put("X", living.posX);
		position.put("Y", living.posY);
		position.put("Z", living.posZ);
		map.put("Position", position);

		map.put("Name", (living instanceof EntityPlayer) ? "Player" : living.getEntityName());
		map.put("RawName", living.getClass().getName());
		
		if (additional) {

			map.put("HeldItem", InventoryUtils.itemstackToMap(living.getHeldItem()));
	
			HashMap armour = new HashMap();
			armour.put("Boots", InventoryUtils.itemstackToMap(living.getCurrentItemOrArmor(1)));
			armour.put("Leggings", InventoryUtils.itemstackToMap(living.getCurrentItemOrArmor(2)));
			armour.put("Chestplate", InventoryUtils.itemstackToMap(living.getCurrentItemOrArmor(3)));
			armour.put("Helmet", InventoryUtils.itemstackToMap(living.getCurrentItemOrArmor(4)));
			
			map.put("Armour", armour);
			map.put("Health", living.getHealth());
			map.put("IsAirborne", living.isAirBorne);
			map.put("IsJumping", living.isJumping);
			map.put("IsBlocking", living.isBlocking());
			map.put("IsBurning", living.isBurning());
			map.put("IsAlive", living.isEntityAlive());
			map.put("IsInWater", living.isInWater());
			map.put("IsOnLadder", living.isOnLadder());
			map.put("IsSleeping", living.isPlayerSleeping());
			map.put("IsRiding", living.isRiding());
			map.put("IsSneaking", living.isSneaking());
			map.put("IsSprinting", living.isSprinting());
			map.put("IsWet", living.isWet());
			map.put("IsChild", living.isChild());
			map.put("IsHome", living.isWithinHomeDistanceCurrentPosition());
			map.put("Yaw", living.rotationYaw);
			map.put("Pitch", living.rotationPitch);
			map.put("YawHead", living.rotationYawHead);
	
			HashMap potionEffects = new HashMap();
			Collection<PotionEffect> effects = living.getActivePotionEffects();
			int count = 1;
			for (PotionEffect effect : effects) {
				potionEffects.put(count, effect.getEffectName());
				count++;
			}
			map.put("PotionEffects", potionEffects);
		}
	
		if (living instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) living;
			map.put("FoodLevel", player.getFoodStats().getFoodLevel());
			map.put("Gamemode", player.capabilities.isCreativeMode);
			if (additional) {
				map.put("Username", player.username);
			}
			map.put("Inventory", InventoryUtils.invToMap(player.inventory));
		}

		return map;
	}
}
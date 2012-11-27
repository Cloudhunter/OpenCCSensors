package openccsensors.common.helper;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import net.minecraft.src.Chunk;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.PotionEffect;
import net.minecraft.src.Vec3;
import net.minecraft.src.World;

public class LivingEntityHelper {

	public static Map livingToMap( EntityLiving living )
	{
		HashMap map = new HashMap();
		
		map.put("heldItem", InventoryHelper.itemstackToMap(living.getHeldItem()));
		
		HashMap armour = new HashMap();
		armour.put("boots", InventoryHelper.itemstackToMap(living.getCurrentItemOrArmor(1)));
		armour.put("leggings", InventoryHelper.itemstackToMap(living.getCurrentItemOrArmor(2)));
		armour.put("chestplate", InventoryHelper.itemstackToMap(living.getCurrentItemOrArmor(3)));
		armour.put("helmet", InventoryHelper.itemstackToMap(living.getCurrentItemOrArmor(4)));
		map.put("armour", armour);
		map.put("health", living.getHealth());
		map.put("isAirborne", living.isAirBorne);		
		map.put("isJumping", living.isJumping);
		map.put("isBlocking", living.isBlocking());
		map.put("isBurning", living.isBurning());
		map.put("isEating", living.isEating());
		map.put("isAlive", living.isEntityAlive());
		map.put("isInWater", living.isInWater());
		map.put("isOnLadder", living.isOnLadder());
		map.put("isSleeping", living.isPlayerSleeping());
		map.put("isRiding", living.isRiding());
		map.put("isSneaking", living.isSneaking());
		map.put("isSprinting", living.isSprinting());
		map.put("isWet", living.isWet());
		map.put("isHome", living.isWithinHomeDistanceCurrentPosition());
		
		HashMap potionEffects = new HashMap();
		Collection<PotionEffect> effects = living.getActivePotionEffects();
		int count = 1;
		for (PotionEffect effect : effects)
		{
			potionEffects.put(count, effect.getEffectName());
			count++;
		}
		map.put("potionEffects", potionEffects);
		
		if (living instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)living;
			map.put("foodLevel", player.getFoodStats().getFoodLevel());
			map.put("gamemode", player.capabilities.isCreativeMode);
			map.put("username", player.username);
			map.put("inventory", InventoryHelper.invToMap(player.inventory));
		}
		
		return map;
	}

	public static HashMap<String, EntityLiving> getLivingEntities(World world, int x, int y, int z, double radius)
	{
		HashMap<String, EntityLiving> map = new HashMap<String, EntityLiving>();
		Vec3 sensorPos = Vec3.createVectorHelper(x,y,z);
		
		int dChunk = (int) Math.ceil(radius/16.0F);  // calculate the maximum distance in chunks to search in
		
		for (int dx=-dChunk; dx<=dChunk; dx++)
		{
			for (int dz=-dChunk; dz<=dChunk; dz++)
			{
				Chunk chunk = world.getChunkFromBlockCoords(x+16*dx, z+16*dz);
				for (List<Entity> subchunk : chunk.entityLists)
				{
					for (Entity entity : subchunk)
					{
						if (entity instanceof EntityLiving)
						{
							EntityLiving entityLiving = (EntityLiving)entity;
							Vec3 livingPos = Vec3.createVectorHelper(entityLiving.posX, entityLiving.posY, entityLiving.posZ);
							if (sensorPos.distanceTo(livingPos) <= radius)
							{
								// Only append the id when it's not a player. EntityPlayer already appends the id: 
								String targetName = (entityLiving instanceof EntityPlayer) ? entityLiving.getEntityName() : entityLiving.getEntityName() + entityLiving.entityId;
								targetName = targetName.replaceAll("\\s","");
								map.put(targetName, entityLiving);
							}
						}
					}
				}
			}
		}
		
		return map;
	}
}

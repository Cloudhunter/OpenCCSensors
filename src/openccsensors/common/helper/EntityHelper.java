package openccsensors.common.helper;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class EntityHelper {

	public static Map livingToMap( EntityLiving living )
	{
		HashMap map = new HashMap();
		
		map.put("HeldItem", InventoryHelper.itemstackToMap(living.getHeldItem()));
		
		HashMap armour = new HashMap();
		armour.put("Boots", InventoryHelper.itemstackToMap(living.getCurrentItemOrArmor(1)));
		armour.put("Leggings", InventoryHelper.itemstackToMap(living.getCurrentItemOrArmor(2)));
		armour.put("Chestplate", InventoryHelper.itemstackToMap(living.getCurrentItemOrArmor(3)));
		armour.put("Helmet", InventoryHelper.itemstackToMap(living.getCurrentItemOrArmor(4)));
		map.put("Armour", armour);
		map.put("Health", living.getHealth());
		map.put("IsAirborne", living.isAirBorne);		
		map.put("IsJumping", living.isJumping);
		map.put("IsBlocking", living.isBlocking());
		map.put("IsBurning", living.isBurning());
		//map.put("IsEating", living.isEating());
		map.put("IsAlive", living.isEntityAlive());
		map.put("IsInWater", living.isInWater());
		map.put("IsOnLadder", living.isOnLadder());
		map.put("IsSleeping", living.isPlayerSleeping());
		map.put("IsRiding", living.isRiding());
		map.put("IsSneaking", living.isSneaking());
		map.put("IsSprinting", living.isSprinting());
		map.put("IsWet", living.isWet());
		map.put("IsHome", living.isWithinHomeDistanceCurrentPosition());
		
		HashMap potionEffects = new HashMap();
		Collection<PotionEffect> effects = living.getActivePotionEffects();
		int count = 1;
		for (PotionEffect effect : effects)
		{
			potionEffects.put(count, effect.getEffectName());
			count++;
		}
		map.put("PotionEffects", potionEffects);
		
		if (living instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)living;
			map.put("FoodLevel", player.getFoodStats().getFoodLevel());
			map.put("Gamemode", player.capabilities.isCreativeMode);
			map.put("Username", player.username);
			map.put("Inventory", InventoryHelper.invToMap(player.inventory));
		}
		
		return map;
	}

	public static HashMap<String, Entity> getEntities(World world, int x, int y, int z, double radius)
	{
		HashMap<String, Entity> map = new HashMap<String, Entity>();
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
						Entity entityLiving = (Entity)entity;
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
		
		return map;
	}
}

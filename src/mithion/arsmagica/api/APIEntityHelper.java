package mithion.arsmagica.api;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.google.common.base.Optional;

import cpw.mods.fml.common.FMLLog;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.world.World;

public class APIEntityHelper {
	private Method makeSpellCasterCall;
	private Method spawnMobCall;

	public Optional<String> WispMobID = Optional.absent();
	public Optional<String> ManaElemMobID = Optional.absent();
	public Optional<String> MageVillagerMobID = Optional.absent();
	public Optional<String> HecateMobID = Optional.absent();
	public Optional<String> ManaCreeperMobID = Optional.absent();
	public Optional<String> DryadMobID = Optional.absent();
	public Optional<String> LightMageMobID = Optional.absent();
	public Optional<String> DarkMageMobID = Optional.absent();
	public Optional<String> TowerGuardianMobID = Optional.absent();
	public Optional<String> WaterElementalMobID = Optional.absent();
	public Optional<String> DarklingMobID = Optional.absent();
	
	public APIEntityHelper(){
		makeSpellCasterCall  = ApiHelper.initializeStaticMethodLookup("mithion.arsmagica.EntityHelper", "MakeSpellCaster", int.class, float.class, boolean.class, EntityLiving.class);
		spawnMobCall  = ApiHelper.initializeStaticMethodLookup("mithion.arsmagica.EntityHelper", "spawnEntityAt", World.class, int.class, int.class, int.class, String.class);
	}
	
	/**
	 * Injects the Ars Magica spell caster AI task into the specified entity's task list. <br/>
	 * Keep in mind that the host entity needs a valid target in order for this spell casting task to work properly.
	 * @param priority How prioritized should the task be?  Lower is higher priority.
	 * @param moveSpeed How fast should the entity be able to move when this AI task is in control?
	 * @param playerHostile Is this entity hostile to players?
	 * @param entity The entity to add the task to.  Must return true from isAIEnabled().
	 * @return True if the task is successfully added, otherwise false.
	 */
	public boolean MakeSpellCaster(int priority, float moveSpeed, boolean playerHostile, EntityLiving entity){
		if (makeSpellCasterCall==null)return false;
		try {
			makeSpellCasterCall.invoke(null, priority, moveSpeed, playerHostile, entity);
			return true;
		} catch (Throwable t){			
			FMLLog.warning("Ars Magica API >> Error attempting to make spell caster out of " + entity.getClass() + ".");
			t.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Spawns the specified AM Mob at the specified coordinates.
	 * @param world The world to spawn the mob in
	 * @param x The x coordinate of the mob
	 * @param y The y coordinate of the mob
	 * @param z The z coordinate of the mob
	 * @param ID The type of mob to spawn
	 * @return True if the mob was spawned (or attempted to spawn, this can still fail due to not meeting spawn conditions).  False, if the API is not hooked into Ars Magica (ie, mod isn't loaded)
	 */
	public boolean spawnMobAtPoint(World world, int x, int y, int z, String ID){
		if (spawnMobCall == null) return false;
		try {
			spawnMobCall.invoke(null, world, x, y, z, ID);
			return true;
		} catch (Throwable t){
			FMLLog.warning("Ars Magica API >> Error when attempting to spawn a mob at %d, %d, %d (type %s)", x, y, z, ID);
			t.printStackTrace();			
		}
		return false;
	}
}

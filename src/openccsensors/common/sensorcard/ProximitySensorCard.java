package openccsensors.common.sensorcard;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraft.src.Chunk;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.TileEntity;
import net.minecraft.src.Vec3;
import net.minecraft.src.World;
import openccsensors.common.core.GenericSensorInterface;
import openccsensors.common.core.ISensorInterface;
import openccsensors.common.core.ISensorCard;
import openccsensors.common.core.ISensorTarget;
import openccsensors.common.helper.BlockTileHelper;
import openccsensors.common.helper.LivingEntityHelper;
import openccsensors.common.helper.SensorHelper;
import openccsensors.common.sensorcard.InventorySensorCard.InventoryTarget;

/**
 * Item which allows sensing of living entities within the defined radius
 */
public class ProximitySensorCard extends Item implements ISensorCard
{
	private final double sensingRadius = 16.0F;

	public ProximitySensorCard(int par1)
	{
		super(par1);
		setCreativeTab(CreativeTabs.tabRedstone);
	}
	
	// Temporary! To differentiate this sensor card from the Inventory one. 
	@Override
	public int getIconFromDamage(int par1) {
		return 1;
	}

	@Override
	public ISensorInterface getSensorInterface(ItemStack itemstack, boolean turtle) 
	{
		return new InventorySensorInterface();
	}
	
	@Override
	public String getItemNameIS(ItemStack is)
	{
		return "openccsensors.item.proximitysensor";
	}
	
	public class InventorySensorInterface extends GenericSensorInterface implements ISensorInterface 
	{
		

		@Override
		public String getName() 
		{
			return "proximity";
		}
		
		@Override
		public HashMap<String, ISensorTarget> getAvailableTargets(World world, int x, int y, int z)
		{

			HashMap<String, ISensorTarget> targets = new HashMap<String, ISensorTarget>();

			HashMap<String, EntityLiving> entities = LivingEntityHelper.getLivingEntities(world, x, y, z, sensingRadius);

			Iterator it = entities.entrySet().iterator();

			while (it.hasNext()) {
				Map.Entry<String, EntityLiving> pairs = (Entry<String, EntityLiving>) it.next();
				targets.put(pairs.getKey(), new LivingTarget(pairs.getValue(), x, y, z));
			}

			return targets;
		}

		@Override
		public String[] getMethods() 
		{
			return null;
		}

		@Override
		public Object[] callMethod(int methodID, Object[] args) throws Exception 
		{
			return null;
		}

	}
	
	/*
	 * Object to represent a living target
	 */
	protected class LivingTarget implements ISensorTarget
	{
		private int id;
		private Vec3 sensorPos;
		protected String rawType;
		
		LivingTarget(EntityLiving living, int sx, int sy, int sz)
		{
			super();
			id = living.entityId;
			sensorPos = Vec3.createVectorHelper(sx, sy, sz);
			rawType = (living instanceof EntityPlayer) ? "Player" : living.getEntityName();

		}
		
		public Map getBasicInformation(World world)
		{
			EntityLiving entityLiving = (EntityLiving)world.getEntityByID(id);
			
			HashMap retMap = new HashMap();
			
			retMap.put("type", rawType);
			addPositionToMap(entityLiving, retMap);
			
			return retMap;
		}
		
		public Map getDetailInformation(World world)
		{
			EntityLiving entityLiving = (EntityLiving)world.getEntityByID(id);
			
			Map retMap = LivingEntityHelper.livingToMap(entityLiving);
			
			if (entityLiving == null)
			{
				return null;
			}
			
			retMap.put("type", rawType);
			addPositionToMap(entityLiving, retMap);
			
			return retMap;
		}
		
		private void addPositionToMap(EntityLiving living, Map map)
		{
			HashMap<String, Integer> pos = new HashMap<String,Integer>();
			pos.put("x", ((Double) living.posX).intValue() - (int) sensorPos.xCoord);
			pos.put("y", ((Double) living.posY).intValue() - (int) sensorPos.yCoord);
			pos.put("z", ((Double) living.posZ).intValue() - (int) sensorPos.zCoord);
			map.put("position", pos);
		}

	}
}

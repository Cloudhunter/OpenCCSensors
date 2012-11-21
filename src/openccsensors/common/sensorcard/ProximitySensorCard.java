package openccsensors.common.sensorcard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import openccsensors.common.core.ISensorInterface;
import openccsensors.common.core.ISensorCard;
import openccsensors.common.core.SensorHelper;

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
	
	public class InventorySensorInterface implements ISensorInterface 
	{
		
		HashMap livingMap;

		@Override
		public String getName() 
		{
			return "proximity";
		}
		
		@Override
		public Map getBasicTarget(World world, int x, int y, int z)
		{
			HashMap livingEntityMap = SensorHelper.getLivingEntities(world, x, y, z, sensingRadius);
			
			Iterator it = livingEntityMap.entrySet().iterator();
		    while (it.hasNext())
		    {
		    	Map.Entry pairs = (Map.Entry) it.next();
		    	pairs.setValue(new LivingTarget((EntityLiving) pairs.getValue()));
		    }
		    
		    livingMap = livingEntityMap;
		    
		    HashMap retMap = new HashMap();
		    
		    it = livingMap.entrySet().iterator();
		    
		    while (it.hasNext())
		    {
		    	Map.Entry pairs = (Map.Entry) it.next();
		    	retMap.put(pairs.getKey(), ((LivingTarget) pairs.getValue()).getBasicInformation(world));
		    }
			
			return retMap;
		}

		@Override
		public Map getDetailTarget(World world, int x, int y, int z, String target)
		{
			LivingTarget living = (LivingTarget) livingMap.get(target);
			if (living == null)
			{
				return null;
			}
			
			
			return living.getDetailInformation(world);
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
	
	private class LivingTarget
	{
		private int id;
		
		private String rawType;
		private String name;
		
		LivingTarget(EntityLiving living)
		{
			id = living.entityId;
			
			rawType = (living instanceof EntityPlayer) ? "Player" : living.getEntityName();
		}
		
		private void addPositionToMap(EntityLiving living, Map map)
		{
			HashMap<String, Integer> pos = new HashMap<String,Integer>();
			pos.put("x", ((Double) living.posX).intValue());
			pos.put("y", ((Double) living.posY).intValue());
			pos.put("z", ((Double) living.posZ).intValue());
			map.put("position",pos);
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
			
			Map retMap = SensorHelper.livingToMap(entityLiving);
			
			if (entityLiving == null)
			{
				return null;
			}
			
			retMap.put("type", rawType);
			addPositionToMap(entityLiving, retMap);
			
			return retMap;
		}
	}

}

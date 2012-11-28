package openccsensors.common.sensorcard;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import openccsensors.common.core.ISensorInterface;
import openccsensors.common.core.ISensorCard;
import openccsensors.common.core.SensorHelper;

public class InventorySensorCard extends Item implements ISensorCard
{

	public InventorySensorCard(int par1)
	{
		super(par1);
		setCreativeTab(CreativeTabs.tabRedstone);
		setTextureFile("/openccsensors/resources/images/terrain.png");
	}
		
	// Temporary! 
	@Override
	public int getIconFromDamage(int par1) {
		return 16;
	}

	@Override
	public ISensorInterface getSensorInterface(ItemStack itemstack, boolean turtle) 
	{
		return new InventorySensorInterface();
	}
	
	@Override
	public String getItemNameIS(ItemStack is)
	{
		return "openccsensors.item.inventorysensor";
	}
	
	public class InventorySensorInterface implements ISensorInterface 
	{
		
		Map inventoryMap;

		@Override
		public String getName() 
		{
			return "inventory";
		}
		
		@Override
		public Map getBasicTarget(World world, int x, int y, int z)
		{
			HashMap tileMap = SensorHelper.getAdjacentTile(world, x, y, z, IInventory.class);
			
			SensorHelper.addToHashMap(world.getBlockTileEntity(x, y, z), tileMap, IInventory.class, "SELF");
			
		    Iterator it = tileMap.entrySet().iterator();
		    while (it.hasNext())
		    {
		    	Map.Entry pairs = (Map.Entry) it.next();
		    	pairs.setValue(new InventoryTarget((TileEntity) pairs.getValue()));
		    }
		    
		    inventoryMap = tileMap;
		    
		    HashMap retMap = new HashMap();
		    
		    it = inventoryMap.entrySet().iterator();
		    
		    while (it.hasNext())
		    {
		    	Map.Entry pairs = (Map.Entry) it.next();
		    	retMap.put(pairs.getKey(), ((InventoryTarget) pairs.getValue()).getBasicInformation(world));
		    }
			
			return retMap;
		}

		@Override
		public Map getDetailTarget(World world, int x, int y, int z, String target)
		{
			InventoryTarget inv = (InventoryTarget) inventoryMap.get(target);
			if (inv == null)
			{
				return null;
			}
			
			
			return inv.getDetailInformation(world);
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
	
	private class InventoryTarget
	{
		private int xCoord;
		private int yCoord;
		private int zCoord;
		
		private String rawType;
		private String name;
		
		InventoryTarget(TileEntity inventory)
		{
			xCoord = inventory.xCoord;
			yCoord = inventory.yCoord;
			zCoord = inventory.zCoord;
			
			rawType = inventory.getClass().getName();		
		}
		
		public Map getBasicInformation(World world)
		{
			HashMap retMap = new HashMap();
			
			retMap.put("type", SensorHelper.getType(rawType)); // abuse translation system to translate obsfucated/dev names
			
			return retMap;
		}
		
		public Map getDetailInformation(World world)
		{
			HashMap retMap = new HashMap();
			TileEntity tile = world.getBlockTileEntity(xCoord, yCoord, zCoord);
			
			if (tile == null || !(tile instanceof IInventory))
			{
				return null;
			}
			
			return SensorHelper.invToMap((IInventory) tile);
		}
	}

}

package openccsensors.common.sensorcard;

import ic2.api.EnergyNet;
import ic2.api.IEnergyConductor;
import ic2.api.IEnergySink;
import ic2.api.IEnergySource;
import ic2.api.IEnergyStorage;
import ic2.api.IReactor;
import ic2.api.IReactorChamber;
import ic2.api.Ic2Recipes;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import openccsensors.common.core.ISensorCard;
import openccsensors.common.core.ISensorInterface;
import openccsensors.common.core.SensorHelper;

public class IndustrialCraftSensorCard extends Item implements ISensorCard 
{
	public IndustrialCraftSensorCard(int par1)
	{
		super(par1);
		setCreativeTab(CreativeTabs.tabRedstone);
	}
	
	@Override
	public ISensorInterface getSensorInterface(ItemStack itemstack, boolean turtle) 
	{
		return new InventorySensorInterface();
	}
	
	@Override
	public String getItemNameIS(ItemStack is)
	{
		return "openccsensors.item.industrialsensor";
	}
	
	
	// Temporary! To differentiate this sensor card from the Inventory/Proximity one. 
	@Override
	public int getIconFromDamage(int par1) {
		return 2;
	}
	
	
	public class InventorySensorInterface implements ISensorInterface 
	{
		
		Map ic2EntityMap;

		@Override
		public String getName() 
		{
			return "industrial";
		}
		
		@Override
		public Map getBasicTarget(World world, int x, int y, int z)
		{			
			Class[] validIC2Tiles = new Class[] { IReactor.class, IReactorChamber.class, IEnergyStorage.class, IEnergyConductor.class, IEnergySource.class, IEnergySink.class };

			HashMap tileMap = SensorHelper.getAdjacentTile(world, x, y, z, validIC2Tiles);		
		    Iterator it = tileMap.entrySet().iterator();
		    while (it.hasNext())
		    {
		    	Map.Entry pairs = (Map.Entry) it.next();
		    	TileEntity ic2Entity = (TileEntity)pairs.getValue();
		    	if (!(ic2Entity instanceof IReactorChamber) || (((IReactorChamber)ic2Entity).getReactor() != null))
		    		pairs.setValue(new IC2Target((TileEntity) pairs.getValue()));
		    }		    
		    ic2EntityMap = tileMap;
		    
		    HashMap retMap = new HashMap();		    
		    it = ic2EntityMap.entrySet().iterator();		    
		    while (it.hasNext())
		    {
		    	Map.Entry pairs = (Map.Entry) it.next();
		    	retMap.put(pairs.getKey(), ((IC2Target) pairs.getValue()).getBasicInformation(world));
		    }
			
			return retMap;
		}

		@Override
		public Map getDetailTarget(World world, int x, int y, int z, String target)
		{
			IC2Target ic2Entity = (IC2Target) ic2EntityMap.get(target);
			return ic2Entity.getDetailInformation(world);
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
	
	private class IC2Target
	{
		private int xCoord;
		private int yCoord;
		private int zCoord;
		
		private String interfaceType;
		private String name;
		
		IC2Target(TileEntity ic2Entity)
		{
			xCoord = ic2Entity.xCoord;
			yCoord = ic2Entity.yCoord;
			zCoord = ic2Entity.zCoord;
			
			if ((ic2Entity instanceof IReactor) || (ic2Entity instanceof IReactorChamber))
				interfaceType = IReactor.class.getName();
			else if (ic2Entity instanceof IEnergyStorage)
				interfaceType = IEnergyStorage.class.getName();
			else if (ic2Entity instanceof IEnergyConductor)
				interfaceType = IEnergyConductor.class.getName();
			else if (ic2Entity instanceof IEnergySink)
				interfaceType = IEnergySink.class.getName();
			else if (ic2Entity instanceof IEnergySource)
				interfaceType = IEnergySource.class.getName();
			else
				interfaceType = ic2Entity.getClass().getName();			
		}
		
		private HashMap populateReactorData(IReactor reactor)
		{
			HashMap rtn = null;
			if (reactor != null)
			{
				rtn = new HashMap();				
				rtn.put("heat", reactor.getHeat());
				rtn.put("maxHeat", reactor.getMaxHeat());
				rtn.put("powered", reactor.produceEnergy());
				rtn.put("output", reactor.getOutput());			
				IInventory reactorInventory = (IInventory)reactor;
				rtn.put("inventory", SensorHelper.invToMap(reactorInventory));				
			}
			return rtn;
		}
		
		private HashMap populateEnergyStorageData(IEnergyStorage storage)
		{
			HashMap rtn = null;
			if (storage != null)
			{
				rtn = new HashMap();
				rtn.put("stored", storage.getStored());
				rtn.put("capacity", storage.getCapacity());
				rtn.put("output", storage.getOutput());
			}
			return rtn;
		}
		
		private HashMap populateEnergyConducted(World world, TileEntity tile)
		{
			HashMap rtn = null;
			if (tile != null)
			{
				rtn = new HashMap();
				Long energyConducted = EnergyNet.getForWorld(world).getTotalEnergyConducted((TileEntity)tile);
				rtn.put("energyConducted", energyConducted);
			}
			return rtn;
		}
		
		public Map getBasicInformation(World world)
		{
			HashMap retMap = new HashMap();
			
			retMap.put("type", SensorHelper.getType(interfaceType)); // abuse translation system to translate obsfucated/dev names
			
			return retMap;
		}
		
		public Map getDetailInformation(World world)
		{
			HashMap retMap = null;
			TileEntity tile = world.getBlockTileEntity(xCoord, yCoord, zCoord);			
			if (tile != null)
			{
				if (IReactor.class.isInstance(tile))
					retMap = populateReactorData((IReactor)tile);
				else if (IReactorChamber.class.isInstance(tile))
					retMap = populateReactorData(((IReactorChamber)tile).getReactor());
				else if (IEnergyStorage.class.isInstance(tile))
					retMap = populateEnergyStorageData((IEnergyStorage)tile);
				else
					retMap = populateEnergyConducted(world, tile);
			}
			return retMap;
		}
	}
}

package openccsensors.common.sensorcard;

import ic2.api.IEnergyStorage;
import ic2.api.IReactor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import openccsensors.OpenCCSensors;
import openccsensors.common.core.GenericSensorInterface;
import openccsensors.common.core.ISensorInterface;
import openccsensors.common.core.ISensorCard;
import openccsensors.common.core.ISensorTarget;
import openccsensors.common.core.OCSLog;
import openccsensors.common.helper.BlockTileHelper;
import openccsensors.common.helper.SensorHelper;
import openccsensors.common.sensorcard.InventorySensorCard.InventoryTarget;
import openccsensors.common.sensortargets.TileSensorTarget;

public class IC2SensorCard extends Item implements ISensorCard
{

	public IC2SensorCard(int par1)
	{
		super(par1);
		setCreativeTab(CreativeTabs.tabRedstone);
		addRecipeToGameRegistry();
	}

	@Override
	public ISensorInterface getSensorInterface(ItemStack itemstack, boolean turtle) 
	{
		return new IC2SensorInterface();
	}
	
	@Override
	public String getItemNameIS(ItemStack is)
	{
		return "openccsensors.item.ic2sensor";
	}
	
	public void addRecipeToGameRegistry()
	{
		GameRegistry.addRecipe(new ItemStack(this), 
				"ccr",
				"crr",
				"rrp",
				'r', new ItemStack(Item.redstone),
				'c', ic2.api.Items.getItem("uraniumIngot") ,
				'p', new ItemStack(Item.paper));
	}
	
	public class IC2SensorInterface extends GenericSensorInterface implements ISensorInterface 
	{
		
		Class[] relevantClassTypes = {
				IEnergyStorage.class,
				IReactor.class
		};

		@Override
		public String getName() 
		{
			return "ic2sensor";
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
		
		@Override
		public HashMap<String, ISensorTarget> getAvailableTargets(World world, int x, int y, int z) {
			
			HashMap<String, ISensorTarget> targets = new HashMap<String, ISensorTarget>();

			HashMap<String, TileEntity> entities = BlockTileHelper.getAdjacentTile(world, x, y, z, relevantClassTypes);

			Iterator it = entities.entrySet().iterator();

			while (it.hasNext()) {
				Map.Entry<String, TileEntity> pairs = (Entry<String, TileEntity>) it.next();
				targets.put(pairs.getKey(), getTargetForTileEntity(pairs.getValue()));
			}

			return targets;
		}
		
		private ISensorTarget getTargetForTileEntity(TileEntity entity)
		{
			if (entity instanceof IReactor)
			{
				return new ReactorTarget(entity);
			}
			else if (entity instanceof IEnergyStorage)
			{
				return new EnergyStorageTarget(entity);
			}
			
			return null;
		}

	}
	
	protected class ReactorTarget extends TileSensorTarget implements ISensorTarget
	{

		ReactorTarget(TileEntity targetEntity) {
			super(targetEntity);
		}


		public Map getDetailInformation(World world)
		{
			HashMap retMap = new HashMap();
			
			TileEntity tile = world.getBlockTileEntity(xCoord, yCoord, zCoord);
			if (tile == null || !(tile instanceof IReactor)) {
				return null;
			}
			
			retMap.put("Heat", ((IReactor) tile).getHeat());
			retMap.put("MaxHeat", ((IReactor) tile).getMaxHeat());
			retMap.put("Output", ((IReactor) tile).getOutput());
			retMap.put("Active", ((IReactor) tile).produceEnergy());
			
			return retMap;
			
		}

	}
	
	protected class EnergyStorageTarget extends TileSensorTarget implements ISensorTarget
	{

		EnergyStorageTarget(TileEntity targetEntity) {
			super(targetEntity);
		}

		public Map getDetailInformation(World world)
		{
			HashMap retMap = new HashMap();
			
			TileEntity tile = world.getBlockTileEntity(xCoord, yCoord, zCoord);
			if (tile == null || !(tile instanceof IEnergyStorage)) {
				return null;
			}
			
			retMap.put("Stored", ((IEnergyStorage) tile).getStored());
			retMap.put("Capacity", ((IEnergyStorage) tile).getCapacity());
			retMap.put("Output", ((IEnergyStorage) tile).getOutput());
			
			return retMap;
			
		}

	}

}

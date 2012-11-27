package openccsensors.common.sensorcard;

import ic2.api.IReactor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import cpw.mods.fml.common.registry.GameRegistry;
import openccsensors.common.core.GenericSensorInterface;
import openccsensors.common.core.ISensorCard;
import openccsensors.common.core.ISensorInterface;
import openccsensors.common.core.ISensorTarget;
import openccsensors.common.core.OCSLog;
import openccsensors.common.helper.BlockTileHelper;
import openccsensors.common.sensortargets.TileSensorTarget;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import buildcraft.energy.TileEngine;

public class BuildCraftSensorCard extends Item implements ISensorCard
{

	public BuildCraftSensorCard(int par1)
	{
		super(par1);
		setCreativeTab(CreativeTabs.tabRedstone);
		addRecipeToGameRegistry();
	}

	@Override
	public ISensorInterface getSensorInterface(ItemStack itemstack, boolean turtle) 
	{
		return new BuildCraftSensorInterface();
	}
	
	@Override
	public String getItemNameIS(ItemStack is)
	{
		return "openccsensors.item.buildcraftsensor";
	}
	
	public void addRecipeToGameRegistry()
	{
		GameRegistry.addRecipe(new ItemStack(this), 
				"ccr",
				"crp",
				"rrp",
				'r', new ItemStack(Item.redstone),
				'c', ic2.api.Items.getItem("uraniumIngot") ,
				'p', new ItemStack(Item.paper));
	}
	
	public class BuildCraftSensorInterface extends GenericSensorInterface implements ISensorInterface 
	{
		
		Class[] relevantClassTypes = {
				TileEngine.class
		};

		@Override
		public String getName() 
		{
			return "buildcraftsensor";
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
			if (entity instanceof TileEngine)
			{
				OCSLog.info(entity.toString());
				return new EngineTarget(entity);
			}
			return null;
		}

	}
	
	
	protected class EngineTarget extends TileSensorTarget implements ISensorTarget
	{

		EngineTarget(TileEntity targetEntity) {
			super(targetEntity);
		}

		@Override
		public Map getDetailInformation(World world) {

			HashMap retMap = new HashMap();
			
			TileEntity tile = world.getBlockTileEntity(xCoord, yCoord, zCoord);
			if (tile == null || !(tile instanceof TileEngine)) {
				return null;
			}
			
			retMap.put("Active", ((TileEngine) tile).engine.isActive());
			retMap.put("Heat", ((TileEngine) tile).engine.getHeat());
			retMap.put("IsBurning", ((TileEngine) tile).engine.isBurning());
			retMap.put("PistonSpeed", ((TileEngine) tile).engine.getPistonSpeed());
			retMap.put("Energy", ((TileEngine) tile).engine.energy);
			retMap.put("MaxEnergy", ((TileEngine) tile).engine.maxEnergy);
			
			return retMap;
		}

	}

}

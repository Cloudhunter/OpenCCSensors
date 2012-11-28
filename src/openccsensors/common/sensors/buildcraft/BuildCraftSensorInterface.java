package openccsensors.common.sensors.buildcraft;

import java.util.Map;

import buildcraft.energy.TileEngine;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import openccsensors.common.api.ISensorInterface;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.api.ITargetWrapper;
import openccsensors.common.helper.TargetHelper;
import openccsensors.common.sensors.TargetRetriever;

public class BuildCraftSensorInterface implements ISensorInterface 
{

	private TargetRetriever retriever = new TargetRetriever();
	
	public BuildCraftSensorInterface()
	{
		retriever.registerTarget(TileEngine.class, new ITargetWrapper() {
			@Override
			public ISensorTarget createNew(Object entity, int sx, int sy, int sz) {
				return new EngineTarget((TileEntity)entity);
			}
		});
	}
	
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
	public Map getBasicTarget(World world, int x, int y, int z)
			throws Exception {
		
		return TargetHelper.getBasicInformationForTargets(
				retriever.getAdjacentTiles(world, x, y, z),
				world
		);
		
	}


	@Override
	public Map getTargetDetails(World world, int x, int y, int z, String target)
			throws Exception {

		return TargetHelper.getDetailedInformationForTarget(
				target,
				retriever.getAdjacentTiles(world, x, y, z),
				world
		);
		
	}
	
}

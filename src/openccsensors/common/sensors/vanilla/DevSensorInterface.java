package openccsensors.common.sensors.vanilla;

import java.util.Map;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorAccess;
import openccsensors.common.api.ISensorInterface;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.helper.TargetHelper;
import openccsensors.common.retrievers.ITileEntityValidatorCallback;
import openccsensors.common.retrievers.TileEntityRetriever;
import openccsensors.common.sensors.SensorCard;

public class DevSensorInterface implements ISensorInterface {

	private TileEntityRetriever retriever = new TileEntityRetriever();
	
	public DevSensorInterface()
	{
		retriever.registerCallback(new ITileEntityValidatorCallback() {
			@Override
			public ISensorTarget getTargetIfValid(TileEntity entity, int relativeX,
					int relativeY, int relativeZ) {
				return new DevTileTarget(entity, relativeX, relativeY, relativeZ);
			}
		});
	}
	
	@Override
	public Map callMethod(ISensorAccess sensor, World world, int x, int y,
			int z, int methodID, Object[] args) throws Exception {
		return null;
	}

	@Override
	public Map getBasicTarget(ISensorAccess sensor, World world, int x, int y,
			int z) throws Exception {
		
		return TargetHelper.getBasicInformationForTargets(
				retriever.getCube(world, x,  y,  z),
				world
		);
	}
	
	@Override
	public ISensorTarget getRelevantTargetForGauge(World world, int x, int y,
			int z) {
		return null;
	}

	@Override
	public int getId() {
		return 99;
	}

	@Override
	public String[] getMethods() {
		return null;
	}

	@Override
	public String getName() {
		return "openccsensors.item.devsensor";
	}

	@Override
	public Map getTargetDetails(ISensorAccess sensor, World world, int x,
			int y, int z, String target) throws Exception {

		return TargetHelper.getDetailedInformationForTarget(target, 
				retriever.getCube(world, x,  y,  z),
				world
		);
	}

	@Override
	public void initRecipes(SensorCard card) {
		
	}

	@Override
	public boolean isDirectionalEnabled() {
		return false;
	}

}

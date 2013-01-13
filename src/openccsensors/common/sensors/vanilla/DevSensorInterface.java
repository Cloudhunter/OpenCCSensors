package openccsensors.common.sensors.vanilla;

import java.util.Map;

import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorAccess;
import openccsensors.common.api.ISensorInterface;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.api.ITargetWrapper;
import openccsensors.common.helper.TargetHelper;
import openccsensors.common.sensors.SensorCard;
import openccsensors.common.sensors.TargetRetriever;

public class DevSensorInterface implements ISensorInterface {

	private TargetRetriever retriever = new TargetRetriever();
	
	public DevSensorInterface()
	{
		retriever.registerTarget(new ITargetWrapper() {
			@Override
			public ISensorTarget createNew(Object entity, int sx, int sy, int sz) {
				return new DevTileTarget((TileEntity) entity);
			}
		});
	}
	
	@Override
	public String getName() {
		return "openccsensors.item.devsensor";
	}

	@Override
	public int getId() {
		return 99;
	}

	@Override
	public Map getBasicTarget(ISensorAccess sensor, World world, int x, int y,
			int z) throws Exception {

		return TargetHelper.getBasicInformationForTargets(
				retriever.getAdjacentTiles(world, x, y, z, 5), world);
	}

	@Override
	public Map getTargetDetails(ISensorAccess sensor, World world, int x,
			int y, int z, String target) throws Exception {

		return TargetHelper.getDetailedInformationForTarget(target,
				retriever.getAdjacentTiles(world, x, y, z, 5), world);
	}

	@Override
	public boolean isDirectionalEnabled() {
		return false;
	}

	@Override
	public String[] getMethods() {
		return null;
	}

	@Override
	public Map callMethod(ISensorAccess sensor, World world, int x, int y,
			int z, int methodID, Object[] args) throws Exception {
		return null;
	}

	@Override
	public void initRecipes(SensorCard card) {
		
	}

}

package openccsensors.common.sensors.buildcraft;

import java.util.HashMap;
import java.util.Map;
import buildcraft.energy.IEngineProvider;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.sensors.TileSensorTarget;

public class EngineTarget extends TileSensorTarget implements ISensorTarget {

	EngineTarget(TileEntity targetEntity) {
		super(targetEntity);
	}

	@Override
	public Map getDetailInformation(World world) {

		HashMap retMap = new HashMap();

		IEngineProvider tileEngine = (IEngineProvider) world
				.getBlockTileEntity(xCoord, yCoord, zCoord);

		retMap.put("Active", tileEngine.getEngine().isActive());
		retMap.put("Heat", tileEngine.getEngine().getHeat());
		retMap.put("IsBurning", tileEngine.getEngine().isBurning());
		retMap.put("PistonSpeed", tileEngine.getEngine().getPistonSpeed());
		retMap.put("Energy", tileEngine.getEngine().energy);
		retMap.put("MaxEnergy", tileEngine.getEngine().maxEnergy);

		return retMap;
	}

}

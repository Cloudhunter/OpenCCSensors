package openccsensors.common.sensors.targets.buildcraft;

import java.lang.reflect.Method;
import java.util.HashMap;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.helper.BCHelper;
import openccsensors.common.sensors.targets.TileSensorTarget;
import buildcraft.api.power.IPowerProvider;
import buildcraft.api.power.IPowerReceptor;

public class PowerReceptorTarget extends TileSensorTarget implements
		ISensorTarget {

	public PowerReceptorTarget(TileEntity targetEntity, int relativeX,
			int relativeY, int relativeZ) {
		super(targetEntity, relativeX, relativeY, relativeZ);
	}

	@Override
	public HashMap getExtendedDetails(World world) {

		HashMap retMap = getBasicDetails(world);
		IPowerReceptor tileEngine = (IPowerReceptor) world.getBlockTileEntity(
				xCoord, yCoord, zCoord);
		IPowerProvider provider = tileEngine.getPowerProvider();

		if (provider == null) {
			return retMap;
		}

		retMap.put("MaxEnergyReceived", provider.getMaxEnergyReceived());
		retMap.put("EnergyStored", provider.getEnergyStored());
		retMap.put("ActivationEnergy", provider.getActivationEnergy());
		retMap.put("Latency", provider.getLatency());
		retMap.put("MaxEnergyStored", provider.getMaxEnergyStored());
		retMap.put("MinEnergyReceived", provider.getMinEnergyReceived());
		retMap.putAll(BCHelper.getEngineData((TileEntity)tileEngine));
		

		Class tileEngineClass = null;
		Class engineClass = null;
		try {
			tileEngineClass = Class.forName("buildcraft.energy.TileEngine");
			engineClass = Class.forName("buildcraft.energy.Engine");
		} catch (ClassNotFoundException e) {
		}

		if (tileEngineClass != null
				&& tileEngineClass.isAssignableFrom(tileEngine.getClass())) {
			try {
				Method getEngineMethod = tileEngineClass.getMethod("getEngine");
				Object engine = getEngineMethod.invoke(tileEngine);
				try {

					float energy = (Float) (engineClass
							.getMethod("getEnergyStored").invoke(engine));
					retMap.put("Energy", energy);

					int maxEnergy = (Integer) (engineClass
							.getField("maxEnergy").get(engine));
					retMap.put("MaxEnergy", maxEnergy);

				} catch (Exception e) {
				}
			} catch (Exception e) {
			}
		}

		return retMap;
	}

	@Override
	public String[] getTrackablePropertyNames() {
		return null;
	}


}

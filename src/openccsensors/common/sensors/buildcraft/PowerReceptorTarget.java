package openccsensors.common.sensors.buildcraft;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.sensors.TileSensorTarget;
import buildcraft.api.power.IPowerProvider;
import buildcraft.api.power.IPowerReceptor;

public class PowerReceptorTarget extends TileSensorTarget implements ISensorTarget {

	PowerReceptorTarget(TileEntity targetEntity, int relativeX, int relativeY, int relativeZ) {
		super(targetEntity, relativeX, relativeY, relativeZ);
	}

	@Override
	public Map getDetailInformation(World world) {

		HashMap retMap = new HashMap();
		IPowerReceptor tileEngine = (IPowerReceptor) world
				.getBlockTileEntity(xCoord, yCoord, zCoord);
		IPowerProvider provider = tileEngine.getPowerProvider();
		
		if (provider == null)
		{
			return null;
		}
	
		retMap.put("MaxEnergyReceived", provider.getMaxEnergyReceived());
		retMap.put("EnergyStored", provider.getEnergyStored());
		retMap.put("ActivationEnergy", provider.getActivationEnergy());
		retMap.put("Latency", provider.getLatency());
		retMap.put("MaxEnergyStored", provider.getMaxEnergyStored());
		retMap.put("MinEnergyReceived", provider.getMinEnergyReceived());
		
		Class tileEngineClass = null;
		Class engineClass = null;
		try {
			tileEngineClass = Class.forName("buildcraft.energy.TileEngine");
			engineClass = Class.forName("buildcraft.energy.Engine");
		}
		catch(ClassNotFoundException e){}
		
		if (tileEngineClass != null && tileEngineClass.isAssignableFrom(tileEngine.getClass()))
		{
			try {
				  Method getEngineMethod = tileEngineClass.getMethod("getEngine");
				  Object engine = getEngineMethod.invoke(tileEngine);
				  try {

					  float energy = (Float)(engineClass.getMethod("getEnergyStored").invoke(engine));
					  retMap.put("Energy", energy);
					  
					  int maxEnergy = (Integer)(engineClass.getField("maxEnergy").get(engine));
					  retMap.put("MaxEnergy", maxEnergy);
					  
				  }catch(Exception e) { }
			} catch (Exception e) {
			}
		}
		

		return retMap;
	}

}

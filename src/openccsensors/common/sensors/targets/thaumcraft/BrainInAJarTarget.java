package openccsensors.common.sensors.targets.thaumcraft;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.sensors.targets.TileSensorTarget;

public class BrainInAJarTarget extends TileSensorTarget implements ISensorTarget {

	public BrainInAJarTarget(TileEntity targetEntity, int relativeX, int relativeY, int relativeZ) {
		super(targetEntity, relativeX, relativeY, relativeZ);
	}

	@Override
	public Map getDetailInformation(World world) {

		HashMap retMap = new HashMap();

		TileEntity brainInJar = world.getBlockTileEntity(xCoord, yCoord, zCoord);

		NBTTagCompound compound = new NBTTagCompound();
		brainInJar.writeToNBT(compound);
		retMap.put("XP", compound.getInteger("XP"));
				
		return retMap;
	}
	
	@Override
	public boolean hasGaugePercentage() {
		return false;
	}

	@Override
	public double getGaugePercentage(World world) {
		return 0;
	}


}

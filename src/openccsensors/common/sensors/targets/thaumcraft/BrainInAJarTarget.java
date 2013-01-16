package openccsensors.common.sensors.targets.thaumcraft;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.sensors.targets.TileSensorTarget;

public class BrainInAJarTarget extends TileSensorTarget implements
		ISensorTarget {

	public BrainInAJarTarget(TileEntity targetEntity, int relativeX,
			int relativeY, int relativeZ) {
		super(targetEntity, relativeX, relativeY, relativeZ);
	}

	@Override
	public HashMap getExtendedDetails(World world) {
		
		TileEntity brainInJar = world.getBlockTileEntity(xCoord, yCoord, zCoord);
		HashMap retMap = getBasicDetails(world);
		NBTTagCompound compound = this.getTagCompound(brainInJar);
		retMap.put("XP", compound.getInteger("XP"));
		return retMap;
	}

	@Override
	public String[] getTrackablePropertyNames(World world) {
		return null;
	}

	@Override
	public int getTrackableProperty(World world, String name) {
		return 0;
	}


}

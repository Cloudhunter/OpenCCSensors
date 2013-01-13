package openccsensors.common.sensors.thaumcraft;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.helper.NetworkHelper;
import openccsensors.common.sensors.TileSensorTarget;

public class BrainInAJarTarget extends TileSensorTarget implements ISensorTarget {

	protected BrainInAJarTarget(TileEntity targetEntity) {
		super(targetEntity);
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


}

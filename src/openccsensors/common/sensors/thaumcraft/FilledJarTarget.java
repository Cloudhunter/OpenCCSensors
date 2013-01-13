package openccsensors.common.sensors.thaumcraft;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.sensors.TileSensorTarget;
import thaumcraft.api.EnumTag;

public class FilledJarTarget  extends TileSensorTarget implements ISensorTarget {

	protected FilledJarTarget(TileEntity targetEntity, int relativeX, int relativeY, int relativeZ) {
		super(targetEntity, relativeX, relativeY, relativeZ);
	}

	@Override
	public Map getDetailInformation(World world) {

		HashMap retMap = new HashMap();
		
		TileEntity filledJar = world.getBlockTileEntity(xCoord, yCoord, zCoord);
		
		NBTTagCompound compound = new NBTTagCompound();
		filledJar.writeToNBT(compound);
		EnumTag tag = EnumTag.get(compound.getByte("Tag"));
		Short amount = compound.getShort("Amount");
		retMap.put("Amount", amount);
		if (amount > 0)
		{
			if (tag != null) {
				retMap.put("Name", tag.name);
				retMap.put("Aggro", tag.aggro);
			}
		}
		return retMap;
	}


}

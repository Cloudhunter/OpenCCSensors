package openccsensors.common.sensors.targets.thaumcraft;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.sensors.targets.TileSensorTarget;
import thaumcraft.api.EnumTag;

public class CrucibleTarget extends TileSensorTarget implements ISensorTarget {

	public CrucibleTarget(TileEntity targetEntity, int relativeX,
			int relativeY, int relativeZ) {
		super(targetEntity, relativeX, relativeY, relativeZ);
	}

	@Override
	public Map getDetailInformation(World world) {

		HashMap retMap = new HashMap();

		TileEntity crucible = world.getBlockTileEntity(xCoord, yCoord, zCoord);

		NBTTagCompound compound = new NBTTagCompound();
		crucible.writeToNBT(compound);
		retMap.put("Heat", compound.getShort("Heat"));
		retMap.put("Liquid", compound.getBoolean("Liquid"));
		retMap.put("LiquidQty", compound.getShort("liquidQty"));
		int[] tb = compound.getIntArray("Tags");
		EnumTag tag;
		int a = 1;
		HashMap tagMap;
		HashMap tags = new HashMap();
		for (int i = 0; i < tb.length; i++) {
			try {
				if (tb[i] > 0) {
					tagMap = new HashMap();
					tag = EnumTag.get(i);
					tagMap.put("Name", tag.name);
					tagMap.put("Aggro", tag.aggro);
					tagMap.put("Amount", tb[i]);
					tags.put(a, tagMap);
					a++;

				}
			} catch (Exception e) {
			}
		}
		retMap.put("Aspects", tags);

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

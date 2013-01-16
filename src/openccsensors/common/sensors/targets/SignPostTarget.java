package openccsensors.common.sensors.targets;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorTarget;

public class SignPostTarget extends TileSensorTarget implements ISensorTarget {

	public SignPostTarget(TileEntity targetEntity, int relativeX, int relativeY, int relativeZ) {
		super(targetEntity, relativeX, relativeY, relativeZ);
	}

	@Override
	public HashMap getExtendedDetails(World world) {

		HashMap retMap = getBasicDetails(world);
		TileEntitySign sign = (TileEntitySign) world.getBlockTileEntity(xCoord,
				yCoord, zCoord);

		String signText = "";
		for (int i = 0; i < sign.signText.length; i++)
		{
			signText = signText + sign.signText[i];
			if (i < 3 && sign.signText[i] != "")
			{
				signText = signText + " ";
			}
		}
		retMap.put("Text", signText.trim());
		
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

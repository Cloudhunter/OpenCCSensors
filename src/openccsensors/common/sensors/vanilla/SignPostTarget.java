package openccsensors.common.sensors.vanilla;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.sensors.TileSensorTarget;

public class SignPostTarget extends TileSensorTarget implements ISensorTarget {

	protected SignPostTarget(TileEntity targetEntity, int relativeX, int relativeY, int relativeZ) {
		super(targetEntity, relativeX, relativeY, relativeZ);
	}

	@Override
	public Map getDetailInformation(World world) {

		HashMap retMap = new HashMap();
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
	public boolean hasGaugePercentage() {
		return false;
	}

	@Override
	public double getGaugePercentage(World world) {
		return 0;
	}

}

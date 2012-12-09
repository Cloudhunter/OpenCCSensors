package openccsensors.common.sensors.vanilla;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.IInventory;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntitySign;
import net.minecraft.src.World;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.helper.InventoryHelper;
import openccsensors.common.sensors.TileSensorTarget;

public class SignPostTarget extends TileSensorTarget implements ISensorTarget {

	protected SignPostTarget(TileEntity targetEntity) {
		super(targetEntity);
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

}

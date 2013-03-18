package openccsensors.api;

import java.util.HashMap;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public interface IGaugeSensor {
	public String[] getGaugeProperties();
	public HashMap getDetails(World world, Object obj, boolean additional);
}

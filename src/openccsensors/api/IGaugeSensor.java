package openccsensors.api;

import java.util.HashMap;

import net.minecraft.world.World;

public interface IGaugeSensor {
	public String[] getGaugeProperties();
	public boolean isValidTarget(Object obj);
	public HashMap getDetails(World world, Object obj, boolean additional);
}

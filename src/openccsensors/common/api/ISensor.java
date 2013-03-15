package openccsensors.common.api;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public interface ISensor {
	public String[] getCustomMethods(SensorUpgradeTier upgrade);

	public Object callCustomMethod(ISensorAccess sensor, World world, int x, int y, int z, int methodID, Object[] args, SensorUpgradeTier upgrade) throws Exception;

	public HashMap<String, ArrayList<ISensorTarget>> getSurroundingTargets(
			World world, int sx, int sy, int sz, SensorUpgradeTier upgrade);

	public String getIconName();
}

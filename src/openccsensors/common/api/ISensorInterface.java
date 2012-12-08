package openccsensors.common.api;

import java.util.Map;

import openccsensors.common.sensors.SensorCard;

import net.minecraft.src.World;

public interface ISensorInterface 
{
	String getName();
	int getId();
	Map getBasicTarget(ISensorAccess sensor, World world, int x, int y, int z) throws Exception;
	Map getTargetDetails(ISensorAccess sensor, World world, int x, int y, int z, String target) throws Exception;
	boolean isDirectionalEnabled();
	String[] getMethods();
	Object[] callMethod(ISensorAccess sensor, int methodID, Object[] args) throws Exception;
	void initRecipes(SensorCard card);
}

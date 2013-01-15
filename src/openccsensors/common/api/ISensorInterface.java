package openccsensors.common.api;

import java.util.Map;

import net.minecraft.world.World;
import openccsensors.common.sensors.SensorCard;

public interface ISensorInterface 
{
	Map callMethod(ISensorAccess sensor, World world, int x, int y, int z, int methodID, Object[] args, int cardMark) throws Exception;
	Map getBasicTarget(ISensorAccess sensor, World world, int x, int y, int z, int cardMark) throws Exception;
	Map getTargetDetails(ISensorAccess sensor, World world, int x, int y, int z, int cardMark, String target) throws Exception;
	int getId();
	String[] getMethods();
	String getName();
	ISensorTarget getRelevantTargetForGauge(World world, int x, int y, int z);
	void initRecipes(SensorCard card);
	boolean isDirectionalEnabled();
}

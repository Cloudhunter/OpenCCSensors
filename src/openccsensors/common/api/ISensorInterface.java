package openccsensors.common.api;

import java.util.Map;

import net.minecraft.src.World;

public interface ISensorInterface 
{
	String getName();
	int getId();
	Map getBasicTarget(World world, int x, int y, int z) throws Exception;
	Map getTargetDetails(World world, int x, int y, int z, String target) throws Exception;
	String[] getMethods();
	Object[] callMethod(ISensorAccess sensor, int methodID, Object[] args) throws Exception;
	void initRecipes();
}

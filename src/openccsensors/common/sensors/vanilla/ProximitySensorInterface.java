package openccsensors.common.sensors.vanilla;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.IInventory;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

import openccsensors.common.core.ISensorInterface;
import openccsensors.common.core.ISensorTarget;
import openccsensors.common.core.ITargetWrapper;
import openccsensors.common.helper.LivingEntityHelper;
import openccsensors.common.helper.TargetHelper;
import openccsensors.common.sensors.TargetRetriever;

public class ProximitySensorInterface implements ISensorInterface 
{

	private TargetRetriever retriever = new TargetRetriever();
	private final double sensingRadius = 16.0F;
	
	public ProximitySensorInterface()
	{
		retriever.registerTarget(EntityLiving.class, new ITargetWrapper() {
			@Override
			public ISensorTarget createNew(Object entity, int sx, int sy, int sz) {
				return new LivingTarget((EntityLiving)entity, sx, sy, sz);
			}
		});
	}

	@Override
	public String getName() 
	{
		return "proximity";
	}
	
	@Override
	public String[] getMethods() 
	{
		return null;
	}

	@Override
	public Object[] callMethod(int methodID, Object[] args) throws Exception 
	{
		return null;
	}

	@Override
	public Map getBasicTarget(World world, int x, int y, int z)
			throws Exception {
		
		return TargetHelper.getBasicInformationForTargets(
				retriever.getLivingEntities(world, x, y, z, sensingRadius),
				world
			);
		
	}

	@Override
	public Map getTargetDetails(World world, int x, int y, int z, String target)
			throws Exception {
		
		return TargetHelper.getDetailedInformationForTarget(
				target,
				retriever.getLivingEntities(world, x, y, z, sensingRadius),
				world
			);
		
	}

}

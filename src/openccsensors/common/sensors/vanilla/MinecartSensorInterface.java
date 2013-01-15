package openccsensors.common.sensors.vanilla;

import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorAccess;
import openccsensors.common.api.ISensorInterface;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.helper.TargetHelper;
import openccsensors.common.retrievers.EntityRetriever;
import openccsensors.common.retrievers.IEntityValidatorCallback;
import openccsensors.common.sensors.SensorCard;

public class MinecartSensorInterface implements ISensorInterface {


	private EntityRetriever retriever = new EntityRetriever();
	private final double sensingRadius = 16.0F;
	
	public MinecartSensorInterface()
	{
		retriever.registerCallback(new IEntityValidatorCallback() {
			@Override
			public ISensorTarget getTargetIfValid(Entity entity, double relativeX, double relativeY, double relativeZ) {
				if (entity instanceof EntityMinecart && entity.isEntityAlive())
				{
					return new MinecartTarget((EntityMinecart)entity, relativeX, relativeY, relativeZ);
				}
				return null;
			}
		});
	}
	
	@Override
	public Map callMethod(ISensorAccess sensor, World world, int x, int y,
			int z, int methodID, Object[] args, int cardMark) throws Exception {
		return null;
	}

	@Override
	public Map getBasicTarget(ISensorAccess sensor, World world, int x, int y,
			int z, int cardMark) throws Exception {
		return TargetHelper.getBasicInformationForTargets(
				retriever.getSphere(world, x, y, z, sensingRadius),
				world);
	}

	@Override
	public Map getTargetDetails(ISensorAccess sensor, World world, int x,
			int y, int z, int cardMark, String target) throws Exception {

		return TargetHelper.getDetailedInformationForTarget(target,
				retriever.getSphere(world, x, y, z, sensingRadius),
				world);

	}

	@Override
	public int getId() {
		return 25;
	}

	@Override
	public String[] getMethods() {
		return null;
	}

	@Override
	public String getName() {
		return "openccsensors.item.minecartsensor";
	}

	@Override
	public ISensorTarget getRelevantTargetForGauge(World world, int x, int y,
			int z) {
		return null;
	}

	@Override
	public void initRecipes(SensorCard card) {
		
	}

	@Override
	public boolean isDirectionalEnabled() {
		return false;
	}

}

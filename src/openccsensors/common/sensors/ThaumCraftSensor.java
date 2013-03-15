package openccsensors.common.sensors;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import openccsensors.common.api.ISensor;
import openccsensors.common.api.ISensorAccess;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.api.ITileEntityValidatorCallback;
import openccsensors.common.api.SensorUpgradeTier;
import openccsensors.common.sensors.targets.thaumcraft.BrainInAJarTarget;
import openccsensors.common.sensors.targets.thaumcraft.CrucibleTarget;
import openccsensors.common.sensors.targets.thaumcraft.FilledJarTarget;

public class ThaumCraftSensor extends BaseTileEntitySensor implements ISensor {

	public static final String BRAIN_IN_A_JAR_CLASS = "thaumcraft.common.blocks.jars.TileJarBrain";
	public static final String CRUCIBLE_CLASS = "thaumcraft.common.blocks.TileCrucible";
	public static final String FILLED_JAR_CLASS = "thaumcraft.common.blocks.jars.TileJarFillable";
	
	public ThaumCraftSensor() {
		
		registerCallback(new ITileEntityValidatorCallback() {

			@Override
			public ISensorTarget getTargetIfValid(TileEntity entity,
					int relativeX, int relativeY, int relativeZ) {
				String className = entity.getClass().getName();

				if (className.equals(BRAIN_IN_A_JAR_CLASS))
				{
					return new BrainInAJarTarget(entity, relativeX, relativeY, relativeZ);
				}
				else if (className.equals(CRUCIBLE_CLASS))
				{
					return new CrucibleTarget(entity, relativeX, relativeY, relativeZ);
				}
				else if (className.equals(FILLED_JAR_CLASS))
				{
					return new FilledJarTarget(entity, relativeX, relativeY, relativeZ);
				}
				return null;
			}
			
		});
		
	}
	
	@Override
	public String[] getCustomMethods(SensorUpgradeTier upgrade) {
		return null;
	}

	@Override
	public Object callCustomMethod(ISensorAccess sensor, World world, int x, int y, int z, int methodID, Object[] args, SensorUpgradeTier upgrade) {
		return null;
	}

	@Override
	public String getIconName() {
		return "openccsensors:thaumcraft";
	}

}

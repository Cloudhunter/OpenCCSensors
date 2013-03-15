package openccsensors.common.sensors;

import ic2.api.IEnergyStorage;
import ic2.api.IReactor;
import ic2.api.IReactorChamber;
import ic2.api.energy.tile.IEnergyConductor;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import openccsensors.common.api.ISensor;
import openccsensors.common.api.ISensorAccess;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.api.ITileEntityValidatorCallback;
import openccsensors.common.api.SensorUpgradeTier;
import openccsensors.common.sensors.targets.industrialcraft.EnergyConductorTarget;
import openccsensors.common.sensors.targets.industrialcraft.EnergyStorageTarget;
import openccsensors.common.sensors.targets.industrialcraft.MassFabTarget;
import openccsensors.common.sensors.targets.industrialcraft.ReactorTarget;

public class IndustrialCraftSensor extends BaseTileEntitySensor implements ISensor {

	protected static final String MASS_FAB_CLASS = "ic2.core.block.machine.tileentity.TileEntityMatter";

	public IndustrialCraftSensor() {
		registerCallback(new ITileEntityValidatorCallback() {

			@Override
			public ISensorTarget getTargetIfValid(TileEntity entity,
					int relativeX, int relativeY, int relativeZ) {
				if (entity instanceof IReactor)
				{
					return new ReactorTarget(entity, relativeX, relativeY, relativeZ);
				}
				else if (entity instanceof IReactorChamber)
				{
					return new ReactorTarget(
							(TileEntity) ((IReactorChamber) entity).getReactor(), relativeX, relativeY, relativeZ);
				}
				return null;
			}
			
		});
		registerCallback(new ITileEntityValidatorCallback() {

			@Override
			public ISensorTarget getTargetIfValid(TileEntity entity,
					int relativeX, int relativeY, int relativeZ) {
				if (entity instanceof TileEntity && entity.getClass().getName() == MASS_FAB_CLASS)
				{
					return new MassFabTarget(entity, relativeX, relativeY, relativeZ);
				}
				return null;
			}
		});

		registerCallback(new ITileEntityValidatorCallback() {
			@Override
			public ISensorTarget getTargetIfValid(TileEntity entity, int relativeX, int relativeY, int relativeZ) {
				if (entity instanceof IEnergyStorage)
				{
					return new EnergyStorageTarget(entity, relativeX, relativeY, relativeZ);
				}
				return null;
			}
		});
		
		registerCallback(new ITileEntityValidatorCallback() {
			@Override
			public ISensorTarget getTargetIfValid(TileEntity entity, int relativeX, int relativeY, int relativeZ) {
				if (entity instanceof IEnergySink ||
					entity instanceof IEnergySource ||
					entity instanceof IEnergyConductor)
				{
					return new EnergyConductorTarget(entity, relativeX, relativeY, relativeZ);
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
		return "openccsensors:industrialCraft";
	}

}

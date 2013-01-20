package openccsensors.common.sensors;

import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import openccsensors.common.api.ISensor;
import openccsensors.common.api.ISensorAccess;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.api.ITileEntityValidatorCallback;
import openccsensors.common.api.SensorUpgradeTier;
import openccsensors.common.core.OCSLog;
import openccsensors.common.helper.RCHelper;
import openccsensors.common.sensors.targets.TankTarget;

public class TankSensor extends BaseTileEntitySensor implements ISensor {

	
	public TankSensor() {
	
		
		registerCallback(new ITileEntityValidatorCallback() {

			@Override
			public ISensorTarget getTargetIfValid(TileEntity entity,
					int relativeX, int relativeY, int relativeZ) {
				
				if (entity instanceof ITankContainer) {
					
					return new TankTarget(((ITankContainer)entity).getTanks(ForgeDirection.UNKNOWN), relativeX, relativeY,
							relativeZ);
					
				} else if (ModLoader.isModLoaded("Railcraft")) {
					
					ILiquidTank tankTile = RCHelper.getTankIfTankTile(entity);
					if (tankTile != null) {
						ILiquidTank[] tanks = new ILiquidTank[] {  (ILiquidTank)tankTile };
						return new TankTarget(tanks, relativeX, relativeY,
								relativeZ);
					}
					
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

}

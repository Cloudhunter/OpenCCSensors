package universalelectricity.components.common.tileentity;

import net.minecraft.block.Block;
import universalelectricity.components.common.BasicComponents;
import universalelectricity.prefab.tile.TileEntityConductor;

public class TileEntityCopperWire extends TileEntityConductor
{
	/**
	 * Changed this if your mod wants to nerf Basic Component's copper wire.
	 */
	public static double RESISTANCE = 0.05;
	public static double MAX_AMPS = 200;

	public TileEntityCopperWire()
	{
		this.channel = BasicComponents.CHANNEL;
	}

	@Override
	public double getResistance()
	{
		return RESISTANCE;
	}

	@Override
	public double getCurrentCapcity()
	{
		return MAX_AMPS;
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();

		if (this.getNetwork() != null && this.ticks % 20 == 0)
		{
			if (this.getNetwork().getProduced().amperes > this.getCurrentCapcity())
			{
				if (!this.worldObj.isRemote)
				{
					this.worldObj.setBlockAndMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, Block.fire.blockID, 0, 2);
				}
			}
		}
	}
}

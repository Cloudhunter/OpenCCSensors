package openccsensors.common.blocks;

import openccsensors.common.tileentities.TileEntitySensor;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class SensorBlock extends BlockContainer {

	protected SensorBlock(int par1, Material par2Material) 
	{
		super(par1, par2Material);
	}

	@Override
	public TileEntity createNewTileEntity(World var1)
	{
		return new TileEntitySensor();
	}

}
